<template>
  <div>
    <van-nav-bar title="添加成员" left-arrow @click-left="$router.replace('/family/tree/0')"/>
    <member-form @submit-form-data="receiveFormData" @hidden-form="$router.replace('/family/tree/0')"/>
  </div>
</template>

<script>
import MemberForm from "@/components/basis/member-form";

export default {
  name: "index",
  components: {MemberForm},
  methods: {
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('添加成功')
    }
  }
}
</script>
