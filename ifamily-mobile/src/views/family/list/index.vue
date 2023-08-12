<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow @click-left="backFamily" @click-right="createFamily">
      <template #right>
        <van-icon name="add-o"/>
      </template>
    </van-nav-bar>

    <family-info-card :me-created="i === 3 || i ===1 " :defaultFamily="i === 1" v-for="i in 10" :key="i"
                       @click.native="handleSetDefaultFamily"/>
  </div>
</template>

<script>
import FamilyInfoCard from "@/views/family/list/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    handleSetDefaultFamily() {
      this.$router.replace('/family')
    },
    createFamily() {
      this.$router.push('/family/manage/create')
    }
  }
}
</script>