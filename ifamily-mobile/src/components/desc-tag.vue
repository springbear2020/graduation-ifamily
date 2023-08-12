<template>
  <!-- 内容描述标签如：[男 234] 或 [年龄 23] 等 -->
  <van-tag :class="{'right': right}" :color="color">
    {{ title }} {{ content }}
  </van-tag>
</template>

<script>
export default {
  name: "desc-tag",
  props: ['title', 'content', 'right', 'color']
}
</script>

<style scoped>
.right {
  margin-right: 1px;
}
</style>