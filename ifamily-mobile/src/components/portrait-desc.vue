<template>
  <!-- 姓名、头像、时间、更多 -->
  <van-cell :border="false">
    <template #title>
      <van-image round width="50" height="50" :src="person.portrait"/>
      <div class="desc">
        <p class="name">{{ person.name }}</p>
        <p>{{ person.content }}</p>
      </div>
    </template>
    <template #right-icon>
      <van-icon name="ellipsis"/>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "portrait-desc",
  props: ['person']
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.desc {
  margin-left: 10px;
}

.desc p {
  margin: 0 0;
}

.desc .name {
  font-weight: bold;
}
</style>