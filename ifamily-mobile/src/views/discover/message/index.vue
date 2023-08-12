<template>
  <div>
    <van-nav-bar left-arrow title="消息中心" @click-left="backHome"/>

    <van-tabs v-model="active">
      <van-tab title="通知">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="私信">
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
    backHome() {
      this.$router.replace('/discover')
    },
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
  }
}
</script>