<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20" v-if="hasGenealogy"/>
      </template>
    </van-nav-bar>

    <div v-if="hasGenealogy">
      <!-- 家族概况卡片 -->
      <family-info-card @click.native="$router.push('/family/info')" :genealogy="defaultGenealogy"/>

      <!-- 家族操作宫格 -->
      <van-grid :column-num="3" :gutter="8">
        <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree/0"/>
        <van-grid-item icon="friends-o" text="家族成员" to="/family/member"/>
        <van-grid-item icon="setting-o" text="家族管理" to="/family/manage"/>
        <van-grid-item icon="volume-o" text="家族公告" to="/family/notice"/>
        <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
        <van-grid-item icon="video-o" text="家族视频"/>
        <van-grid-item icon="clock-o" text="家族历史"/>
        <van-grid-item icon="notes-o" text="家族大事"/>
        <van-grid-item icon="label-o" text="修谱记录"/>
      </van-grid>

      <van-empty description="敬请期待"/>
    </div>

    <van-empty class="empty-genealogy" v-else :image="require('@/assets/img/empty-image-default.png')">
      <van-button icon="plus" type="info" replace to="/family/form/0">创建家族</van-button>
    </van-empty>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";
import {genealogyList} from "@/mixin/genealogy-list";

export default {
  name: "index",
  components: {FamilyInfoCard},
  mixins: [genealogyList],
  computed: {
    defaultGenealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"]
    }
  }
}
</script>

<style scoped>
.van-grid {
  margin-top: 8px;
}

/deep/ .van-tag {
  margin-right: 2px;
}

.empty-genealogy {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
</style>
