<template>
  <div>
    <van-nav-bar title="上传图片" left-arrow @click-left="$router.replace('/family/admin')" @click-right="uploadImages">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-uploader multiple v-model="fileList"
                  :after-read="afterRead" :max-size="5 * 1024 * 1024" :before-read="beforeRead"
                  @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
    />
  </div>
</template>

<script>
import {imageUploader} from "@/mixin/image-uploader"
import qs from "qs";

export default {
  name: "index",
  mixins: [imageUploader],
  data() {
    return {
      fileList: []
    }
  },
  mounted() {
    // [1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册
    this.imgType = '4'
  },
  watch: {
    imgUrl(url) {
      // 图片上传成功，将图片链接替换文件 File 对象
      this.fileList[this.fileList.length - 1] = {url}
    }
  },
  methods: {
    uploadImages() {
      let imgUrls = []
      this.fileList.forEach(item => {
        if (item.url) {
          imgUrls.push(item.url)
        }
      })

      if (imgUrls.length === 0) {
        this.$toast({message: '请先选择并上传图片', position: 'bottom'})
        return
      }

      // 将图片列表集合上传到服务器
      this.$api.album.uploadPhotos(qs.stringify({'imgUrls': imgUrls}, {arrayFormat: 'brackets'})).then(() => {
        this.$toast.success('上传成功');
        this.$router.replace('/family/album')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
/deep/ .van-uploader__preview {
  margin: 8px 0 8px 8px;
  background-color: #ffffff;
}

/deep/ .van-uploader__upload {
  margin: 8px 0 8px 8px;
  background-color: #ffffff;
}
</style>
