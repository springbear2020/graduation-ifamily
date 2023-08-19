<template>
  <div>
    <van-nav-bar title="登录设备" left-arrow @click-left="$router.replace('/mine/security')"/>

    <van-cell title="LAPTOP-EGI3GE88" label="2023-02-13 09:31:23" v-for="i in 10" :key="i">
      <template #default>
        <p>192.168.43.225</p>
        <p class="van-ellipsis">湖北省武汉市</p>
      </template>
    </van-cell>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>

<style scoped>
.van-cell__value p {
  margin: 0;
}
</style>