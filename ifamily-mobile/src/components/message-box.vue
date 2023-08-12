<template>
  <van-cell-group>
    <van-swipe-cell v-for="i in 20" :key="i">
      <!-- 右滑收藏 -->
      <template #left>
        <van-button square type="info" icon="star-o" @click="starContacts"/>
      </template>

      <!-- 徽标、头像、标题、内容、时间 -->
      <van-cell center @click="handleViewMessage">
        <template #title>
          <van-badge :content="9">
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
          </van-badge>
          <div class="title-container">
            <p class="title">光头勇</p>
            <p class="van-ellipsis">自动省略多余的文本...</p>
          </div>
        </template>

        <template #default>
          <span>2023-02-23 18:55</span>
        </template>
      </van-cell>

      <!-- 左滑置顶、已读、删除 -->
      <template #right>
        <van-button square type="primary" text="置顶" @click="pinTop"/>
        <van-button square type="warning" text="已读" @click="messageRead"/>
        <van-button square type="danger" text="删除" @click="sessionDelete"/>
      </template>
    </van-swipe-cell>
  </van-cell-group>
</template>

<script>
export default {
  name: "message-box",
  methods: {
    handleViewMessage() {
      this.$toast.fail('查看消息')
    },
    pinTop() {
      this.$toast.success('消息置顶')
    },
    messageRead() {
      this.$toast.success('已读')
    },
    sessionDelete() {
      this.$toast.fail('删除会话')
    },
    starContacts() {
      this.$toast.success('收藏联系人')
    }
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.van-swipe-cell button {
  height: 100%;
}
</style>