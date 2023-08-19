<template>
  <div>
    <van-nav-bar title="光头勇" left-arrow @click-left="$router.replace('/home/message')"/>

    <!-- 会话内容 -->
    <van-tabbar>
      <van-field v-model="content" left-icon="smile-o" :right-icon="rightIcon"/>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      content: '',
      rightIcon: 'add-o'
    }
  },
  watch: {
    content(newVal) {
      this.rightIcon = newVal ? 'guide-o' : 'add-o';
    }
  }
}
</script>

<style scoped>
/deep/ .van-field__control {
  background-color: #f7f8fa;
}
</style>