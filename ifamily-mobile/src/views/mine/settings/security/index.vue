<template>
  <div>
    <van-nav-bar title="账号安全" left-arrow @click-left="backSettings"/>

    <van-cell-group>
      <van-cell title="UID" value="10278341567348"/>
      <van-cell title="手机" value="13853239825" is-link/>
      <van-cell title="邮箱" value="springbear@163.com" is-link/>
      <van-cell title="登录密码" to="/mine/password/3" is-link/>
      <van-cell title="登录设备" is-link/>
      <van-cell title="注销账号" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    backSettings() {
      this.$router.replace('/mine/settings')
    }
  }
}
</script>