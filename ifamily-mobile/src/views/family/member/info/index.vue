<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow @click-left="$router.replace(backRoute)"/>

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

      <!-- 手机、常住地 -->
      <van-cell title="手机" :label="me.phone" class="top"/>
      <van-cell title="常住地" :label="me.residence"/>
      <!-- 生于、出生地 -->
      <van-cell title="生于" :label="birthdate" class="top"/>
      <van-cell title="出生地" :label="me.birthplace"/>
      <!-- 逝于、埋葬地 -->
      <van-cell title="逝于" :label="deathDate" v-if="!alive" class="top"/>
      <van-cell title="埋葬地" :label="me.burialPlace" v-if="!alive"/>

      <!-- 家庭关系 -->
      <family-relationship :add-button="false" @view-family-member="viewFamilyMember" class="top"
                           :relation="familyRelation"
      />
      <van-cell title="关系备注" :label="me.familyRelationRemark" v-if="me.familyRelationRemark"/>
    </div>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import DescTag from '@/components/tag/desc-tag'
import FamilyRelationship from '@/components/basis/family-relationship'
import {solarToLunar} from "@/utils/converter"
import {peopleDetails} from "@/mixin/people-details";
import {defaultPortrait} from "@/mixin/common-utils";
import {previewImage} from "@/mixin/common-utils";

export default {
  name: "index",
  components: {SexTag, FamilyRelationship, DescTag},
  mixins: [peopleDetails, defaultPortrait, previewImage],
  data() {
    return {
      type: '0'
    }
  },
  mounted() {
    // [0] 家族树谱 [1]成员列表 [2]成员管理
    let type = this.$route.params.type
    if (!(type === '0' || type === '1' || type === '2')) {
      type = '0'
    }
    this.type = type

    this.initPeople()
  },
  computed: {
    backRoute() {
      let back = '/'
      if (this.type === '0') {
        back = `/family/tree?pid=${this.$route.query.pid}`
      } else if (this.type === '1') {
        back = '/family/member'
      } else if (this.type === '2') {
        back = '/family/admin/member'
      }
      return back;
    },
    birthdate() {
      return this.me.birthdate ? solarToLunar(this.me.birthdate, this.me.lunarBirthdate) : ''
    },
    deathDate() {
      return this.me.deathDate ? solarToLunar(this.me.deathDate, this.me.lunarDeathDate) : ''
    },
    familyRelation() {
      return {
        father: this.people.father,
        mother: this.people.mother,
        mates: this.people.mates,
        children: this.people.children,
        compatriots: this.people.compatriots
      }
    }
  }
}
</script>
