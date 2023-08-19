<template>
  <!-- 家族信息概况 -->
  <van-card centered tag="李" title="相亲相爱" desc="湖北省/武汉市/江岸区"
            thumb="https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg">
    <template #tags>
      <desc-tag title="总" content="523" color="#fd7e14"/>
      <desc-tag title="男" content="323" color="#007bff"/>
      <desc-tag title="女" content="200" color="#e83e8c"/>
      <desc-tag title="生" content="423" color="#28a745"/>
      <desc-tag title="逝" content="100" color="#6c757d"/>
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
