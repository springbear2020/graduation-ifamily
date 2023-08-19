<template>
  <van-cell center class="flex-cell-container" :border="false">
    <template #title>
      <van-image round width="52" height="52" :src="person.portrait"/>
      <div class="portrait-title-container">
        <p>{{ person.name }}</p>
        <p class="content">{{ person.content }}</p>
      </div>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "portrait-desc",
  props: {
    person: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.flex-cell-container .van-cell__title {
  display: flex;
}

.portrait-title-container {
  margin-left: 8px;
}

.portrait-title-container p {
  margin: 0 0;
}

.portrait-title-container .content {
  color: #969799;
  font-size: 12px;
}
</style>
