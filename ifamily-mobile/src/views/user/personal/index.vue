<template>
  <div>
    <van-nav-bar
        title="个人信息"
        left-arrow
        @click-left="backUser"
        @click-right="saveUserPersonal"
    >
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-form>
      <van-field name="uploader" label="头像">
        <template #input>
          <van-uploader v-model="formData.uploader" max-count="1"/>
        </template>
      </van-field>
      <van-field
          v-model="formData.nickname"
          name="nickname"
          label="昵称"
          placeholder="昵称"
      />
      <van-field
          v-model="formData.realName"
          name="realName"
          label="真实姓名"
          placeholder="真实姓名"
      />
      <van-field
          readonly
          clickable
          name="birthdate"
          :value="formData.birthdate"
          label="出生日期"
          placeholder="点击选择生日"
          @click="showCalendar = true"
      />
      <van-field
          type="digit"
          readonly
          v-model="formData.age"
          name="age"
          label="年龄"
          placeholder="年龄"
      />
      <van-field name="gender" label="性别">
        <template #input>
          <van-radio-group v-model="formData.gender" direction="horizontal">
            <van-radio name="0">男</van-radio>
            <van-radio name="1">女</van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <van-field
          readonly
          clickable
          name="nativePlace"
          :value="formData.nativePlace"
          label="籍贯"
          placeholder="点击选择省市区"
          @click="chooseArea('1')"
      />
      <van-field
          readonly
          clickable
          name="presentAddress"
          :value="formData.presentAddress"
          label="现所在地"
          placeholder="点击选择省市区"
          @click="chooseArea('2')"
      />
      <van-field
          type="digit"
          readonly
          v-model="formData.mobile"
          name="mobile"
          label="联系电话"
          placeholder="联系电话"
      />
      <van-field
          v-model="formData.signature"
          rows="2"
          label="个性签名"
          type="textarea"
          maxlength="100"
          placeholder="个性签名"
          show-word-limit
      />
    </van-form>

    <van-popup v-model="showArea" position="bottom">
      <van-area
          :area-list="areaList"
          @confirm="confirmArea"
          @cancel="showArea = false"
      />
    </van-popup>
    <van-calendar v-model="showCalendar" @confirm="confirmBirthdate"/>
  </div>
</template>

<script>
import {areaList} from '@vant/area-data'

export default {
  name: "index",
  data() {
    return {
      formData: {
        uploader: [{url: 'https://img01.yzcdn.cn/vant/leaf.jpg'}],
        nickname: '',
        realName: '',
        birthdate: '',
        age: undefined,
        gender: '',
        nativePlace: '',
        presentAddress: '',
        mobile: '',
        signature: '',
      },
      showCalendar: false,
      showArea: false,
      areaList: [],
      // 地区选择类型：[1]籍贯 [2]现所在地
      clickAreaType: '1'
    };
  },
  mounted() {
    this.areaList = areaList
  },
  methods: {
    backUser() {
      this.$router.replace('/user')
    },
    saveUserPersonal() {
      console.log(this.formData)
      this.$router.replace('/user')
    },
    confirmBirthdate(date) {
      this.formData.birthdate = date.toString()
      this.showCalendar = false;
    },
    confirmArea(value) {
      let area = value
          .filter((item) => !!item)
          .map((item) => item.name)
          .join('/');
      if (this.clickAreaType === '1') {
        this.formData.nativePlace = area
      } else if (this.clickAreaType === '2') {
        this.formData.presentAddress = area
      }
      this.showArea = false;
    },
    chooseArea(type) {
      this.clickAreaType = type
      this.showArea = true
    }
  }
}
</script>

<style scoped>

</style>