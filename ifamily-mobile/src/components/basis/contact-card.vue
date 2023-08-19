<template>
  <van-cell is-link center>
    <template #title>
      <van-image round width="52" height="52" :src="people.portrait || defaultPortrait"/>
      <p>
        <span>{{ people.name }}</span>
      </p>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "contact-card",
  props: {
    people: {
      type: Object,
      required: true
    }
  },
  computed: {
    defaultPortrait() {
      // 根据性别决定展示的默认肖像
      return this.people.gender === 0 ? 'img/male.jpg' : 'img/female.jpg'
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell__title {
  display: flex;
}

.van-cell__title p {
  margin-left: 8px;
}
</style>
