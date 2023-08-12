<template>
  <van-cell-group>
    <van-swipe-cell v-for="i in 20" :key="i">
      <template #left>
        <van-button square type="info" text="收藏"/>
      </template>

      <van-cell center @click="handleViewMessage">
        <template #title>
          <van-badge :content="9">
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
          </van-badge>
          <div class="desc">
            <p class="name">光头勇</p>
            <p class="van-ellipsis">自动省略多余的文本...</p>
          </div>
        </template>

        <template #default>
          <span>23-02-23 18:55:23</span>
        </template>
      </van-cell>

      <template #right>
        <van-button square type="primary" text="置顶"/>
        <van-button square type="warning" text="已读"/>
        <van-button square type="danger" text="删除"/>
      </template>
    </van-swipe-cell>
  </van-cell-group>
</template>

<script>
export default {
  name: "message-box",
  methods: {
    handleViewMessage() {
      this.$toast.success('查看消息')
    }
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.desc {
  margin-left: 10px;
}

.desc p {
  margin: 0 0;
}

.desc .name {
  font-weight: bold;
}

.van-swipe-cell button {
  height: 100%;
}
</style>