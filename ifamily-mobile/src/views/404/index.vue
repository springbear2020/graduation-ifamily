<template>
  <div>
    <van-nav-bar left-arrow title="404 Not Found" @click-left="backHome"/>

    <van-empty description="您访问的页面不存在" image="https://img01.yzcdn.cn/vant/custom-empty-image.png"/>
  </div>
</template>

<script>
export default {
  name: "404",
  methods: {
    backHome() {
      this.$router.replace('/home')
    }
  }
}
</script>