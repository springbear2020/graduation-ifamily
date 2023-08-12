<template>
  <div>
    <van-nav-bar title="家族" @click-right="myFamilyList">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族信息概况 -->
    <family-brief-info @click.native="familyInfo"/>

    <van-grid square :gutter="8" :column-num="3">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
    </van-grid>

    <van-tabs v-model="active">
      <van-tab title="成员动态">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="家族公告">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="修谱日志">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import FamilyBriefInfo from "@/views/family/list/family-brief-info";

export default {
  name: "index",
  components: {FamilyBriefInfo},
  data() {
    return {
      active: 0,
      isLoading: false,
    };
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
    myFamilyList() {
      this.$router.push('/family/list')
    },
    familyInfo() {
      this.$router.push('/family/info')
    }
  },
}
</script>