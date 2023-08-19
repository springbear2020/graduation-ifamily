<template>
  <div>
    <van-nav-bar title="家族公告" left-arrow @click-left="$router.replace('/family')"/>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadNotices">
      <div v-for="notice in list" :key="notice.id" class="top">
        <van-cell center class="avatar-cell" :border="false">
          <template #title>
            <van-image round width="52" height="52" :src="notice.announcer.portrait"/>
            <div class="avatar-wrapper">
              <p class="plain-border">{{ notice.announcer.name }}</p>
              <p class="plain-border avatar-wrapper_desc">{{ notice.announcer.content }}</p>
            </div>
          </template>

          <!-- TODO 家族公告已读人数 -->
        </van-cell>
        <van-cell style="padding-top: 0">
          <div class="line-wrap" v-html="notice.content"/>
        </van-cell>
      </div>
    </van-list>
  </div>
</template>

<script>
import {momentDate} from "@/utils/converter";

export default {
  name: "index",
  data() {
    return {
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
      this.$api.genealogy.noticePageData(this.formData).then(notices => {
        notices.forEach(item => {
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
