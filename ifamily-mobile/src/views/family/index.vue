<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族概况卡片 -->
    <family-info-card @click.native="$router.push('/family/info')" :disabled="true"/>

    <!-- 家族操作宫格 -->
    <van-grid :column-num="3">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="volume-o" text="家族公告" to="/family/notice"/>
      <van-grid-item icon="records" text="修谱记录" to="/family/records"/>
      <van-grid-item icon="setting-o" text="家族管理" to="/family/manage"/>
    </van-grid>

    <van-tabs >
      <!-- 成员动态 -->
      <van-tab title="成员动态">
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
import FamilyInfoCard from "@/views/family/list/family-info-card";
import moments from '@/assets/json/moments.json'

export default {
  name: "index",
  components: {FamilyInfoCard},
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