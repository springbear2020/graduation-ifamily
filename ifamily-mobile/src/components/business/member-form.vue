<template>
  <div>
    <van-form @submit="$emit('save', formData)">
      <!-- 肖像图片 -->
      <div class="flex-container van-hairline--bottom">
        <van-field>
          <template #input>
            <van-uploader max-count="1" :after-read="afterRead" v-model="fileList" :max-size="5 * 1024 * 1024"
                          @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})" :before-read="beforeRead"/>
          </template>
        </van-field>
      </div>

      <!-- 姓氏、名字、字辈 -->
      <van-cell-group :border="false">
        <van-field label="姓氏" placeholder="姓氏" required :border="false" v-model.trim="formData.surname"
                   :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写人员姓氏, 长度不大于 30' }]">
          <template #button>
            <van-button v-if="anonymous" size="small" type="warning" @click.prevent="unknown">人员已失考？</van-button>
          </template>
        </van-field>
        <van-field label="姓名" placeholder="姓氏 + 名字" required :border="false" v-model.trim="formData.name"
                   :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写人员姓名, 长度不大于 30' }]"/>
        <van-field label="字辈" placeholder="字辈" required :border="false" v-model.trim="formData.generationName"
                   :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写人员字辈, 长度不大于 30' }]"/>
      </van-cell-group>

      <!-- 性别、世代、排行 -->
      <van-cell-group>
        <van-field label="性别" :border="false" required>
          <template #input>
            <van-radio-group v-model="formData.gender" direction="horizontal">
              <van-radio name="0">男</van-radio>
              <van-radio name="1">女</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field label="世代" placeholder="世代" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.generation"/>
          </template>
        </van-field>
        <van-field label="排行" placeholder="排行" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.seniority"/>
          </template>
        </van-field>
      </van-cell-group>

      <!-- 手机、常住地 -->
      <van-cell-group :border="false">
        <van-field type="tel" label="手机" placeholder="手机" :border="false" v-model.trim="formData.phone"/>
        <van-field label="常住地" placeholder="点击选择省市区" readonly clickable :border="false"
                   v-model.trim="formData.residence" @click="areaType = '1'; showAreaPopup = true"
        />
      </van-cell-group>

      <!-- 生于、出生地 -->
      <van-cell-group>
        <van-field readonly clickable label="生于" placeholder="点击选择出生日期"
                   v-model="formData.birthdate" @click="dateType = '0'; showDatetimePicker = true" :border="false"/>
        <van-field readonly clickable label="出生地" placeholder="点击选择省市区"
                   v-model="formData.birthplace" @click="areaType = '2'; showAreaPopup = true" :border="false"/>
      </van-cell-group>

      <!-- 逝于、埋葬地 -->
      <van-cell-group :border="false">
        <van-field label="健在" input-align="right" :border="false">
          <template #input>
            <van-switch v-model="alive" size="20"/>
          </template>
        </van-field>
        <div v-show="!alive">
          <van-field readonly clickable label="逝于" placeholder="点击选择逝世日期"
                     v-model="formData.deathDate" :border="false" @click="dateType = '1'; showDatetimePicker = true"/>
          <van-field readonly clickable label="埋葬地" placeholder="点击选择省市区"
                     @click="areaType = '3'; showAreaPopup = true" v-model="formData.burialPlace" :border="false"/>
        </div>
      </van-cell-group>

      <van-cell-group>
        <van-field rows="3" :autosize="true" type="textarea" maxlength="200" label="备注"
                   placeholder="特殊家庭关系备注，如养子、养女、继子、继女等"
                   show-word-limit v-model.trim="formData.familyRelationRemark"/>
      </van-cell-group>

      <div class="flex-container">
        <van-button block @click.prevent="$emit('hidden-form'); resetForm">取消</van-button>
        <van-button block type="info" native-type="submit">保存</van-button>
      </div>
    </van-form>

    <!-- 日期选择动作面板 -->
    <van-action-sheet v-model="showDatetimePicker" @open="datetimePickerOpen">
      <van-datetime-picker v-model="selectedDate" type="date" title="选择年月日"
                           :min-date="minDate" :max-date="curDate" @cancel="showDatetimePicker = false"
                           @confirm="confirmDate">
        <template #columns-bottom>
          <van-cell center title="农历日期">
            <template #right-icon>
              <van-switch v-model="lunar" size="20"/>
            </template>
          </van-cell>
        </template>
      </van-datetime-picker>
    </van-action-sheet>

    <!-- 地址选择动作面板 -->
    <van-action-sheet v-model="showAreaPopup">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false" title="选择省市区">
        <template #columns-bottom>
          <div class="full-address-wrapper">
            <van-field rows="1" :autosize="true" label="详细地址" type="textarea" maxlength="100" placeholder="详细地址"
                       show-word-limit
                       v-model="fullAddress"/>
          </div>
        </template>
      </van-area>
    </van-action-sheet>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import {areaList} from '@vant/area-data'
