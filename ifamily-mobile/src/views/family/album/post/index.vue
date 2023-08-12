<template>
  <div>
    <van-nav-bar left-arrow title="家族动态" @click-left="backAlbum" @click-right="postAlbum">
      <template #right>
        <van-icon name="guide-o"/>
      </template>
    </van-nav-bar>

    <van-field placeholder="分享家族新鲜事..." type="textarea" maxlength="1000" rows="5" autofocus show-word-limit
               v-model="content"/>

    <van-uploader v-model="fileList" :multiple="true"/>

    <van-cell-group>
      <van-cell title="权限设置" icon="eye-o" is-link/>
      <van-cell title="定时发表" icon="clock-o" is-link/>
      <van-cell title="分享位置" icon="location-o" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      content: '',
      fileList: [],
    }
  },
  methods: {
    backAlbum() {
      this.$router.replace('/family/album')
    },
    postAlbum() {
      this.$toast.success('管理员审核')
    },
  }
}
</script>