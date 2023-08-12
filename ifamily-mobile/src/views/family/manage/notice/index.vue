<template>
  <div>
    <van-nav-bar title="公告管理" left-arrow @click-left="backFamilyManagement" @click-right="publishNotice">
      <template #right>
        <van-icon name="guide-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 头像、标题、内容、时间 -->
    <van-collapse v-model="activeName" accordion>
      <van-swipe-cell v-for="i in 20" :key="i">
        <van-collapse-item :title="`管理员${i}`" :name="i+''" value="2023-02-28 19:46:38">
          族谱作为一个记载以血缘关系为主体的家族世代繁衍和重要人物事迹的特殊图书体裁。
        </van-collapse-item>
        <!-- 左滑置顶、编辑、删除 -->
        <template #right>
          <van-button square type="primary" text="置顶" @click="topNotice"/>
          <van-button square type="warning" text="编辑" @click="editNotice"/>
          <van-button square type="danger" text="删除" @click="deleteNotice"/>
        </template>
      </van-swipe-cell>
    </van-collapse>

    <!-- 公告发布编辑动作面板 -->
    <van-action-sheet v-model="showActionSheet" :title="title">
      <van-field rows="5" autosize type="textarea" maxlength="1000" placeholder="家族公告" show-word-limit
                 v-model="noticeContent"/>
      <van-button type="primary" block @click="handleNotice">确定</van-button>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      activeName: '1',
      showActionSheet: false,
      title: '发布公告',
      noticeContent: ''
    }
  },
  methods: {
    backFamilyManagement() {
      this.$router.replace('/family/manage')
    },
    publishNotice() {
      this.title = '发布公告'
      this.showActionSheet = true
    },
    topNotice() {
      this.$toast.success('置顶公告')
    },
    editNotice() {
      this.title = '编辑公告'
      this.showActionSheet = true
    },
    deleteNotice() {
      this.$toast.fail('删除公告')
    },
    handleNotice() {
      this.$toast.success(this.title)
      this.showActionSheet = false
    }
  }
}
</script>

<style scoped>
.van-swipe-cell button {
  height: 100%;
}
</style>