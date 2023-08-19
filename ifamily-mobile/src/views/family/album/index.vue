<template>
  <div>
    <van-nav-bar left-arrow title="家族图片" @click-left="$router.replace('/family')"/>
    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadAlbums">
      <div v-for="album in list" :key="album.id" class="top">
        <van-cell center class="avatar-cell" :border="false">
          <template #title>
            <van-image round width="52" height="52" :src="album.uploader.portrait"/>
            <div class="avatar-wrapper">
              <p class="plain-border">{{ album.uploader.name }}</p>
              <p class="plain-border avatar-wrapper_desc">{{ album.uploader.content }}</p>
            </div>
          </template>
        </van-cell>
        <image-list :data-list="album.imgUrls"/>
      </div>
    </van-list>
  </div>
</template>

<script>
import ImageList from '@/components/basis/image-list'
import {momentDate} from "@/utils/converter"

export default {
  name: "index",
  components: {ImageList},
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
    loadAlbums() {
      this.$api.genealogy.photoPageData(this.formData).then(album => {
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
