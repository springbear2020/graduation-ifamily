<template>
  <div>
    <van-nav-bar title="登录设备" left-arrow @click-left="$router.replace('/mine/settings/security')"/>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="onLoad">
      <van-cell v-for="item in loginLogList" :key="item.id" :title="item.device" :label="item.loginDatetime">
        <template #default>
          <p>{{ item.location }}</p>
          <p>{{ item.ip }}</p>
        </template>
      </van-cell>
    </van-list>

  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      loading: false,
      finished: false,
      formData: {
        current: 1,
        size: 20
      },
      loginLogList: []
    }
  },
  methods: {
    onLoad() {
      this.$api.user.getUserLoginLog(this.formData).then(logList => {
        this.loginLogList = this.loginLogList.concat(logList)
        this.loading = false
        this.formData.current += 1
      }).catch(() => {
        this.finished = true
      })
    },
  }
}
</script>

<style scoped>
.van-cell__value p {
  margin: 0;
}
</style>
