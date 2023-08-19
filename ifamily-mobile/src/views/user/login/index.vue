<template>
  <div>
    <van-nav-bar title="用户登录" :right-text="rightText"
                 @click-right="formData.loginType = formData.loginType === '0' ? '1' : '0'"/>

    <logo-pattern/>

    <van-form @submit="handleLogin">
      <div v-if="formData.loginType === '0'">
        <van-field size="large" type="text" label="账号"
                   :rules="[{ required: true }]"
                   :border="false" placeholder="用户名 / 手机 / 邮箱" v-model.trim="formData.account"
        />
      </div>
      <div v-else-if="formData.loginType === '1'">
        <van-field size="large" type="text" label="账号"
                   :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱' }]"
                   :border="false" placeholder="手机 / 邮箱" v-model.trim="formData.account"
        />
      </div>

      <!-- 验证码框 -->
      <div v-if="formData.loginType === '1'">
        <van-field size="large" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6"
                   :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]" :border="false"
                   v-model.trim="formData.code"
        >
          <template #button>
            <van-button size="small" type="primary" @click.prevent="handleSendCode" :disabled="countdown > 0">
              {{ buttonText }}
            </van-button>
          </template>
        </van-field>
      </div>
      <!-- 密码框 -->
      <div v-else-if="formData.loginType === '0'">
        <van-field size="large" label="密码" placeholder="密码" autocomplete="on"
                   :type="passwordFieldType" :right-icon="rightIcon" :border="false"
                   :rules="[{ required: true }]" v-model.trim="formData.password"
                   @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
        />
      </div>

      <van-field name="agree" :border="false" :rules="[{ required: true }]">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          《服务条款》和《隐私协议》
        </template>
      </van-field>

      <div class="login-btn-container">
        <van-button block type="info">登录</van-button>
      </div>
    </van-form>

    <van-nav-bar left-text="忘记密码" right-text="用户注册"
                 :border="false"
                 @click-left="$router.push('/user/reset/1')"
                 @click-right="$router.push('/user/reset/0')"
    />
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      formData: {
        account: '',
        password: '',
        code: '',
        // [0]密码登录 [1]验证码登录
        loginType: '0',
      },
      agree: false,
      passwordFieldType: 'password',
      buttonText: '获取验证码',
      countdown: 0,
      // [0]邮箱账号 [1]手机账号
      accountType: undefined
    };
  },
  computed: {
    rightText() {
      return this.formData.loginType === '0' ? '验证码登录' : (this.formData.loginType === '1' ? '密码登录' : '');
    },
    rightIcon() {
      return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
    }
  },
  methods: {
    /*
     * 用户登录请求
     */
    handleLogin() {
      if (this.accountType === '1') {
        this.$toast.fail('手机验证码服务暂不可用')
        return;
      }

      this.$store.dispatch('user/login', this.formData).then(() => {
        const dstRoute = this.$route.query.redirect ? this.$route.query.redirect : '/home'
        this.$router.replace(dstRoute)
      }).catch(error => {
        this.$notify({
          type: 'warning',
          message: error.data || error.desc
        });
      });
    },
    /*
     * 发送验证码请求
     */
    handleSendCode() {
      if (this.countdown > 0) {
        return;
      }

      // 请求服务器发送验证码 accountType: [0]发送邮箱验证码 [1]发送手机验证码
      if (this.accountType === '0') {
        this.sendEmailVerifyCode();
      } else if (this.accountType === '1') {
        this.sendPhoneVerifyCode();
      }
    },
    // 验证手机号和邮箱格式
    validatePhoneOrEmail() {
      const account = this.formData.account
      const phoneRegExp = /^1[3456789]\d{9}$/
      const emailRegExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (emailRegExp.test(account)) {
        this.accountType = '0'
      } else if (phoneRegExp.test(account)) {
        this.accountType = '1'
      } else {
        this.accountType = undefined
      }
      return phoneRegExp.test(account) || emailRegExp.test(account);
    },
    // 发送邮箱验证码
    sendEmailVerifyCode() {
      this.$api.manager.sendEmailCode({email: this.formData.account}).then(res => {
        this.$toast.success('发送成功');
        this.lockButton();
      }).catch(err => {
        this.$notify(err.data || err.desc)
      })
    },
    // 发送手机验证码
    sendPhoneVerifyCode() {
      this.$toast.fail('手机验证码服务暂不可用')
    },
    // 锁定获取验证码按钮
    lockButton() {
      this.countdown = 60;
      const timer = setInterval(() => {
        if (this.countdown <= 1) {
          clearInterval(timer);
          this.countdown = 0;
          this.buttonText = "获取验证码";
        } else {
          this.countdown--;
          this.buttonText = `${this.countdown}s 后重试`;
        }
      }, 1000);
    }
  }
}
</script>

<style scoped>
.login-btn-container {
  margin: 16px;
}
</style>
