<template>
  <div>
    <van-nav-bar title="账号安全" left-arrow @click-left="$router.replace('/mine/settings')"/>

    <van-cell title="UID" :value="user.username" is-link to="/mine/settings/security/uid"/>
    <van-cell title="手机号" :value="user.phone" is-link to="/mine/settings/security/form/1"/>
    <van-cell title="邮箱地址" :value="user.email" is-link to="/mine/settings/security/form/0"/>
    <van-cell title="修改密码" is-link to="/user/reset/2"/>
    <van-cell title="登录设备" is-link to="/mine/settings/security/devices"/>
    <van-cell title="注销账号" is-link @click="$toast('注销账号')"/>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "index",
  computed: {
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    })
  },
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 16px 16px;
}
</style>
