<template>
  <div>
    <van-nav-bar title="添加亲人" left-arrow @click-left="back"/>

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
    <van-cell title="家庭关系" :border="false">
      <template #label>
        <p>父亲：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(1)">添加</van-button>
        </p>
        <p>母亲：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(2)">添加</van-button>
        </p>
        <p>配偶：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(3)">添加</van-button>
        </p>
        <p>子女：
          <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
          <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
          <people-tag :name="'冯学良'" :sex="0" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(4)">添加</van-button>
        </p>
        <p>同胞：
          <people-tag :name="'冯世元'" :sex="0" :right="true" @click.native="$toast.success('查看人员信息')"/>
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(5)">添加</van-button>
        </p>
      </template>
    </van-cell>

    <!-- 信息表单 -->
    <div v-show="showForm">
      <van-divider :style="{color: '#1989fa', borderColor: '#1989fa'}"> {{ subTitle }}</van-divider>
      <member-form @submit-form-data="receiveFormData"/>
    </div>
  </div>
</template>

<script>
import {ImagePreview} from "vant";
import MemberForm from "@/views/family/manage/members/member-form";

export default {
  name: "index",
  components: {MemberForm},
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
  methods: {
    addRelatives(type) {
      // [1]父亲 [2]母亲 [3]配偶 [4]子女 [5]同胞
      switch (type) {
        case 1:
          this.subTitle = '添加父亲';
          break;
        case 2:
          this.subTitle = '添加母亲';
          break;
        case 3:
          this.subTitle = '添加配偶';
          break;
        case 4:
          this.subTitle = '添加子女';
          break;
        case 5:
          this.subTitle = '添加同胞';
          break;
        default:
          this.subTitle = '添加亲人'
      }
      this.showForm = true
    },
    previewImage() {
      let images = []
      images.push(this.url)
      ImagePreview({images})
    },
    receiveFormData(formData) {
      console.log(formData)
      this.$toast.success('添加成功')
    },
    back() {
      if (this.type === '0') {
        this.$router.replace('/family/tree')
      } else if (this.type === '1') {
        this.$router.replace('/family/manage/members')
      }
    }
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

.van-cell__label p {
  height: 20px;
}

.van-cell__title button {
  height: 20px;
  position: absolute;
}
</style>