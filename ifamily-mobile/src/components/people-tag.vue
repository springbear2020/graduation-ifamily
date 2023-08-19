<template>
  <span>
    <!-- [0]male -->
    <van-tag color="#007bff" size="medium"
             :class="{'right': right, 'bottom': bottom}" v-if="sex === 0">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag color="#e83e8c" size="medium"
             :class="{'right': right, 'bottom': bottom}" v-else-if="sex === 1">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- known -->
    <van-tag size="medium"
             :class="{'right': right, 'bottom': bottom}" v-else>
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
    },
    right: {
      type: Boolean,
      default: true
    },
    bottom: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style scoped>
.right {
  margin-right: 1px;
}

.bottom {
  margin-bottom: 1px;
}
</style>