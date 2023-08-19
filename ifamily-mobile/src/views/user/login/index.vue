<template>
  <div>
    <van-nav-bar title="用户登录" :right-text="rightText"
                 @click-right="formData.loginType = formData.loginType === '0' ? '1' : '0'"
    />

    <logo-pattern/>

    <van-form @submit="handleLogin">
      <van-field size="large" type="text" label="账号" autofocus
                 :rules="[{ required: true }]" v-if="formData.loginType === '0'"
                 placeholder="UID / 手机 / 邮箱" v-model.trim="formData.account"
      />

      <van-field size="large" type="text" label="账号" autofocus v-else
                 :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱', trigger: 'onChange' }]"
                 placeholder="手机 / 邮箱" v-model.trim="formData.account"
      />

      <!-- 验证码框 -->
      <van-field size="large" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6"
                 :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]"
                 v-model.trim="formData.code" v-if="formData.loginType === '1'"
      >
        <template #button>
          <van-button size="small" type="primary" @click.prevent="handleSendCode"
                      :disabled="countdown > 0 || !accountType">
            {{ buttonText }}
          </van-button>
        </template>
      </van-field>

      <!-- 密码框 -->
      <van-field size="large" label="密码" placeholder="密码" autocomplete="on"
                 :type="passwordFieldType" :right-icon="rightIcon" v-else
                 :rules="[{ required: true }]" v-model.trim="formData.password"
                 @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
      />

      <van-field name="agree" :border="false" :rules="[{ required: true }]">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          《服务条款》和《隐私协议》
        </template>
      </van-field>

      <div class="block-button-container">
        <van-button block type="info">登录</van-button>
      </div>
    </van-form>

    <van-nav-bar left-text="忘记密码" right-text="用户注册"
                 :border="false" class="bottom-nav"
                 @click-left="$router.push('/user/reset/1')"
                 @click-right="$router.push('/user/reset/0')"
    />
  </div>
</template>

<script>
import {code} from '@/mixin/code'

export default {
  name: "index",
  mixins: [code],
  data() {
    return {
      formData: {
        account: undefined,
        password: undefined,
        code: undefined,
        // [0]密码登录 [1]验证码登录
        loginType: '0',
      },
      agree: false,
      passwordFieldType: 'password'
    }
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
    // 用户登录请求
    handleLogin() {
      this.$store.dispatch('user/login', this.formData).then(() => {
        const dstRoute = this.$route.query.redirect ? this.$route.query.redirect : '/home'
        this.$router.replace(dstRoute)
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      });
    },
  }
}
</script>

<style scoped>
.block-button-container {
  margin: 8px 16px;
}

.bottom-nav {
  background-color: #f7f8fa;
}
</style>
