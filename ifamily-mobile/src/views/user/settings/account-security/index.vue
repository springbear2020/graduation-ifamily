<template>
  <div>
    <van-nav-bar
        title="账号安全"
        left-arrow
        @click-left="backSettings"
    />

    <van-cell-group>
      <van-cell title="UID" value="102341348"/>
      <van-cell title="手机" is-link/>
      <van-cell title="邮箱" is-link/>
      <van-cell title="登录密码" to="/user/password?type=3" is-link/>
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
      this.$router.replace('/user/settings')
    }
  }
}
</script>