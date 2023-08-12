<template>
  <div>
    <van-nav-bar title="主页" @click-right="messageCenter">
      <template #right>
        <van-badge content="99+">
          <van-icon name="bell" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <!-- 轮播图 -->
    <van-swipe :autoplay="3000" indicator-color="white">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
      <van-swipe-item>5</van-swipe-item>
    </van-swipe>

    <!-- tab 卡片 -->
    <van-tabs v-model="active">
      <van-tab title="广场">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-list isFinished-text="没有更多了" v-model="isLoading" :isFinished="isFinished" @load="onLoad"
                    :error.sync="error" error-text="请求失败，点击重新加载">
            <social-moments :data-list="dataList" v-on:post-comment="handlePostComment"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>

      <van-tab title="家族">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-list isFinished-text="没有更多了" v-model="isLoading" :isFinished="isFinished" @load="onLoad"
                    :error.sync="error" error-text="请求失败，点击重新加载">
            <social-moments :data-list="dataList" v-on:post-comment="handlePostComment"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import momentsList from '@/assets/json/moments.json'

export default {
  name: "index",
  data() {
    return {
      active: 0,
      dataList: [],
      isRefreshing: false,
      isLoading: false,
      isFinished: false,
      error: false
    }
  },
  mounted() {
    this.dataList = momentsList && momentsList.length > 0 ? momentsList : []
  },
  methods: {
    messageCenter() {
      this.$router.push('/home/message')
    },
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
    onLoad() {
      setTimeout(() => {
        this.isLoading = false;
        this.error = true;
      }, 1000);
    },
    handlePostComment(content, moment) {
      this.$toast.success('评论成功')
      console.log(content, moment)
    }
  },
}
</script>

<style scoped>
/* TODO remove when the image url added */
.van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>