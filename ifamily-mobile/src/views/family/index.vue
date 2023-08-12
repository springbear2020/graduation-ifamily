<template>
  <div>
    <van-nav-bar title="家族" @click-right="familyList">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族概况卡片 -->
    <family-info-card @click.native="familyInfo" :disabled="true"/>

    <van-grid square :gutter="8" :column-num="4">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="setting-o" text="家族管理"/>
    </van-grid>

    <van-tabs v-model="active">
      <van-tab title="成员动态">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>

      <van-tab title="家族公告">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>

      <van-tab title="修谱日志">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import FamilyInfoCard from "@/views/family/list/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
  data() {
    return {
      active: 0,
      isRefreshing: false
    };
  },
  methods: {
    familyList() {
      this.$router.push('/family/list')
    },
    familyInfo() {
      this.$router.push('/family/info')
    },
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
  },
}
</script>