<template>
  <span>
    <!-- [0]male -->
    <van-tag color="#007bff" size="medium" v-if="sex === 0" style="margin-right: 2px">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag color="#e83e8c" size="medium" v-else-if="sex === 1" style="margin-right: 2px">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- known -->
    <van-tag size="medium" v-else style="margin-right: 2px">
      <van-icon name="circle" color="#6c757d"/>&nbsp;{{ name }}
    </van-tag>
  </span>
</template>

<script>
export default {
  name: "people-tag",
  props: {
    name: {
      type: String,
      required: true
    },
    sex: {
      type: Number,
      default: 0
    }
  }
}
</script>