import {imageUploader} from "@/mixin/image-uploader";
import {mergeNonNullValues} from "@/utils/converter";

export default {
  name: "member-form",
  data() {
    return {
      // FIXME [Vant BUG]while minDate.year < 100, error years between minDate and curDate
      // [0]出生日期 [1]逝世日期
      dateType: '1',
      minDate: new Date(100, 0, 1),
      curDate: new Date(),
      selectedDate: new Date(),
      showDatetimePicker: false,
      // [1]常住地 [2]出生地 [3]埋葬地
      areaType: '1',
      areaList: [],
      showAreaPopup: false,
      fullAddress: '',
      lunar: false,
      // 肖像
      fileList: [],
      alive: true,
      formData: {
        portrait: undefined,
        surname: undefined,
        name: undefined,
        gender: '0',
        generation: 1,
        generationName: undefined,
        seniority: 1,
        phone: undefined,
        residence: undefined,
        birthdate: undefined,
        lunarBirthdate: 0,
        birthplace: undefined,
        deathDate: undefined,
        lunarDeathDate: 0,
        burialPlace: undefined,
        familyRelationRemark: undefined
      }
    }
  },
  mixins: [imageUploader],
  props: {
    people: {
      type: Object,
      required: false
    },
    anonymous: {
      type: Boolean,
      default: false
    }
  },
  mounted() {
    this.areaList = areaList
    // 修改图片类型为家族封面 [1]用户头像 [2]家族封面 [3]人物肖像
    this.imgType = '3';
  },
  watch: {
    // 图片上传成功,将任务肖像链接保存到表单信息中
    imgUrl() {
      this.formData.portrait = this.imgUrl
    },
    people: {
      deep: true,
      handler() {
        if (this.people) {
          // 获取到 people 中的所有非空属性值组合为一个新对象，避免直接使用 Object.assign 时将 people 中的 null 合并为 ""
          const target = mergeNonNullValues(this.people)
          Object.assign(this.formData, target);

          // 特殊处理肖像、性别、出生日期和逝世日期
          let {portrait, gender, birthdate, deathDate} = this.people
          if (portrait) {
            this.fileList.push({url: portrait})
          }
          if (gender) {
            this.formData.gender = gender.toString();
          }
          if (birthdate) {
            this.formData.birthdate = dayjs(birthdate).format("YYYY-MM-DD");
          }
          if (deathDate) {
            this.alive = false
            this.formData.deathDate = dayjs(deathDate).format("YYYY-MM-DD")
          }
        }
      }
    },
    alive(newVal) {
      // 健在，清除逝世日期和埋葬地表单信息
      if (newVal) {
        this.formData.deathDate = ""
        this.formData.lunarDeathDate = 0
        this.formData.burialPlace = ""
      }
    }
  },
  methods: {
    confirmDate() {
      const dateStr = dayjs(this.selectedDate).format('YYYY-MM-DD')
      if (this.dateType === '0') {
        // 出生日期
        this.formData.birthdate = dateStr
        this.formData.lunarBirthdate = this.lunar ? 1 : 0
      } else if (this.dateType === '1') {
        // 逝世日期
        this.formData.deathDate = dateStr
        this.formData.lunarDeathDate = this.lunar ? 1 : 0
      }
      this.showDatetimePicker = false
    },
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress.trim() : area
      switch (this.areaType) {
        case "1":
          this.formData.residence = area;
          break;
        case "2":
          this.formData.birthplace = area
          break;
        case "3":
          this.formData.burialPlace = area;
          break;
      }
      this.showAreaPopup = false
    },
    datetimePickerOpen() {
      // 设置日期选择器打开时是否勾选农历日期
      const dateType = this.dateType
      if (dateType === '0') {
        // 出生日期
        this.lunar = this.formData.lunarBirthdate === 1
      } else {
        // 逝世日期
        this.lunar = this.formData.lunarDeathDate === 1
      }
    },
    resetForm() {
      // 表单恢复初始状态
      this.formData = this.$options.data().formData
    },
    unknown() {
      this.formData.surname = '佚';
      this.formData.name = '无名氏';
      this.formData.generationName = this.formData.generationName ? this.formData.generationName : '佚'
    }
  }
}
</script>

<style scoped>
.van-uploader {
  margin: auto auto;
  padding: 24px;
}

/deep/ .van-uploader__preview {
  margin: 0;
}

/deep/ .van-uploader__upload {
  margin: 0;
}

.flex-container button {
  margin: 8px 0;
}

.full-address-wrapper .van-field {
  background-color: #f7f8fa;
}
</style>
