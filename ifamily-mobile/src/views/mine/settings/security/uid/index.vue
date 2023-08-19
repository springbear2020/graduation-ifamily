<template>
  <div>
    <van-nav-bar title="UID" left-arrow @click-left="$router.replace('/mine/settings/security')"/>

    <van-empty description="UID 是百家谱的唯一凭证，一年只能修改一次" :image="require('@/assets/img/uid.png')" class="empty">
      <van-form @submit="handleUpdate">
        <van-field size="large" type="text" label="UID" placeholder="UID" v-model.trim="username"
                   :rules="[{required: true, pattern: /^[a-zA-Z]([-_a-zA-Z0-9]{4,19})+$/,
                   message: 'UID 长度限 5-20 位且以字母开头，可包含数字、字母、下划线和连字符'}]"
        />
        <van-field size="large" label="密码" placeholder="密码" autocomplete="on"
                   v-model.trim="password" :rules="[{required: true, message: '请输入您的账号登录密码'}]"
                   :right-icon="rightIcon" :type="passwordFieldType"
                   @click-right-icon="passwordFieldType = (passwordFieldType === 'password' ? 'text' : 'password')"
        />

        <div style="margin: 8px 0">
          <van-button block type="info" :disabled="username === user.username || !username">
            立即修改
          </van-button>
        </div>
      </van-form>
    </van-empty>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      username: undefined,
      password: undefined,
      passwordFieldType: 'password'
    }
  },
  mounted() {
    this.username = this.user.username
  },
  computed: {
    user() {
      return this.$store.state.user.user || {}
    },
    rightIcon() {
      return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
    }
  },
  methods: {
    handleUpdate() {
      // type: [1]用户名 [2]邮箱 [3]手机
      this.$api.user.updateUserPrivacy(this.username, this.password, 1).then(() => {
        // 退出登录，移除仓库信息，前往登录页
        this.$store.dispatch('user/logout')
        this.$store.commit('genealogy/CLEAR_STATE')
        this.$router.replace('/user/login')
        this.$toast.success('UID 修改成功\n请重新登录')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>
