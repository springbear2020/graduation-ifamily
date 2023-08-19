<template>
  <div>
    <van-nav-bar title="个人信息" left-arrow @click-left="$router.push('/mine')"/>

    <van-cell title="头像" center class="user-avatar-cell">
      <template #default>
        <van-uploader max-count="1" :after-read="afterRead" v-model="fileList"
                      :max-size="5 * 1024 * 1024" :before-read="beforeRead"
                      @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
        />
      </template>
    </van-cell>
    <van-cell title="昵称" is-link to="/mine/info/form/0" center :value="user.nickname"/>
    <van-cell title="签名" is-link to="/mine/info/form/1" center :value="user.signature"/>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {imageUploader} from "@/mixin/image-uploader";

export default {
  name: "index",
  mixins: [imageUploader],
  mounted() {
    // 展示用户原有头像
    const avatar = this.user.avatar
    if (avatar) {
      this.fileList.push({url: avatar})
    }
    // 修改图片类型为用户头像 [1]用户头像 [2]家族封面
    this.imgType = '1'
  },
  data() {
    return {
      fileList: []
    }
  },
  computed: {
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    })
  },
  watch: {
    imgUrl() {
      // 图片上传成功，更新用户头像
      this.updateAvatar()
    }
  },
  methods: {
    updateAvatar() {
      // 更新用户头像地址 [1]用户昵称 [2]个性签名 [3]头像地址
      this.$api.user.updateUserProfile(this.imgUrl, 3).then(() => {
        // 移除用户信息，查询最新的用户信息
        this.$store.dispatch('user/getUser')
        this.$toast.success('更新成功')
      }).catch(() => {
        this.$toast({message: '更新失败', position: 'bottom'})
      });
    }
  }
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
