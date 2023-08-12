<template>
  <div>
    <van-nav-bar left-arrow title="消息中心" @click-left="backHome" @click-right="handleClearAll">
      <template #right>
        <van-icon name="sweep" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
      <message-box/>
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
    handleClearAll() {
      this.$toast.fail('清除所有')
    }
  }
}
</script>