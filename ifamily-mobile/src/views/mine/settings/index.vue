<template>
  <div>
    <van-nav-bar title="设置" left-arrow @click-left="$router.replace('/mine')"/>

    <van-cell title="账号安全" icon="shield-o" is-link to="/mine/settings/security"/>
    <!-- 清空缓存、服务协议、隐私政策 -->
    <van-cell title="权限管理" icon="browsing-history-o" is-link/>
    <van-cell title="投诉举报" icon="warn-o" is-link class="top"/>
    <van-cell title="意见反馈" icon="smile-o" is-link/>
    <van-cell title="使用帮助" icon="question-o" is-link class="top"/>
    <!-- 联系我们、版本信息 -->
    <van-cell title="关于我们" icon="info-o" is-link/>

    <div class="block-button-container">
      <van-button color="linear-gradient(to right, #ff6034, #ee0a24)" block @click="signOut">退出登录</van-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    signOut() {
      this.$dialog.confirm({
        title: '退出登录',
        message: '您确定要退出吗？您可以随时再次登录。'
      }).then(() => {
        // 退出登录，移除仓库信息，跳转到登录页
        this.$store.dispatch('user/logout')
        this.$store.commit('genealogy/CLEAR_STATE')
        this.$router.replace('/user/login')
      }).catch(() => {
      });
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}
</style>
