<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow @click-left="backFamily" @click-right="createFamily">
      <template #right>
        <van-icon name="add-o"/>
      </template>
    </van-nav-bar>

    <van-tabs v-model="active">
      <!-- 全部家族 -->
      <van-tab title="全部家族">
        <family-info-card v-for="i in 10" :key="i" @click.native="handleSetDefaultFamily"
                          :me-created="i === 3 || i ===1 " :default-family="i === 1"/>
      </van-tab>
      <!-- 我创建的 -->
      <van-tab title="我创建的">
        <van-empty description="无内容"/>
      </van-tab>
      <!-- 我管理的 -->
      <van-tab title="我管理的">
        <van-empty description="无内容"/>
      </van-tab>
      <!-- 可查看的 -->
      <van-tab title="可查看的">
        <van-empty description="无内容"/>
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
      active: '0'
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    handleSetDefaultFamily() {
      this.$router.replace('/family')
    },
    createFamily() {
      this.$router.push('/family/manage/form/2')
    }
  }
}
</script>