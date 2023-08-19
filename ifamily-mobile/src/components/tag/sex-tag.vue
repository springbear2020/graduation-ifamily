<template>
  <span>
    <!-- [0]male -->
    <van-icon v-if="sex === 0" name="male" color="#007bff" class="iconfont" class-prefix="icon" size="14"/>
    <!-- [1]female -->
    <van-icon v-else-if="sex === 1" name="female" color="#e83e8c" class="iconfont" class-prefix="icon" size="14"/>
    <!-- known -->
    <van-icon v-else name="circle" color="#6c757d" size="14"/>
  </span>
</template>

<script>
export default {
  name: "sex-tag",
  props: {
    sex: {
      type: Number,
      default: 0
    }
  }
}
</script>

<style scoped>
.iconfont {
  margin-left: 2px;
}
</style>
