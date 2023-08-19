<template>
  <div class="img-wrap">
    <!-- 图片列表展示，支持图片点击预览 -->
    <van-image width="100" height="100"
               v-for="(img, index) in dataList" :key="img.id" :src="img.url" @click="previewImage(index)"
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
    previewImage(index) {
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
.van-image {
  margin-right: 1px;
  margin-bottom: 1px;
}

.img-wrap {
  font-size: 0;
  line-height: normal;
}
</style>