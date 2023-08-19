<template>
  <div>
    <van-nav-bar title="主页">
      <template #right>
        <van-badge>
          <van-icon name="envelop-o" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <van-swipe :autoplay="3000" indicator-color="#ffffff" class="my-swipe">
      <van-swipe-item>百</van-swipe-item>
      <van-swipe-item>家</van-swipe-item>
      <van-swipe-item>谱</van-swipe-item>
      <van-swipe-item>欢</van-swipe-item>
      <van-swipe-item>迎</van-swipe-item>
      <van-swipe-item>您</van-swipe-item>
    </van-swipe>

    <van-tabs>
      <van-tab title="动态">
        <van-pull-refresh success-text="刷新成功" v-model="refreshing" @refresh="onRefresh">
          <van-list finished-text="没有更多了" v-model="loading" :finished="finished" @load="onLoad">
            <social-moments :data-list="list"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import socialMoments from '@/components/business/social-moments'
import {momentDate} from "@/utils/converter";

export default {
  name: "index",
  components: {
    socialMoments,
  },
  data() {
    return {
      refreshing: false,
      finished: false,
      loading: false,
      formData: {
        current: 1,
        size: 10,
      },
      list: []
    }
  },
  methods: {
    onLoad() {
      this.$api.social.genealogyMomentPageData(this.formData).then(momentList => {
        momentList.forEach(item => {
          item.scheduled = momentDate(item.scheduled)
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1

        if (!momentList || momentList.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.finished = true
      })
    },
    onRefresh() {
      this.list = []
      this.formData.current = 1
      this.$api.social.genealogyMomentPageData(this.formData).then(momentList => {
        momentList.forEach(item => {
          item.scheduled = momentDate(item.scheduled)
          this.list.push(item)
        })
        this.refreshing = false
        this.formData.current += 1

        if (!momentList || momentList.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.refreshing = false
        this.finished = true
      })
    }
  }
}
</script>

<style scoped>
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 200px;
  text-align: center;
  background-color: #6f42c1;
}
</style>
