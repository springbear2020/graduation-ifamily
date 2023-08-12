<template>
  <span>
    <!-- [0]male -->
    <van-tag :class="{'right': right, 'bottom': bottom}" color="#007bff" size="medium"
             v-if="sex === 0">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag :class="{'right': right, 'bottom': bottom}" color="#e83e8c" size="medium"
             v-else-if="sex === 1">
      <van-icon name="manager-o"/>&nbsp;{{ name }}
    </van-tag>

    <!-- known -->
    <van-tag :class="{'right': right, 'bottom': bottom}" size="medium"
             v-else>
      <van-icon name="circle" color="#6c757d"/>&nbsp;{{ name }}
    </van-tag>
  </span>
</template>

<script>
export default {
  name: "people-tag",
  props: ['name', 'sex', 'right', 'bottom']
}
</script>

<style scoped>
.right {
  margin-right: 5px;
}

.bottom {
  margin-bottom: 5px;
}
</style>