<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族概况卡片 -->
    <family-info-card @click.native="$router.push('/family/info')"/>

    <!-- 家族操作宫格 -->
    <van-grid :column-num="3">
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="records" text="修谱记录" to="/family/records"/>
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree/0"/>
      <van-grid-item icon="volume-o" text="家族公告" to="/family/notice"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="setting-o" text="家族管理" to="/family/manage"/>
    </van-grid>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
}
</script>
