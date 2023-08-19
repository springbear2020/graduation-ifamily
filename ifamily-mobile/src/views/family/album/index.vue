<template>
  <div>
    <van-nav-bar left-arrow title="家族图片" @click-left="$router.replace('/family')"/>
    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadAlbums">
      <div v-for="(album, index) in list" :key="index" class="van-hairline--bottom">
        <portrait-desc :person="album.uploader"/>
        <image-list :data-list="album.imgUrls"/>
      </div>
    </van-list>
  </div>
</template>

<script>
import ImageList from '@/components/basis/image-list'
import PortraitDesc from '@/components/basis/portrait-desc'
import {momentDate} from "@/utils/converter";

export default {
  name: "index",
  components: {ImageList, PortraitDesc},
  data() {
    return {
      // 分页数据
      finished: false,
      loading: false,
      formData: {
        current: 1,
        size: 10,
      },
      list: [],
    }
  },
  methods: {
    loadAlbums() {
      this.$api.album.photoPageData(this.formData).then(album => {
        album.forEach(item => {
          item.uploader.content = momentDate(item.uploadTime)
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1

        if (!album || album.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.finished = true
      })
    }
  }
}
</script>
