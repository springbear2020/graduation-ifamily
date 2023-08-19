<template>
  <div>
    <van-nav-bar title="编辑成员" left-arrow @click-left="$router.replace('/family/manage/members')"/>
    <member-form @submit-form-data="receiveFormData"/>
  </div>
</template>

<script>
import MemberForm from "@/views/family/manage/members/member-form";

export default {
  name: "index",
  components: {MemberForm},
  methods: {
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('编辑成功')
    }
  }
}
</script>