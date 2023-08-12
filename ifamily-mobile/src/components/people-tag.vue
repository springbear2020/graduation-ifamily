<template>
  <span>
    <!-- [0]male -->
    <van-tag v-if="sex === 0" :class="{'people-tag-margin-right': right, 'people-tag-margin-bottom': bottom}"
             plain type="primary" size="medium">
      <van-icon name="contact"/> {{ name }}
    </van-tag>

    <!-- [1]female -->
    <van-tag v-else-if="sex === 1" :class="{'people-tag-margin-right': right, 'people-tag-margin-bottom': bottom}"
             plain type="danger" size="medium">
      <van-icon name="contact"/> {{ name }}
    </van-tag>

    <!-- known -->
    <van-tag v-else :class="{'people-tag-margin-right': right, 'people-tag-margin-bottom': bottom}"
             plain type="default" size="medium">
      <van-icon name="circle"/> {{ name }}
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
.people-tag-margin-right {
  margin-right: 5px;
}

.people-tag-margin-bottom {
  margin-bottom: 5px;
}
</style>