<template>
  <div>
    <van-nav-bar title="个人资料" left-arrow @click-left="backMine" @click-right="savePersonalInfo">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 用户头像 -->
    <div class="img-portrait-father">
      <van-uploader v-model="formData.uploader" class="img-portrait" max-count="1"/>
    </div>

    <van-cell-group>
      <van-cell title="昵称" value="你不懂我&我不怪你" is-link/>
      <van-cell title="个性签名" value="长相思兮长相忆" is-link/>
      <van-cell title="真实姓名" value="陆平彪" is-link/>
      <van-cell title="出生日期" value="1999-01-01" is-link @click="showCalendar = true"/>
      <van-cell title="联系电话" value="13853289426" is-link/>
      <van-cell title="年龄" value="12" is-link/>
      <van-cell title="性别" value="男" is-link/>
      <van-cell title="现居住地" value="北京市/昌平区" is-link @click="showArea = true"/>
    </van-cell-group>

    <!-- 地址选择组件 -->
    <van-popup v-model="showArea" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showArea = false"/>
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
        age: 0,
        gender: '',
        presentAddress: '',
        mobile: '',
        signature: '',
      },
      showCalendar: false,
      showArea: false,
      areaList: [],
    };
  },
  mounted() {
    this.areaList = areaList
  },
  methods: {
    backMine() {
      this.$router.replace('/mine')
    },
    savePersonalInfo() {
      console.log(this.formData)
      this.$router.replace('/mine')
    },
    confirmBirthdate(date) {
      this.formData.birthdate = date.toString()
      this.showCalendar = false;
    },
    confirmArea(value) {
      this.formData.presentAddress = value.filter((item) => !!item).map((item) => item.name).join('/')
      this.showArea = false;
    }
  }
}
</script>