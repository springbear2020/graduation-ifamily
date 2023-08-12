<template>
  <div>
    <van-nav-bar title="设置" left-arrow @click-left="backMine" @click-right="logout">
      <template #right>
        <van-icon name="exit" color="#ee0a24" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-cell-group>
      <van-cell title="账号安全" to="/mine/settings/security" is-link/>
      <van-cell title="关于我们" is-link/>
      <van-cell title="联系我们" is-link/>
      <van-cell title="服务协议" is-link/>
      <van-cell title="隐私政策" is-link/>
      <van-cell title="权限管理" is-link/>
      <van-cell title="投诉举报" is-link/>
      <van-cell title="意见反馈" is-link/>
      <van-cell title="版本信息" is-link/>
      <van-cell title="产品介绍" is-link/>
      <van-cell title="使用帮助" is-link/>
      <van-cell title="清空缓存" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    backMine() {
      this.$router.replace('/mine')
    },
    logout() {
      this.$toast.fail('退出登录')
    }
  }
}
</script>