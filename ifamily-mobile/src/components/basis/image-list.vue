<template>
  <van-cell :border="false">
    <van-grid square :column-num="columnNum" :gutter="2" :border="false">
      <van-grid-item v-for="(img, index) in dataList" :key="index">
        <van-image :src="img.url" @click="previewImage(index)" class="img"/>
      </van-grid-item>
    </van-grid>
  </van-cell>
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
  computed: {
    columnNum() {
      const length = this.dataList.length
      if (length === 1) {
        return 1
      } else if (length === 2 || length === 4) {
        return 2
      } else {
        return 3
      }
    }
  },
  methods: {
    previewImage(index) {
      let images = []
      this.dataList.forEach(item => {
        images.push(item.url)
      })
      ImagePreview({images: images, startPosition: index})
    }
  }
}
</script>

<style scoped>
/deep/ .van-grid-item__content {
  padding: 0;
}

.img {
  flex: 1;
  overflow: hidden;
  width: 100%;
}
</style>
