<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow @click-left="$router.replace(backRoute)"/>

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
      <van-cell-group>
        <van-cell :border="false" title="手机" :label="me.phone"/>
        <van-cell :border="false" title="常住地" :label="me.residence"/>
      </van-cell-group>
      <!-- 生于、出生地 -->
      <van-cell-group>
        <van-cell :border="false" title="生于" :label="birthdate"/>
        <van-cell :border="false" title="出生地" :label="me.birthplace"/>
      </van-cell-group>
      <!-- 逝于、埋葬地 -->
      <van-cell-group v-if="!alive">
        <van-cell :border="false" title="逝于" :label="deathDate"/>
        <van-cell :border="false" title="埋葬地" :label="me.burialPlace"/>
      </van-cell-group>

      <!-- 家庭关系 -->
      <van-cell-group>
        <family-relationship :add-button="false" @view-family-member="viewFamilyMember"
                             :relation="{father: people.father, mother: people.mother, mates: people.mates,
                             children: people.children, compatriots: people.compatriots}"
        />
        <van-cell title="关系备注" v-if="me.familyRelationRemark" :border="false" :label="me.familyRelationRemark"
                  class="family-member-remarks"/>
      </van-cell-group>
    </div>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import DescTag from '@/components/tag/desc-tag'
import FamilyRelationship from '@/components/business/family-relationship'
import {solarToLunar} from "@/utils/converter"
import {peopleInfo} from "@/mixin/people-info";

export default {
  name: "index",
  components: {SexTag, FamilyRelationship, DescTag},
  mixins: [peopleInfo],
  mounted() {
    // [0] 家族树谱
    let type = this.$route.params.type
    if (!(type === '0' || type === '1')) {
      type = '0'
    }
    this.type = type

    // 初始化人员数据
    this.initPeople()
  },
  computed: {
    backRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = `/family/tree/0?pid=${this.$route.query.pid}`
      } else if (this.type === '1') {
        dst = `/family/member`
      }
      return dst;
    },
    birthdate() {
      return this.me.birthdate ? solarToLunar(this.me.birthdate, this.me.lunarBirthdate) : ''
    },
    deathDate() {
      return this.me.deathDate ? solarToLunar(this.me.deathDate, this.me.lunarDeathDate) : ''
    }
  },
}
</script>

<style scoped>
.family-member-remarks {
  padding-top: 0;
}

.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}

/deep/ .van-tag {
  margin-right: 2px;
}

/deep/ .portrait-grid .van-grid-item__content {
  padding-bottom: 0;
}

.van-grid-item__content p {
  margin: 0;
}
</style>
