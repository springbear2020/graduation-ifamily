<template>
  <div>
    <van-nav-bar
        title="设置"
        left-arrow
        @click-left="backUser"
    />

    <van-cell-group>
      <van-cell title="账号安全" to="/user/settings/account" is-link/>
      <van-cell title="关于我们" is-link/>
      <van-cell title="联系我们" is-link/>
      <van-cell title="用户协议" is-link/>
      <van-cell title="隐私政策" is-link/>
      <van-cell title="权限管理" is-link/>
      <van-cell title="投诉举报" is-link/>
      <van-cell title="意见反馈" is-link/>
      <van-cell title="版本信息" is-link/>
      <van-cell title="清空缓存" is-link/>
    </van-cell-group>

    <van-tabbar>
      <van-button block color="linear-gradient(to right, #ff6034, #ee0a24)">退出登录</van-button>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    backUser() {
      this.$router.replace('/user')
    }
  }
}
</script>