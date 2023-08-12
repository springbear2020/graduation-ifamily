<template>
  <div>
    <van-image width="100" height="100" @click="viewImage(dataList, index)"
               v-for="(img, index) in dataList" :src="img.url" :key="img.id"
    />
  </div>
</template>

<script>
import {ImagePreview} from "vant";

export default {
  name: "image-list",
  props: ['dataList'],
  methods: {
    viewImage(dataList, index) {
      let images = []
      dataList.forEach(item => {
        images.push(item.url)
      })
      ImagePreview({images: images, startPosition: index, closeable: true, showIndicators: true})
    },
  }
}
</script>

<style scoped>
/* TODO bottom margin or padding? */
.van-image {
  margin-right: 8px;
}
</style>