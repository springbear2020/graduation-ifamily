<template>
  <div>
    <van-form @submit="$emit('submit-form-data', formData)">
      <!-- 肖像图片 -->
      <div class="flex-container">
        <van-field>
          <template #input>
            <van-uploader max-count="1" :after-read="afterRead" v-model="fileList" :max-size="5 * 1024 * 1024"
                          @oversize="$toast('文件大小不能超过 5MB')" :before-read="beforeRead"/>
          </template>
        </van-field>
      </div>

      <!-- 姓氏、名字、字辈 -->
      <van-cell-group>
        <van-field label="姓氏" placeholder="姓氏" required :border="false" v-model="formData.surname"
                   :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写人员姓氏, 长度不大于 30' }]"/>
        <van-field label="名字" placeholder="名字" required :border="false" v-model="formData.name"
                   :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写人员姓名, 长度不大于 30' }]"/>
        <van-field v-model="formData.generationName" label="字辈" placeholder="字辈" required :border="false"
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
      <van-cell-group>
        <van-field type="tel" label="手机" placeholder="手机" :border="false" v-model="formData.phone"/>
        <van-field label="常住地" placeholder="点击选择省市区" readonly clickable :border="false"
                   v-model="formData.residence" @click="areaType = '1'; showAreaPopup = true"
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
      <van-cell-group>
        <van-field label="健在" input-align="right" :border="false">
          <template #input>
            <van-switch v-model="formData.alive" size="20"/>
          </template>
        </van-field>
        <div v-show="!formData.alive">
          <van-field readonly clickable label="逝于" placeholder="点击选择逝世日期"
                     v-model="formData.deathDate" :border="false" @click="dateType = '1'; showDatetimePicker = true"/>
          <van-field readonly clickable label="埋葬地" placeholder="点击选择省市区"
                     @click="areaType = '3'; showAreaPopup = true" v-model="formData.burialPlace" :border="false"/>
        </div>
      </van-cell-group>

      <van-cell-group>
        <van-field rows="3" autosize type="textarea" maxlength="100" label="备注" placeholder="特殊家庭关系备注，如养子、养女、继子、继女等"
                   show-word-limit v-model="formData.familyRelationRemark"/>
      </van-cell-group>

      <div class="flex-container">
        <van-button block @click="$emit('hidden-form')">取消</van-button>
        <van-button block type="info" native-type="submit">保存</van-button>
      </div>
    </van-form>

    <!-- 日期选择弹出层 -->
    <van-action-sheet v-model="showDatetimePicker">
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

    <!-- 地址选择弹出层 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <div class="full-address-wrapper">
            <van-field rows="1" autosize label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
                       v-model="fullAddress"/>
          </div>
        </template>
      </van-area>
    </van-popup>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import {areaList} from '@vant/area-data'
import {imageUploader} from "@/mixin/image-uploader";

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
      formData: {
        portrait: '',
        surname: '',
        name: '',
        gender: '0',
        generation: 1,
        generationName: '',
        seniority: 1,
        phone: '',
        residence: '',
        birthdate: '',
        lunarBirthdate: 0,
        birthplace: '',
        alive: true,
        deathDate: '',
        lunarDeathDate: 0,
        burialPlace: '',
        familyRelationRemark: ''
      }
    }
  },
  mixins: [imageUploader],
  mounted() {
    this.areaList = areaList
    // 修改图片类型为家族封面 [1]用户头像 [2]家族封面 [3]人物肖像
    this.imgType = '3';
  },
  watch: {
    // 图片上传成功,将任务肖像链接保存到表单信息中
    imgUrl() {
      this.formData.portrait = this.imgUrl
    }
  },
  methods: {
    confirmDate() {
      const dateStr = dayjs(this.selectedDate).format('YYYY-MM-DD')
      if (this.dateType === '0') {
        this.formData.birthdate = dateStr
        if (this.lunar) {
          this.formData.lunarBirthdate = 1
        }
      } else if (this.dateType === '1') {
        this.formData.deathDate = dateStr
        if (this.lunar) {
          this.formData.lunarDeathDate = 1
        }
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
  }
}
</script>

<style scoped>
.flex-container {
  display: flex;
}

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
