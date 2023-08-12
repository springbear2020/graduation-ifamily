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

    <van-field name="uploader" label="头像">
      <template #input>
        <van-uploader v-model="formData.uploader" max-count="1"/>
      </template>
    </van-field>
    <van-cell-group>
      <van-cell title="昵称" value="你不懂我&我不怪你" is-link/>
      <van-cell title="真实姓名" value="陆平彪" is-link/>
      <van-cell title="出生日期" value="1999-01-01" is-link @click="showCalendar = true"/>
      <van-cell title="联系电话" value="13853289426" is-link/>
      <van-cell title="年龄" value="12"/>
      <van-cell title="性别" value="男" is-link/>
      <van-cell title="籍贯" value="湖北省/武汉市/洪山区" is-link @click="chooseArea('1')"/>
      <van-cell title="现居住地" value="北京市/昌平区" is-link @click="chooseArea('2')"/>
      <van-cell title="个性签名" value="长相思兮长相忆" is-link/>
    </van-cell-group>

    <!-- 地址选择组件 -->
    <van-popup v-model="showArea" position="bottom">
      <van-area
          :area-list="areaList"
          @confirm="confirmArea"
          @cancel="showArea = false"
      />
    </van-popup>
    <!-- 日历选择组件 -->
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