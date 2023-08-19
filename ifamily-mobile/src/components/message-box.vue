<template>
  <van-cell-group>
    <van-swipe-cell v-for="msg in dataList" :key="msg.id">
      <!-- 左滑添加联系人到通讯录 -->
      <template #left>
        <van-button square type="info" icon="like-o" @click="$toast.success('添加联系人')"/>
      </template>

      <!-- 徽标、头像、标题、内容、时间 -->
      <van-cell center @click="$toast.fail('查看消息')">
        <template #title>
          <van-badge :content="msg.unread" v-if="msg.unread">
            <van-image round width="50" height="50" :src="msg.portrait"/>
          </van-badge>
          <van-image round width="50" height="50" :src="msg.portrait" v-else/>
          <div class="title-container">
            <p class="title">{{ msg.name }}</p>
            <p class="van-ellipsis">{{ msg.content }}</p>
          </div>
        </template>

        <template #default>
          <span>{{ msg.datetime }}</span>
        </template>
      </van-cell>

      <!-- 左滑置顶、已读、删除 -->
      <template #right>
        <van-button square type="primary" text="置顶" @click="$toast.success('消息置顶')"/>
        <van-button square type="warning" text="已读" @click="$toast.success('已读')"/>
        <van-button square type="danger" text="删除" @click="$toast.fail('删除会话')"/>
      </template>
    </van-swipe-cell>
  </van-cell-group>
</template>

<script>
export default {
  name: "message-box",
  props: {
    dataList: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}
</style>