<template>
  <div>
    <van-nav-bar title="家族列表" left-arrow @click-left="$router.replace('/family')"/>
    <family-info-card v-for="genealogy in genealogyList" :key="genealogy.id" :genealogy="genealogy"
                      :default-family="genealogy.defaultGenealogy === 1"
                      :me-created="genealogy.creatorUserId === userInfo.id"
                      @click.native="updateDefaultFamily(genealogy.id)"
    />
    <van-empty class="empty" description="空空如也~" v-if="genealogyList.length === 0"
               :image="require('@/assets/img/empty.png')"/>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/basis/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
  computed: {
    defaultGenealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"]
    },
    genealogyList() {
      return this.$store.state.genealogy.genealogyList || []
    },
    userInfo() {
      return this.$store.state.user.user || {}
    }
  },
  methods: {
    updateDefaultFamily(genealogyId) {
      // 用户选择的家族不是默认家族时则更新用户选择家族为其默认家族
      const defaultGenealogyId = this.defaultGenealogy.id
      if (genealogyId !== defaultGenealogyId) {
        this.$api.genealogy.setDefaultGenealogyForUser(genealogyId).then(() => {
          // 切换家族，移除已有的所有家族信息
          this.$store.commit('genealogy/CLEAR_STATE')
          this.$router.replace('/family')
        }).catch(err => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      } else {
        this.$router.replace('/family')
      }
    }
  }
}
</script>

<style scoped>
/deep/ .van-card {
  margin: 8px;
  background-color: #ffffff;
}
</style>
