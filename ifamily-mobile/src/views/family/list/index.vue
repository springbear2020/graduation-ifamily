<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow
                 @click-left="$router.replace('/family')"
                 @click-right="$router.push('/family/manage/form/0')"
    >
      <template #right>
        <van-icon v-if="hasGenealogy" name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 用户家族列表 -->
    <family-info-card v-for="genealogy in genealogyList" :genealogy="genealogy" :key="genealogy.id"
                      :default-family="genealogy.defaultGenealogy === 1"
                      :me-created="genealogy.creatorUserId === userInfo.id"
                      @click.native="updateDefaultFamily(genealogy.id)"
    />
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";
import {mapState} from "vuex";
import {genealogyList} from "@/mixin/genealogy-list";

export default {
  name: "index",
  mixins: [genealogyList],
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
      if (genealogyId !== this.$store.getters["genealogy/defaultGenealogy"].id) {
        this.$api.genealogy.setDefaultGenealogyOfUser(genealogyId).then(() => {
          // 移除 store 中的家族列表信息，查询最新的家族信息
          this.$store.dispatch('genealogy/listGenealogyList')
          this.$router.replace('/family')
        }).catch(err => {
          this.$toast.fail(err.data || err.desc)
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
</style>
