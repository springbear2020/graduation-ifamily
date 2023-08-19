<template>
  <div>
    <van-nav-bar title="UID" left-arrow @click-left="$router.replace('/mine/settings/security')"/>

    <van-empty description="UID 是百家谱的唯一凭证，一年只能修改一次" :image="require('@/assets/img/ifamily-160x160.png')">
      <van-form @submit="handleUpdate">
        <van-field type="text" label="UID" placeholder="UID" clearable
                   v-model.trim="username"
                   :rules="[{required: true, pattern: /^[a-zA-Z]([-_a-zA-Z0-9]{4,19})+$/, message: 'UID 以字母开头，可包含字母、数字、下划线和连字符，长度为 5-20 位'}]"
        />
        <van-field label="密码" placeholder="密码" autocomplete
                   v-model.trim="password" :rules="[{required: true, message: '请输入您的账号登录密码'}]"
                   :right-icon="rightIcon" :type="passwordFieldType"
                   @click-right-icon="passwordFieldType = (passwordFieldType === 'password' ? 'text' : 'password')"
        />
        <div class="block-button-container">
          <div class="van-field__error-message">{{ error }}</div>

          <van-button block type="info" :disabled="username === user.username || !username">
            立即修改
          </van-button>
        </div>
      </van-form>
    </van-empty>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "index",
  data() {
    return {
      username: '',
      password: '',
      error: '',
      passwordFieldType: 'password'
    }
  },
  mounted() {
    this.username = this.user.username
  },
  computed: {
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    }),
    rightIcon() {
      return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
    }
  },
  methods: {
    handleUpdate() {
      // [1]用户名 [2]邮箱 [3]手机
      this.$api.user.updateUserPrivacy(this.username, this.password, 1).then(() => {
        // 退出登录，移除仓库信息
        this.$store.dispatch('user/logout')
        this.$store.dispatch('genealogy/logout')
        this.$router.replace('/user/login')
        this.$toast('UID 修改成功，请重新登录')
      }).catch(err => {
        this.error = err.data || err.desc
      })
    }
  }
}
</script>

<style scoped>
.block-button-container {
  margin: 8px 16px;
}

.van-empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}

.van-field__error-message {
  margin-bottom: 8px;
}
</style>
