<template>
  <div>
    <van-nav-bar title="个人信息" left-arrow @click-left="$router.push('/mine')"/>

    <van-cell title="头像" center class="user-avatar-cell">
      <template #default>
        <van-uploader :after-read="afterRead" v-model="fileList" height="66" width="66" max-count="1"/>
      </template>
    </van-cell>
    <van-cell title="昵称" is-link to="/mine/info/form/0" center :value="user.nickname"/>
    <van-cell title="签名" is-link to="/mine/info/form/1" center :value="user.signature"/>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "index",
  data() {
    return {
      fileList: [{url: ''}],
    }
  },
  mounted() {
    this.fileList[0].url = this.user.avatar ? this.user.avatar : 'avatar.jpg'
  },
  computed: {
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    })
  },
  methods: {
    afterRead(file) {
      file.status = 'uploading';
      file.message = '上传中...';
      // TODO 用户头像上传
      setTimeout(() => {
        file.status = 'failed';
        file.message = '上传失败';
      }, 3000);
    },
  },
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}

.user-avatar-cell {
  padding: 0 16px;
  line-height: 0;
}

/deep/ .van-uploader__preview {
  margin: 8px 0;
}

/deep/ .van-uploader__upload {
  margin: 8px 0;
}
</style>
