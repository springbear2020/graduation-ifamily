<template>
  <div>
    <van-nav-bar title="我的亲人" left-arrow @click-left="$router.replace('/mine')"/>

    <!-- 家庭关系 -->
    <div class="van-hairline--top">
      <!-- 使用 :title="''" 达到不展示 “家庭关系” 文字的目的 -->
      <family-relationship @add-type="addRelatives" :title="''"/>
    </div>
    <!-- 信息表单 -->
    <div v-show="showForm">
      <van-divider :style="{color: '#1989fa', borderColor: '#1989fa'}"> {{ subTitle }}</van-divider>
      <member-form @submit-form-data="receiveFormData" @hidden-form="showForm = false"/>
    </div>
  </div>
</template>

<script>
import MemberForm from "@/views/family/manage/members/member-form";

export default {
  name: "index",
  components: {MemberForm},
  data() {
    return {
      active: 1,
      showForm: false,
      subTitle: ''
    }
  },
  methods: {
    addRelatives(type) {
      // [1]父亲 [2]母亲 [3]配偶 [4]子女 [5]同胞
      switch (type) {
        case '1':
          this.subTitle = '添加父亲';
          break;
        case '2':
          this.subTitle = '添加母亲';
          break;
        case '3':
          this.subTitle = '添加配偶';
          break;
        case '4':
          this.subTitle = '添加子女';
          break;
        case '5':
          this.subTitle = '添加同胞';
          break;
        default:
          this.subTitle = '添加亲人'
      }
      this.showForm = true
    },
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('添加成功')
      this.showForm = false
    },
  }
}
</script>