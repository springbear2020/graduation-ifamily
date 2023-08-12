<template>
  <div>
    <van-nav-bar left-arrow title="家族相册" @click-left="backFamily" @click-right="uploadImage">
      <template #right>
        <van-icon name="upgrade"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
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
      isRefreshing: false
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    uploadImage() {
      this.$toast.success('上传图片')
    },
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
  }
}
</script>