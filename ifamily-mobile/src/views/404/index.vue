<template>
  <div>
    <van-nav-bar left-arrow title="404" @click-left="$router.replace('/')"/>

    <van-empty description="很抱歉，您所请求的页面不存在" :image="require('@/assets/img/404.jpg')">
      <van-button round type="info" replace to="/home" icon="arrow-left">返回首页</van-button>
    </van-empty>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>

<style scoped>
.van-empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
</style>
