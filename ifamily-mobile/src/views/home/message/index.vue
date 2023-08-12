<template>
  <div>
    <van-nav-bar left-arrow title="消息中心" @click-left="backHome"/>

    <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
      <message-box/>
    </van-pull-refresh>
  </div>
</template>

<script>
import MessageBox from "@/components/message-box";

export default {
  name: "index",
  components: {MessageBox},
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