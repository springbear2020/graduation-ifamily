<template>
  <span>
    <!-- [0]male -->
    <van-icon name="male" color="#007bff" class="iconfont left" size="14" class-prefix="icon" v-if="sex === 0"/>
    <!-- [1]female -->
    <van-icon name="female" color="#e83e8c" class="iconfont left" size="14" class-prefix="icon" v-else-if="sex === 1"/>
    <!-- known -->
    <van-icon name="circle" color="#343a40" class="left" size="14" v-else/>
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
