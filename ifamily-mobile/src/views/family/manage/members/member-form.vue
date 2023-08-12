<template>
  <div>
    <!-- 信息表单 -->
    <van-form>
      <!-- 头像、姓氏、名字、字辈 -->
      <van-cell-group class="flex-container">
        <van-uploader max-count="1" v-model="formData.portrait"/>
        <div>
          <van-field name="surname" v-model="formData.surname" label="姓氏" placeholder="姓氏" :border="false" required/>
          <van-field name="name" v-model="formData.name" label="名字" placeholder="名字" :border="false" required/>
          <van-field name="word" v-model="formData.word" label="字辈" placeholder="字辈" :border="false" required/>
        </div>
      </van-cell-group>

      <!-- 性别、世代、排行 -->
      <van-cell-group>
        <van-field name="sex" label="性别" :border="false" required>
          <template #input>
            <van-radio-group v-model="formData.sex" direction="horizontal">
              <van-radio name="0">男</van-radio>
              <van-radio name="1">女</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field name="generation" label="世代" placeholder="世代" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.generation"/>
          </template>
        </van-field>
        <van-field name="range" label="排行" placeholder="排行" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.range"/>
          </template>
        </van-field>
      </van-cell-group>

      <!-- 手机、现居地 -->
      <van-cell-group>
        <van-field name="mobile" type="tel" v-model="formData.mobile" label="手机" placeholder="手机" :border="false"/>
        <van-field name="presentPlace" label="现居地" placeholder="点击选择省市区" readonly clickable
                   v-model="formData.presentPlace" @click="areaType = '1'; showAreaPopup = true"
                   :border="false"/>
      </van-cell-group>

      <!-- 生于、出生地 -->
      <van-cell-group>
        <van-field readonly clickable name="birthdate" label="生于" placeholder="点击选择出生日期"
                   v-model="formData.birthdate" @click="dateType = '1'; showDatetimePicker = true"
                   :border="false"/>
        <van-field name="birthplace" readonly clickable label="出生地" placeholder="点击选择省市区"
                   v-model="formData.birthplace" @click="areaType = '2'; showAreaPopup = true"
                   :border="false"/>
      </van-cell-group>

      <!-- 逝于、埋葬地 -->
      <van-cell-group>
        <van-field name="alive" v-model="formData.birthplace" label="健在" input-align="right" :border="false">
          <template #input>
            <van-switch v-model="formData.alive" size="20"/>
          </template>
        </van-field>
        <div v-show="!formData.alive">
          <van-field readonly clickable name="dieDate" label="逝于" placeholder="点击选择逝世日期"
                     v-model="formData.dieDate" :border="false"
                     @click="dateType = '2'; showDatetimePicker = true"/>
          <van-field name="burialPlace" readonly clickable label="埋葬地" placeholder="点击选择省市区"
                     @click="areaType = '3'; showAreaPopup = true" v-model="formData.burialPlace"
                     :border="false"/>
        </div>
      </van-cell-group>

      <div class="flex-container">
        <van-button block @click="showForm = false">取消</van-button>
        <van-button block type="primary" @click="handleAddRelatives">保存</van-button>
      </div>
    </van-form>

    <!-- 日期选择弹出层 -->
    <van-action-sheet v-model="showDatetimePicker">
      <van-datetime-picker v-model="selectedDate" type="datetime" title="选择年月日小时分钟"
                           :min-date="minDate" :max-date="curDate" @cancel="showDatetimePicker = false"
                           @confirm="confirmDate"/>
    </van-action-sheet>

    <!-- 地址选择弹出层 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <div class="full-address-container">
            <van-field rows="1" label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
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

export default {
  name: "member-form",
  data() {
    return {
      // TODO [Vant bug]当 minDate 的 year 小于 100 时 [minYear, maxYear] 间的年份不能正常渲染
      // [1]出生日期 [2]逝世日期
      dateType: '1',
      minDate: new Date(100, 0, 1),
      curDate: new Date(),
      selectedDate: new Date(),
      showDatetimePicker: false,
      // [1]现居地 [2]出生地 [3]埋葬地
      areaType: '1',
      areaList: [],
      showAreaPopup: false,
      fullAddress: '',

      formData: {
        portrait: [],
        surname: '',
        name: '',
        sex: '0',
        generation: 1,
        word: '',
        range: 1,
        mobile: '',
        presentPlace: '',
        birthdate: '',
        birthplace: '',
        alive: true,
        dieDate: '',
        burialPlace: ''
      }
    }
  },
  mounted() {
    this.areaList = areaList
  },
  methods: {
    handleAddRelatives() {
      this.$emit('submit-form-data', this.formData)
      this.showForm = false
    },
    confirmDate() {
      const dateStr = dayjs(this.selectedDate).format('YYYY-MM-DD HH:mm:ss')
      if (this.dateType === '1') {
        this.formData.birthdate = dateStr
      } else {
        this.formData.dieDate = dateStr
      }
      this.showDatetimePicker = false
    },
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress.trim() : area
      switch (this.areaType) {
        case "1":
          this.formData.presentPlace = area;
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
/deep/ .van-uploader__upload {
  margin: 0;
}

/deep/ .van-uploader {
  display: flex;
}

/deep/ .van-uploader .van-uploader__wrapper {
  margin: auto;
  padding: 16px;
}

.flex-container button {
  margin-top: 16px;
}
</style>