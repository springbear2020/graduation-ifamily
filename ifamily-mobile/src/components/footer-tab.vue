<template>
  <div>
    <!-- 解决底部导航遮挡主体内容的问题 -->
    <div class="footer-placeholder"/>

    <van-tabbar v-model="active">
      <van-tabbar-item icon="home-o" replace to="/home">主页</van-tabbar-item>
      <van-tabbar-item icon="cluster-o" replace to="/family">家族</van-tabbar-item>
      <van-tabbar-item icon="user-o" replace to="/mine">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: "footer-tab",
  data() {
    return {
      active: 0
    }
  }
}
</script>

<style scoped>
.footer-placeholder {
  padding-bottom: calc(50px + env(safe-area-inset-bottom));
}
</style>