<template>
  <div>
    <van-tabbar v-model="active">
      <van-tabbar-item icon="home-o" replace to="/home">首页</van-tabbar-item>
      <van-tabbar-item icon="chat-o" replace to="/message">消息</van-tabbar-item>
      <van-tabbar-item icon="cluster-o" replace to="/genealogy">家族</van-tabbar-item>
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