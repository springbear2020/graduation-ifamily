<template>
  <div>
    <van-nav-bar title="消息" @click-right="friendsList">
      <template #right>
        <van-icon name="friends-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-empty description="无内容"/>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    friendsList() {
      this.$router.push('/message/contacts')
    }
  }
}
</script>