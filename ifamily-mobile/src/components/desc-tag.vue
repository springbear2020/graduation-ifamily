<template>
  <!-- 内容描述标签如：[男 234] 或 [年龄 23] 等 -->
  <van-tag :class="{'right': right}" :color="color">
    {{ text }} {{ number }}
  </van-tag>
</template>

<script>
export default {
  name: "desc-tag",
  props: ['text', 'number', 'right', 'color']
}
</script>

<style scoped>
.right {
  margin-right: 1px;
}
</style>