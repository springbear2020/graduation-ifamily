<template>
  <!-- 内容描述标签如：[男 234] 或 [年龄 23] 等 -->
  <van-tag :color="color" style="margin-right: 2px">
    {{ title }} {{ content }}
  </van-tag>
</template>

<script>
export default {
  name: "desc-tag",
  props: {
    title: {
      type: String,
      required: true
    },
    content: {
      required: true
    },
    color: {
      type: String,
      required: true
    }
  }
}
</script>
