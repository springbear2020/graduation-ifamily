<template>
  <div>
    <van-nav-bar title="编辑成员" left-arrow @click-left="$router.replace(dstRoute)"/>
    <member-form @save="handleSave" @hidden-form="$router.replace(dstRoute)" :people="people"/>
  </div>
</template>

<script>
import MemberForm from "@/components/basis/member-form";

export default {
  name: "index",
  components: {MemberForm},
  data() {
    return {
      // [0]家谱树谱 [1]树谱人员信息 [2]列表人员信息
      type: '0',
      people: {}
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0' || type === '1' || type === '2')) {
      type = '0'
    }
    this.type = type

    this.initPeople()
  },
  computed: {
    dstRoute() {
      let dst = '/'
      const pid = this.$route.query.pid
      if (this.type === '0') {
        dst = `/family/tree/0?pid=${pid}`
      } else if (this.type === '1') {
        dst = `/family/member/info/0?pid=${pid}`
      } else if (this.type === '2') {
        dst = `/family/member/info/1?pid=${pid}`
      }
      return dst;
    }
  },
  methods: {
    handleSave(formData) {
      this.$api.people.updatePeople(formData).then(() => {
        // 跳转到家族人员信息页
        this.$router.replace(`/family/member/info/0?pid=${formData.id}`)
        this.$toast.success('更新成功')
        // 清空仓库家族数据
        this.$store.dispatch('genealogy/logout')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: "bottom"})
      })
    },
    initPeople() {
      // 路由路径中读取 pid
      const pid = this.$route.query.pid
      if (!pid) {
        return
      }

      this.$api.people.getPeopleById({peopleId: pid}).then(people => {
        this.people = people
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>
