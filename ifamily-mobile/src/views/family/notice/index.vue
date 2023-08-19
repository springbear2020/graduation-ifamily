<template>
  <div>
    <van-nav-bar title="家族公告" left-arrow @click-left="$router.replace('/family')"/>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadNotices">
      <div v-for="notice in list" :key="notice.id" class="van-hairline--bottom">
        <portrait-desc :person="notice.announcer"/>
        <van-cell style="padding-top: 0">
          {{ notice.content }}
        </van-cell>
      </div>
    </van-list>
  </div>
</template>

<script>
import PortraitDesc from '@/components/basis/portrait-desc';
import {momentDate} from "@/utils/converter";

export default {
  name: "index",
  components: {PortraitDesc},
  data() {
    return {
      // 分页数据
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
    loadNotices() {
      this.$api.notice.noticePageData(this.formData).then(notices => {
        notices.forEach(item => {
          // 将发布时间追加到发布者对象中
          item.announcer.content = momentDate(item.created)
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1

        if (!notices || notices.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.finished = true
      })
    }
  }
}
</script>
