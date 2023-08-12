<template>
  <div>
    <van-nav-bar
        left-arrow
        title="通知公告"
        @click-left="backHome"
    />

    <van-tabs v-model="active">
      <van-tab title="家族公告">
        <van-empty description="无内容"/>
      </van-tab>
      <van-tab title="系统通知">
        <van-empty description="无内容"/>
      </van-tab>
      <van-tab title="平台公告">
        <van-empty description="无内容"/>
      </van-tab>
      <van-tab title="评论通知">
        <van-empty description="无内容"/>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      active: 1
    }
  },
  methods: {
    backHome() {
      this.$router.replace('/home')
    }
  }
}
</script>