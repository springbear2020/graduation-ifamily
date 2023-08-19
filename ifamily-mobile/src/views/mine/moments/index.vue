<template>
  <div>
    <van-nav-bar title="我的动态" left-arrow @click-left="$router.replace('/mine')"
                 @click-right="$router.push('/mine/moments/post')">
      <template #right>
        <van-icon name="guide-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 动态列表 -->
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
    };
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
      this.$toast('评论成功')
      console.log(content, moment)
    }
  }
}
</script>