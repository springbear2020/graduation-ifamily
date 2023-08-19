<template>
  <div>
    <van-nav-bar title="个人信息" left-arrow @click-left="$router.push('/mine')"/>

    <van-cell title="头像" center class="user-avatar-cell">
      <template #default>
        <van-uploader height="66" width="66" max-count="1" :after-read="afterRead" v-model="fileList"
                      :max-size="5 * 1024 * 1024" @oversize="onOversize" :before-read="beforeRead"/>
      </template>
    </van-cell>
    <van-cell title="昵称" is-link to="/mine/info/form/0" center :value="user.nickname"/>
    <van-cell title="签名" is-link to="/mine/info/form/1" center :value="user.signature"/>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {getAvatarToken} from "@/api/manager";
import {upload} from "@/utils/qiniu";

export default {
  name: "index",
  data() {
    return {
      fileList: [{url: ''}],
      fileType: ''
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
    afterRead(obj) {
      // 获取文件上传 token
      getAvatarToken({suffix: this.fileType}).then(map => {
        obj.status = 'uploading';
        obj.message = '上传中...';

        // 将文件上传到七牛云服务器
        upload(obj.file, map.key, map.token).then(() => {
          obj.status = 'done'

          // 更新用户头像地址 [1]用户昵称 [2]个性签名 [3]头像地址
          const imgUrl = map.cdn + map.key
          this.$api.user.updateUserProfile(imgUrl, 3).then(() => {
            // 移除用户信息，查询最新的用户信息
            this.$store.commit('user/REMOVE_USER')
          }).catch(() => {
            this.$toast.fail('更新头像失败')
          });

        })

      }).catch(() => {
        obj.status = 'failed';
        obj.message = '上传失败';
      })
    },
    onOversize() {
      this.$toast('文件大小不能超过 5MB');
    },
    beforeRead(file) {
      if (file.type.indexOf("image") === -1) {
        this.$toast('请上传正确格式的图片文件');
        return false;
      }
      // 获取图片文件格式后缀
      this.fileType = '.' + file.type.split('/')[1]
      return true;
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
