<template>
  <div>
    <footer-tab-span/>

    <van-tabbar v-model="active">
      <van-tabbar-item icon="guide-o" replace to="/discover">发现</van-tabbar-item>
      <van-tabbar-item icon="cluster-o" replace to="/family">家族</van-tabbar-item>
      <van-tabbar-item icon="user-circle-o" replace to="/user">我的</van-tabbar-item>
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