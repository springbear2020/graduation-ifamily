<template>
  <div>
    <!-- 图片列表展示，支持图片点击预览 -->
    <van-image width="100" height="100" @click="viewImage(index)"
               v-for="(img, index) in dataList" :src="img.url" :key="img.id"
    />
  </div>
</template>

<script>
import {ImagePreview} from "vant";

export default {
  name: "image-list",
  props: {
    dataList: {
      type: Array,
      required: true
    }
  },
  methods: {
    viewImage(index) {
      let images = []
      this.dataList.forEach(item => {
        images.push(item.url)
      })
      ImagePreview({images: images, startPosition: index})
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