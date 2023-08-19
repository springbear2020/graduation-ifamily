<template>
  <div>
    <van-nav-bar left-arrow title="家族相册"
                 @click-left="$router.replace('/family')" @click-right="showUploadActionSheet = true">
      <template #right>
        <van-icon name="upgrade" size="20"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
      <van-cell-group v-for="i in 2">
        <!-- 头像、姓名、时间、更多 -->
        <portrait-desc :person="person" @click.native="$toast.success('查看人员信息')"/>
        <!-- 图片列表 -->
        <van-cell :border="false">
          <image-list :data-list="imgList"/>
        </van-cell>
      </van-cell-group>
    </van-pull-refresh>

    <!-- 家族图片上传动作面板 -->
    <van-action-sheet v-model="showUploadActionSheet" title="上传照片">
      <van-uploader multiple :after-read="afterRead" v-model="fileList"/>
      <van-button type="info" block @click="$toast.fail('管理员审核')">确定</van-button>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      isRefreshing: false,
      imgList: [
        {id: 1, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 2, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 3, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 4, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 5, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 6, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
        {id: 7, url: 'https://img01.yzcdn.cn/vant/sand.jpg'},
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
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
    afterRead(file) {
      console.log(file)
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
