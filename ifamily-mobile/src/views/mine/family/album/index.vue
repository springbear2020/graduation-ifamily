<template>
  <div>
    <van-nav-bar title="家庭相册" left-arrow
                 @click-left="$router.replace('/mine')"
                 @click-right="showUploadActionSheet = true"
    >
      <template #right>
        <van-icon name="upgrade" size="20"/>
      </template>
    </van-nav-bar>

    <div v-for="i in 5" :key="i">
      <van-cell-group>
        <!-- 头像、姓名、时间 -->
        <portrait-desc :person="person" @click-image="$toast('查看人员')"/>
        <!-- 图片列表 -->
        <van-cell :border="false">
          <image-list :data-list="imgList"/>
        </van-cell>
      </van-cell-group>
    </div>

    <!-- 家庭图片上传动作面板 -->
    <van-action-sheet v-model="showUploadActionSheet" title="上传照片">
      <van-uploader multiple :after-read="afterRead" v-model="fileList"/>
      <van-button type="info" block @click="$toast('上传成功'); showUploadActionSheet = false">确定</van-button>
    </van-action-sheet>
  </div>
</template>

<script>
import PortraitDesc from '@/components/basis/portrait-desc'
import ImageList from '@/components/basis/image-list'

export default {
  name: "index",
  components: {PortraitDesc, ImageList},
  data() {
    return {
      imgList: [
        {id: 1, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 2, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 3, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 4, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 5, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 6, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
      ],
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '2023-02-25 12:05'
      },
      showUploadActionSheet: false,
      fileList: [],
    }
  },
  methods: {
    afterRead(file) {
    }
  }
}
</script>

<style scoped>
/deep/ .van-uploader__preview {
  margin: 0 0 8px 8px;
}

/deep/ .van-uploader__upload {
  margin: 0 0 8px 8px;
}
</style>
