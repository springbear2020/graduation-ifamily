<template>
  <span>
    <!-- [0]male -->
    <van-tag color="#007bff" size="medium" class="right" v-if="sex === 0">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag color="#e83e8c" size="medium" class="right" v-else-if="sex === 1">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- known -->
    <van-tag color="#343a40" size="medium" class="right" v-else>
      <van-icon name="circle"/>&nbsp;{{ name }}
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
