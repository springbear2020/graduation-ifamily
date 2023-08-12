<template>
  <van-swipe-cell :disabled="disabled">
    <van-card centered tag="李" title="相亲相爱" desc="湖北省/武汉市/江岸区"
              thumb="https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg">
      <template #tags>
        <desc-tag text="总" number="523" type="warning" :right="true"/>
        <desc-tag text="男" number="323" type="primary" :right="true"/>
        <desc-tag text="女" number="200" type="danger" :right="true"/>
        <desc-tag text="生" number="456" type="success" :right="true"/>
        <desc-tag text="逝" number="67" type="default"/>
      </template>

      <template #footer>
        <van-button v-if="defaultFamily" plain disabled size="mini" type="primary">默认家族</van-button>
      </template>
    </van-card>
  </van-swipe-cell>

</template>

<script>
export default {
  name: "family-brief-info",
  props: ['defaultFamily', 'disabled']
}
</script>