<template>
  <!-- 家族信息概况 -->
  <van-card centered :tag="genealogy.surname" :title="genealogy.title" :desc="genealogy.address"
            :thumb="genealogy.cover">
    <template #tags>
      <desc-tag title="总" :content="genealogy.total" color="#fd7e14"/>
      <desc-tag title="男" :content="genealogy.male" color="#007bff"/>
      <desc-tag title="女" :content="genealogy.female" color="#e83e8c"/>
      <desc-tag title="生" :content="genealogy.alive" color="#28a745"/>
      <desc-tag title="逝" :content="genealogy.death" color="#6c757d"/>
    </template>

    <template #footer>
      <van-button v-if="defaultFamily" disabled size="mini" type="info">默认家族</van-button>
      <van-button v-if="meCreated" disabled size="mini" type="primary">我创建的</van-button>
    </template>
  </van-card>
</template>

<script>
import DescTag from '@/components/tag/desc-tag'

export default {
  name: "family-info-card",
  components: {DescTag},
  props: {
    genealogy: {
      type: Object,
      required: true
    },
    defaultFamily: {
      type: Boolean,
      default: false
    },
    meCreated: {
      type: Boolean,
      default: false
    }
  }
}
</script>
