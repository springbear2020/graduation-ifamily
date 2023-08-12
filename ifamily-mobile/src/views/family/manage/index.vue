<template>
  <div>
    <van-nav-bar title="家族管理" left-arrow @click-left="backFamily" @click-right="deleteFamily">
      <template #right>
        <van-icon name="delete-o"/>
      </template>
    </van-nav-bar>

    <van-cell-group>
      <van-cell title="家族信息" is-link to="/family/manage/form/1"/>
      <van-cell title="成员管理" is-link to="/family/manage/members"/>
      <van-cell title="字辈管理" is-link to="/family/manage/seniority"/>
      <van-cell title="公告管理" is-link/>
      <van-cell title="权限设置" is-link/>
      <van-cell title="访问控制" is-link/>
      <van-cell title="修委会" is-link/>
    </van-cell-group>
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
    deleteFamily() {
      this.$dialog.confirm({
        title: '删除家族',
        message: '您确定要删除《相亲相爱》这个家族吗？',
      }).then(() => {
        this.$toast.success('删除家族成功')
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>