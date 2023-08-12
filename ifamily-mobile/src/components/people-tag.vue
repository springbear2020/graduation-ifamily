<template>
  <span>
    <!-- [0]male -->
    <van-tag :class="{'right-margin': right, 'bottom-margin': bottom}" type="primary" size="medium" plain
             v-if="sex === 0">
      <van-icon name="contact"/>&nbsp;{{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag :class="{'right-margin': right, 'bottom-margin': bottom}" type="danger" size="medium" plain
             v-else-if="sex === 1">
      <van-icon name="contact"/>&nbsp;{{ name }}
    </van-tag>

    <!-- known -->
    <van-tag :class="{'right-margin': right, 'bottom-margin': bottom}" type="default" size="medium" plain
             v-else>
      <van-icon name="circle"/>&nbsp;{{ name }}
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
.right-margin {
  margin-right: 5px;
}

.bottom-margin {
  margin-bottom: 5px;
}
</style>