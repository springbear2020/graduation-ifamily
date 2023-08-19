<template>
  <div>
    <van-nav-bar title="登录设备" left-arrow @click-left="$router.replace('/mine/settings/security')"/>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="onLoad">
      <van-cell v-for="item in list" :key="item.id" :title="item.device" :label="item.loginDatetime">
        <template #default>
          <p class="plain-border">{{ item.location }}</p>
          <p class="plain-border">{{ item.ip }}</p>
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
        size: 10
      },
      list: []
    }
  },
  methods: {
    onLoad() {
      this.$api.user.loginLogPageData(this.formData).then(logList => {
        logList.forEach(item => {
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1

        if (!logList || logList.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.finished = true
      })
    }
  }
}
</script>
