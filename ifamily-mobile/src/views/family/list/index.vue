<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow @click-left="$router.replace('/family')"/>

    <!-- 用户家族列表 -->
    <family-info-card v-for="genealogy in genealogyList" :genealogy="genealogy" :key="genealogy.id"
                      :default-family="genealogy.defaultGenealogy === 1"
                      :me-created="genealogy.creatorUserId === userInfo.id"
                      @click.native="updateDefaultFamily(genealogy.id)"
    />

    <van-empty class="empty" v-if="!genealogyList || genealogyList.length === 0" description="空空如也~"/>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";
import {mapState} from "vuex";

export default {
  name: "index",
  components: {FamilyInfoCard},
  computed: {
    ...mapState({
      genealogyList: state => {
        return state.genealogy.genealogyList
      },
      userInfo: state => {
        return state.user.user
      }
    })
  },
  methods: {
    updateDefaultFamily(genealogyId) {
      // 用户选择的家族不是默认家族时则更新用户选择家族为其默认家族
      const defaultGenealogyId = this.$store.getters["genealogy/defaultGenealogy"].id
      if (genealogyId !== defaultGenealogyId) {
        this.$api.genealogy.setDefaultGenealogyOfUser(genealogyId).then(() => {
          // 切换家族，移除已有的所有家族信息
          this.$store.dispatch('genealogy/logout')
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
/deep/ .van-tag {
  margin-right: 2px;
}

.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}

/deep/ .van-card {
  background-color: #ffffff;
}
</style>
