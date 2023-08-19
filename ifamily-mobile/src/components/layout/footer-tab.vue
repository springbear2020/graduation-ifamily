<template>
  <div>
    <van-tabbar v-model="active" :placeholder="true" route>
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
