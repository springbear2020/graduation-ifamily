<template>
  <div>
    <van-nav-bar title="添加亲人" left-arrow @click-left="$router.replace(dstRoute)"/>

    <div v-if="people.me">
      <!-- 肖像、姓名、性别 -->
      <van-grid :border="false" :column-num="1" class="portrait-grid">
        <van-grid-item>
          <template #icon>
            <van-image round height="100" width="100" :src="me.portrait || defaultPortrait"
                       @click="previewImage(me.portrait || defaultPortrait)"/>
          </template>
          <template #text>
            <p>
              {{ me.name }}
              <sex-tag :sex="me.gender"/>
            </p>
          </template>
        </van-grid-item>
      </van-grid>

      <!-- 信息概况 -->
      <van-grid direction="horizontal" :border="false" :column-num="5">
        <van-grid-item>
          <desc-tag title="世代" :content="me.generation" color="#ffc107"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="排行" :content="me.seniority" color="#17a2b8"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="健在" content="" color="#28a745" v-if="!me.deathDate"/>
          <desc-tag title="已逝" content="" color="#6c757d" v-else/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="年龄" :content="me.age" color="#20c997"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="字辈" :content="me.generationName" color="#dc3545"/>
        </van-grid-item>
      </van-grid>

      <!-- 家庭关系 -->
      <family-relationship @add-type="addRelatives" :relation="{father: people.father, mother: people.mother, mates: people.mates,
                             children: people.children, compatriots: people.compatriots}" :hasHusband="!!me.gender"
                           @view-family-member="viewFamilyMember" :border="false"/>

      <!-- 信息表单 -->
      <div v-show="showForm">
        <van-divider :style="{color: '#1989fa', borderColor: '#1989fa'}"> {{ subTitle }}</van-divider>
        <member-form @save="handleSave" @hidden-form="showForm = false" :people="relativePeople" ref="memberForm"/>
      </div>
    </div>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import DescTag from '@/components/tag/desc-tag'
import MemberForm from "@/components/basis/member-form";
import FamilyRelationship from "@/components/business/family-relationship";
import {peopleInfo} from "@/mixin/people-info";

export default {
  name: "index",
  mixins: [peopleInfo],
  components: {SexTag, DescTag, MemberForm, FamilyRelationship},
  data() {
    return {
      subTitle: undefined,
      relativePeople: {
        id: -1,
        gender: 0,
        generation: -1,
        generationName: undefined,
        residence: undefined,
        birthplace: undefined
      },
      relativeType: -1,
    }
  },
  mounted() {
    // [0]家谱树谱
    let type = this.$route.params.type
    if (!(type === '0')) {
      type = '0'
    }
    this.type = type

    this.initPeople()
  },
  computed: {
    dstRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = `/family/tree/0?pid=${this.$route.query.pid}`
      }
      return dst;
    },
  },
  methods: {
    handleSave(formData) {
      if (!(this.relativeType >= 1 && this.relativeType <= 5)) {
        this.$toast({message: '添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞'})
        return
      }
      formData.type = this.relativeType

      this.$api.people.addRelatives(formData).then(() => {
        this.$toast.success(`${this.subTitle}成功`);
        this.showForm = false
        // 清空表单数据
        this.$refs.memberForm.resetForm()
        // 重新查询人员信息
        this.initPeople()
        // 清空仓库家族数据
        this.$store.dispatch('genealogy/logout')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    addRelatives(type) {
      let {id, gender, generation, generationName, residence, birthplace} = this.people.me

      // [1]父亲 [2]母亲 [3]配偶 [4]子女 [5]同胞
      let target = {}
      switch (type) {
        case '1':
          /*
           * 不存在生父、不存在丈夫：允许添加生父
           * 我的生父世代比我小 1
           */
          this.subTitle = '添加生父';
          gender = 0
          generation -= 1
          target = {id, gender, generation, residence, birthplace}
          this.relativeType = 1
          break;
        case '2':
          /*
           * 存在生父、不存在生母、不存在丈夫：允许添加生母
           * 我的生母世代比我小 1，丈夫 ID 为我生父 ID
           */
          this.subTitle = '添加生母';
          gender = 1
          generation -= 1
          target = {id, gender, generation, residence, birthplace}
          this.relativeType = 2
          break;
        case '3':
          /*
           * 不存在丈夫：允许添加配偶
           * 我的妻子世代、字辈与我一致，丈夫 ID 为我的 ID
           */
          this.subTitle = '添加配偶';
          gender = this.people.me.gender === 0 ? 1 : 0
          target = {id, gender, generation, generationName, residence, birthplace}
          this.relativeType = 3
          break;
        case '4':
          /*
           * 1. 存在丈夫：允许添加子女
           * 2. 不存在丈夫、仅有一个妻子：允许添加子女
           */
          if (this.people.mates.length > 1) {
            this.showForm = false
            this.$toast({message: '检测到您存在多个配偶，请通过配偶添加子女，从而明确子女的生母关系', position: 'bottom'})
            return
          }
          this.subTitle = '添加子女';
          generation += 1;
          target = {id, generation, residence, birthplace}
          this.relativeType = 4;
          break;
        case '5':
          /*
           * 不存在丈夫、存在生父、存在生母：允许添加同胞
           * 我的同胞世代、字辈、生父、生母与我一致
           */
          this.subTitle = '添加同胞';
          target = {id, generation, generationName, residence, birthplace}
          this.relativeType = 5
          break;
        default:
          this.relativeType = -1
          this.subTitle = '添加亲人'
      }

      // 将我的部分信息赋值给我的亲人表单数据，避免重复输入
      this.$refs.memberForm.resetForm()
      this.relativePeople = {...target}
      this.showForm = true
    },
  }
}
</script>

<style scoped>
.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}

/deep/ .portrait-grid .van-grid-item__content {
  padding-bottom: 0;
}

.van-grid-item__content p {
  margin: 0;
}

/deep/ .van-tag {
  margin-right: 2px;
}

/deep/ .van-divider {
  margin: 0;
  background-color: #ffffff;
}
</style>
