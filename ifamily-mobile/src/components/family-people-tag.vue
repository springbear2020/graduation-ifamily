<template>
  <span>
    <van-tag v-if="sex === 0" size="medium" class="people-tag" plain type="primary"><van-icon name="contact"/> {{
        name
      }}</van-tag>
    <van-tag v-else size="medium" color="#ff461f" class="people-tag" plain type="warning"><van-icon name="contact"/> {{
        name
      }}</van-tag>
  </span>
</template>

<script>
export default {
  name: "family-people",
  props: ['name', 'sex']
}
</script>

<style scoped>
.people-tag {
  margin-left: 5px;
}
</style>