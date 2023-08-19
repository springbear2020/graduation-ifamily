<template>
  <div>
    <van-nav-bar title="主页" @click-right="$router.push('/home/message')">
      <template #right>
        <van-badge content="99+">
          <van-icon name="bell" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <!-- 轮播图 -->
    <van-swipe :autoplay="3000" indicator-color="white">
      <van-swipe-item v-for="i in 10" :key="i">{{ i }}</van-swipe-item>
    </van-swipe>

    <!-- 家族动态 -->
    <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
      <van-list isFinished-text="没有更多了" error-text="请求失败，点击重新加载"
                v-model="isLoading" :isFinished="isFinished" @load="onLoad" :error.sync="error">
        <social-moments :data-list="momentList" @post-comment="handlePostComment"/>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script>
import moments from '@/assets/json/moments.json'

export default {
  name: "index",
  data() {
    return {
      momentList: [],
      isRefreshing: false,
      isLoading: false,
      isFinished: false,
      error: false
    }
  },
  mounted() {
    this.momentList = moments
  },
  methods: {
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