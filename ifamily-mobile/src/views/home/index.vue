<template>
  <div>
    <van-nav-bar title="主页" @click-right="$router.push('/home/message')">
      <template #right>
        <van-badge dot>
          <van-icon name="envelop-o" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
    </van-swipe>

    <van-tabs>
      <!-- 动态 -->
      <van-tab title="动态">
        <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
          <van-list isFinished-text="没有更多了" error-text="请求失败，点击重新加载"
                    v-model="isLoading" :isFinished="isFinished" @load="onLoad" :error.sync="error">
            <social-moments :data-list="momentList" @post-comment="handlePostComment"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import moments from '@/assets/json/moments.json'
import socialMoments from '@/components/business/social-moments'

export default {
  name: "index",
  components: {
    socialMoments,
  },
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

<style scoped>
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>
