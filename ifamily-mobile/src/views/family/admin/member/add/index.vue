<template>
  <div>
    <van-nav-bar title="添加亲人" left-arrow @click-left="$router.replace('/family/admin/member')"/>

    <div v-if="people.me">
      <!-- 肖像、姓名、性别 -->
      <van-grid :column-num="1">
        <van-grid-item>
          <template #icon>
            <van-image round height="100" width="100" :src="me.portrait || defaultPortrait(me.gender)"
                       @click="previewImage(me.portrait || defaultPortrait(me.gender))"/>
          </template>
          <template #text>
            <p class="plain-border">
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
          <desc-tag title="健在" content="" color="#28a745" v-if="alive"/>
          <desc-tag title="已逝" content="" color="#6c757d" v-else/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="年龄" :content="age" color="#20c997"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="字辈" :content="me.generationName" color="#dc3545"/>
        </van-grid-item>
      </van-grid>

      <!-- 家庭关系 -->
      <family-relationship @add-type="addRelatives" :relation="familyRelation" :hasHusband="!!me.gender"
                           @view-family-member="viewFamilyMember" :border="false"
      />

      <!-- 信息表单 -->
      <div v-show="showForm">
        <van-divider :style="{color: '#1989fa', borderColor: '#1989fa'}"> {{ subTitle }}</van-divider>
        <member-form @save="handleSave" @hidden-form="showForm = false" :people="relativePeople" :anonymous="true"
                     ref="memberForm"
        />
      </div>
    </div>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import DescTag from '@/components/tag/desc-tag'
import MemberForm from "@/components/business/member-form";
import FamilyRelationship from "@/components/basis/family-relationship";
import {peopleDetails} from "@/mixin/people-details";
import {defaultPortrait} from "@/mixin/common-utils";
import {previewImage} from "@/mixin/common-utils";

export default {
  name: "index",
  mixins: [peopleDetails, defaultPortrait, previewImage],
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
    this.initPeople()
  },
  computed: {
    familyRelation() {
      return {
        father: this.people.father,
        mother: this.people.mother,
        mates: this.people.mates,
        children: this.people.children,
        compatriots: this.people.compatriots
      }
    }
  },
  methods: {
    handleSave(formData) {
      this.$api.genealogy.addRelative(formData, {relativeType: this.relativeType}).then(() => {
        // 添加亲人成功，重新查询当前人员详细信息
        this.showForm = false
        this.$refs.memberForm.resetForm()
        this.initPeople()
        this.$toast.success(`${this.subTitle}成功`)

        // 更新家族仓库中的家族列表信息
        this.$store.dispatch('genealogy/updateGenealogyStore')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
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
    }
  }
}
</script>

<style scoped>
/deep/ .portrait-grid .van-grid-item__content {
  padding-bottom: 0;
}

/deep/ .van-divider {
  margin: 0;
  background-color: #ffffff;
}
</style>
