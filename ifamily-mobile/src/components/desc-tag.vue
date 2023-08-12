<template>
  <!-- 内容描述标签如：[男 234] -->
  <van-tag plain :type="type" :class="{'desc-tag-margin-right': right}" :size="size">
    {{ text }} {{ number }}
  </van-tag>
</template>

<script>
export default {
  name: "desc-tag",
  props: ['text', 'number', 'type', 'right', 'size']
}
</script>

<style scoped>
.desc-tag-margin-right {
  margin-right: 5px;
}
</style>