<template>
  <div>
    <van-nav-bar title="我的动态" left-arrow
                 @click-left="$router.replace('/mine')"
                 @click-right="$router.push('/mine/moment/post/1')"
    >
      <template #right>
        <van-icon name="guide-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh success-text="刷新成功" v-model="refreshing" @refresh="onRefresh">
      <van-list finished-text="没有更多了" v-model="loading" :finished="finished" @load="onLoad">
        <social-moments :data-list="list" :show-permission="true"/>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script>
import SocialMoments from '@/components/business/social-moments'
import {momentDate} from "@/utils/converter"

export default {
  name: "index",
  components: {SocialMoments},
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
      this.$api.social.userMomentPageData(this.formData).then(momentList => {
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

      this.$api.social.userMomentPageData(this.formData).then(momentList => {
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
