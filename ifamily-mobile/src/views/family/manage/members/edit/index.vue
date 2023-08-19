<template>
  <div>
    <van-nav-bar title="编辑成员" left-arrow @click-left="$router.replace(dstRoute)"/>
    <member-form @submit-form-data="receiveFormData" @hidden-form="$router.replace(dstRoute)"/>
  </div>
</template>

<script>
import MemberForm from "@/views/family/manage/members/member-form";

export default {
  name: "index",
  components: {MemberForm},
  data() {
    return {
      // [0]家谱树 [1]成员管理 [2]我的
      type: '0'
    }
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    dstRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = '/family/tree/0'
      } else if (this.type === '1') {
        dst = '/family/manage/members'
      } else if (this.type === '2') {
        dst = '/family/members/people/3'
      }
      return dst
    }
  },
  methods: {
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('编辑成功')
    }
  }
}
</script>