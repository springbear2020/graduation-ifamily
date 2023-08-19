<template>
  <div>
    <van-nav-bar title="消息" @click-right="$toast('清除未读消息')">
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
import messageBox from './message-box'

export default {
  name: "index",
  components: {messageBox},
  data() {
    return {
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
    }
  }
}
</script>