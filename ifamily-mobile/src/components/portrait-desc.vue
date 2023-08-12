<template>
  <!-- 头像、标题、内容、更多 -->
  <van-cell :border="false">
    <template #title>
      <van-image round width="50" height="50" :src="person.portrait"/>
      <div class="title-container">
        <p class="title">{{ person.name }}</p>
        <p class="van-ellipsis">{{ person.content }}</p>
      </div>
    </template>
    <template #right-icon>
      <van-icon name="ellipsis" v-show="more" @click.stop="$emit('more-operation')"/>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "portrait-desc",
  props: ['person', 'more']
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}
</style>