<template>
  <div>
    <van-nav-bar title="用户资料" left-arrow @click-left="$router.replace('/mine')"/>

    <van-cell title="头像" center style="padding: 0 16px">
      <template #default>
        <van-uploader max-count="1" v-model="fileList"
                      :before-read="beforeRead" :after-read="afterRead" :max-size="5 * 1024 * 1024"
                      @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
        />
      </template>
    </van-cell>
    <van-cell title="昵称" is-link center :value="userInfo.nickname" @click="openPopup(1)"/>
    <van-cell title="签名" is-link center :value="userInfo.signature" @click="openPopup(2)"/>

    <!-- 昵称、签名修改弹出层 -->
    <van-popup v-model="showPopup" position="right" class="full-popup">
      <van-nav-bar left-arrow :title="popupTitle" @click-left="showPopup = false"/>
      <van-form @submit="handleUpdate">
        <van-field rows="1" type="textarea" maxlength="30" show-word-limit
                   :autosize="true" v-model="content" :label="inputLabel" :placeholder="inputLabel"
                   :rules="[{ required: true, message: `${inputLabel}不能为空，请重新输入`}]"
        />
        <div class="block-button-container">
          <van-button block type="info">保存</van-button>
        </div>
      </van-form>
    </van-popup>
  </div>
</template>

<script>
import {imageUploader} from "@/mixin/image-uploader";

export default {
  name: "index",
  mixins: [imageUploader],
  mounted() {
    // 修改图片类型为用户头像 [1]用户头像 [2]家族封面
    this.imgType = '1'
    // 初始化用户原始头像
    this.initAvatar()
  },
  data() {
    return {
      fileList: [],
      showPopup: false,
      content: undefined,
      popupTitle: '',
      inputLabel: '',
      operationType: undefined,
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.user.user || {}
    }
  },
  watch: {
    imgUrl(imgUrl) {
      // 图片上传成功，更新用户头像
      this.updateAvatar(imgUrl)
    }
  },
  methods: {
    initAvatar() {
      const avatar = this.userInfo.avatar
      if (avatar) {
        this.fileList.push({url: avatar})
      }
    },
    updateAvatar(imgUrl) {
      // 更新用户头像地址 [1]用户昵称 [2]个性签名 [3]头像地址
      this.$api.user.updateUserProfile(imgUrl, 3).then(() => {
        this.$store.dispatch('user/getUser')
        this.$toast.success('更新成功')
      }).catch((err) => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    openPopup(type) {
      this.popupTitle = type === 1 ? '修改昵称' : '修改签名';
      this.inputLabel = type === 1 ? '新昵称' : '新签名'
      this.content = type === 1 ? this.userInfo.nickname : this.userInfo.signature
      this.operationType = type
      this.showPopup = true
    },
    handleUpdate() {
      if (this.operationType === 1) {
        // 校验新旧昵称是否发生更改
        if (this.content === this.userInfo.nickname) {
          this.$toast({message: '新旧昵称一致，请重新输入', position: 'bottom'})
          return
        }
      } else {
        // 校验新旧签名是否发生更改
        if (this.content === this.userInfo.signature) {
          this.$toast({message: '新旧签名一致，请重新输入', position: 'bottom'})
          return
        }
      }

      // 请求服务器更新用户昵称或签名：[1]用户昵称 [2]个性签名 [3]头像地址
      this.$api.user.updateUserProfile(this.content, this.operationType).then(() => {
        // 查询修改后最新的用户信息
        this.$store.dispatch('user/getUser').then(() => {
          this.showPopup = false
          this.$toast.success('更新成功')
        }).catch((err) => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      }).catch((err) => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}

/deep/ .van-uploader__preview {
  margin: 8px 0;
}

/deep/ .van-uploader__upload {
  margin: 8px 0;
}
</style>
