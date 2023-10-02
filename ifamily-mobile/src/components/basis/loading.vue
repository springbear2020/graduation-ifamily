<template>
  <van-overlay :show="isLoading" :custom-style="{'background-color': 'transparent'}">
    <div class="wrapper">
      <van-loading vertical color="#6f42c1" type="spinner">加载中...</van-loading>
    </div>
  </van-overlay>
</template>

<script>
export default {
  name: 'loading',
  computed: {
    isLoading() {
      return this.$store.state.loading.isLoading
    }
  }
}
</script>

<style scoped>
.wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
</style>
