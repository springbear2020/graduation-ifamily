<template>
  <div>
    <van-nav-bar left-arrow title="消息中心" @click-left="backHome"/>

    <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
      <van-empty description="无内容"/>
    </van-pull-refresh>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      active: 0,
      isLoading: false
    }
  },
  methods: {
    backHome() {
      this.$router.replace('/home')
    },
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
  }
}
</script>