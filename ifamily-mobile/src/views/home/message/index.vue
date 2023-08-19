<template>
  <div>
    <van-nav-bar left-arrow title="消息中心" @click-left="$router.replace('/home')" @click-right="handleClearAll">
      <template #right>
        <van-icon name="sweep" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
      <message-box :data-list="messageList"/>
    </van-pull-refresh>
  </div>
</template>

<script>
import messages from '@/assets/json/messages.json'

export default {
  name: "index",
  data() {
    return {
      active: 0,
      isLoading: false,
      messageList: []
    }
  },
  mounted() {
    this.messageList = messages
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
    handleClearAll() {
      this.$dialog.confirm({
        title: '清除提示',
        message: '您确定要清除所有未读消息吗？',
      }).then(() => {
        this.$toast.success('清除成功')
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>