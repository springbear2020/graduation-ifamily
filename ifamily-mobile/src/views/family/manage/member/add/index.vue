<template>
  <div>
    <van-nav-bar title="添加亲人" left-arrow @click-left="$router.replace(dstRoute)"/>

    <!-- 头像、姓名 -->
    <van-grid :border="false" :column-num="1">
      <van-grid-item>
        <template #icon>
          <van-image round height="100" width="100" :src="url" @click="previewImage"/>
        </template>
        <template #text>
          <p class="portrait-name">冯勇贤</p>
        </template>
      </van-grid-item>
    </van-grid>

    <!-- 家庭关系 -->
    <family-relationship @add-type="addRelatives"/>

    <!-- 信息表单 -->
    <div v-show="showForm">
      <van-divider :style="{color: '#1989fa', borderColor: '#1989fa'}"> {{ subTitle }}</van-divider>
      <member-form @submit-form-data="receiveFormData" @hidden-form="showForm = false"/>
    </div>
  </div>
</template>

<script>
import {ImagePreview} from "vant";
import MemberForm from "@/components/basis/member-form";
import FamilyRelationship from "@/components/business/family-relationship";

export default {
  name: "index",
  components: {MemberForm, FamilyRelationship},
  data() {
    return {
      // [0]家谱树 [1]成员管理
      type: '0',
      showForm: false,
      url: 'https://img01.yzcdn.cn/vant/cat.jpeg',
      subTitle: '',
    }
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    dstRoute() {
      return this.type === '0' ? '/family/tree/0' : (this.type === '1' ? '/family/manage/member' : '/')
    }
  },
  methods: {
    previewImage() {
      let images = []
      images.push(this.url)
      ImagePreview({images})
    },
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('添加成功')
      this.showForm = false
    },
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
  }
}
</script>

<style scoped>
/deep/ .van-grid-item__content {
  padding-bottom: 0;
}

.portrait-name {
  color: #323233;
  font-size: 16px;
  line-height: 24px;
  font-weight: bold;
  margin-bottom: 0;
}
</style>
