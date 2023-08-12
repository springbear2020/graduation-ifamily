<template>
  <div>
    <van-nav-bar left-arrow title="家族相册" @click-left="backFamily" @click-right="postAlbum">
      <template #right>
        <van-icon name="guide-o"/>
      </template>
    </van-nav-bar>

    <van-tabs v-model="active">
      <van-tab title="相册">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="视频">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
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
    backFamily() {
      this.$router.replace('/family')
    },
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
    postAlbum() {
      this.$toast.success('发布成功')
    }
  }
}
</script>