<template>
  <div>
    <van-nav-bar title="账号安全" left-arrow @click-left="$router.replace('/mine/settings')"/>

    <van-cell title="UID" :value="user.username" is-link to="/mine/settings/security/uid"/>
    <van-cell title="手机" :value="user.phone" is-link to="/mine/settings/security/form/1" class="top"/>
    <van-cell title="邮箱" :value="user.email" is-link to="/mine/settings/security/form/0"/>
    <van-cell title="登录设备" is-link to="/mine/settings/security/devices" class="top"/>
    <van-cell title="修改密码" is-link to="/user/reset/2"/>
    <van-cell title="注销账号" is-link @click="logoutDialogShow = true" class="top"/>

    <!-- 账户注销弹出确认框 -->
    <van-dialog v-model="logoutDialogShow" title="注销账号" show-cancel-button
                :before-close="handleLogout" @closed="password = '';">
      <div class="van-dialog__message van-dialog__message--has-title">
        <p>尊敬的用户，您正在进行账号注销操作，在您决定注销账号之前，请注意以下提示：</p>
        <p>1. 如果您确定要注销账户，请注意这将删除您的所有数据和信息，包括您的个人和家族信息。</p>
        <p>2. 在注销前，请确认您已经备份或保存了您需要保留的数据和信息，因为注销后将无法恢复。</p>
        <p>3. 如果您有任何建议或反馈，请告诉我们，我们会尽力改进和优化我们的服务。</p>
        <p>4. 如果您遇到任何问题，欢迎联系我们的客户服务团队，他们将竭诚为您服务。</p>
        <p>5. 我们非常感谢您对百家谱的贡献和支持，很遗憾您要离开，但我们尊重您的决定。</p>
        <p>最后，请确认您已经理解以上提示并输入您的密码进行账号注销。</p>
        <van-form>
          <van-field placeholder="密码" autocomplete="on" :type="passwordFieldType" v-model.trim="password"
                     :right-icon="passwordFieldType === 'password' ? 'closed-eye' : 'eye-o'"
                     @click-right-icon="passwordFieldType = (passwordFieldType === 'password' ? 'text' : 'password')"
          />
        </van-form>
      </div>
    </van-dialog>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      logoutDialogShow: false,
      password: '',
      passwordFieldType: 'password'
    }
  },
  computed: {
    user() {
      return this.$store.state.user.user || {}
    }
  },
  methods: {
    handleLogout(action, done) {
      // 点击确认按钮，执行注销账号业务逻辑
      if ('confirm' === action) {
        if (this.password.length <= 0) {
          this.$toast({message: '请输入您的账号登录密码', position: 'bottom'})
          return done(false)
        }

        this.$api.user.logout({password: this.password}).then(() => {
          // 注销成功，退出系统，清除仓库信息，前往登录页
          this.$store.dispatch('user/logout')
          this.$store.commit('genealogy/CLEAR_STATE')
          this.$router.replace('/user/login')
          this.$toast.success('注销成功')
          return done(true)
        }).catch(msg => {
          this.$toast({message: msg, position: 'bottom'})
        })

        return done(false)
      }

      return done(true)
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}

/deep/ .van-dialog__message {
  text-align: left;
}

.van-dialog__message p {
  margin: 8px 0;
}

/deep/ .van-field {
  background-color: #f7f8fa;
}
</style>
