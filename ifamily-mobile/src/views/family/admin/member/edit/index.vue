<template>
  <div>
    <van-nav-bar title="编辑成员" left-arrow @click-left="$router.replace('/family/admin/member')"/>
    <member-form @save="handleSave" @hidden-form="$router.replace('/family/admin/member')" :people="people"/>
  </div>
</template>

<script>
import MemberForm from "@/components/business/member-form";

export default {
  name: "index",
  components: {MemberForm},
  data() {
    return {
      people: {}
    }
  },
  mounted() {
    this.initPeople()
  },
  methods: {
    handleSave(formData) {
      this.$api.genealogy.updatePeople(formData).then(() => {
        // 跳转到家族人员信息页
        this.$router.replace(`/family/member/info/2?pid=${this.people.id}`)
        this.$toast.success('更新成功')
        // 更新家族仓库中的家族列表信息
        this.$store.dispatch('genealogy/updateGenealogyStore')
      }).catch(msg => {
        this.$toast({message: msg, position: "bottom"})
      })
    },
    initPeople() {
      // 路由路径中读取 pid
      const pid = this.$route.query.pid
      if (!pid) {
        return
      }

      this.$api.genealogy.getPeople(pid).then(people => {
        this.people = people
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>
