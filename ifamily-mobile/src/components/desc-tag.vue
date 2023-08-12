<template>
  <!-- 内容描述标签如：[男 234] 或 [年龄 23] 等 -->
  <van-tag :type="type" :class="{'right': right}" plain>
    {{ text }} {{ number }}
  </van-tag>
</template>

<script>
export default {
  name: "desc-tag",
  props: ['text', 'number', 'right', 'type']
}
</script>

<style scoped>
.right {
  margin-right: 5px;
}
</style>