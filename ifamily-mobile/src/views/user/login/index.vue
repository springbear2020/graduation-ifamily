<template>
  <div>
    <van-nav-bar title="用户登录" :right-text="rightText"
                 @click-right="formData.loginType = formData.loginType === '0' ? '1' : '0'"
    />

    <logo-pattern/>

    <van-form @submit="handleLogin">
      <div v-if="formData.loginType === '0'">
        <van-field size="large" type="text" label="账号" autofocus
                   :rules="[{ required: true }]"
                   :border="false" placeholder="UID / 手机 / 邮箱" v-model.trim="formData.account"
        />
      </div>
      <div v-else-if="formData.loginType === '1'">
        <van-field size="large" type="text" label="账号" autofocus
                   :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱', trigger: 'onChange' }]"
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
            <van-button size="small" type="primary" @click.prevent="handleSendCode"
                        :disabled="countdown > 0 || !accountType">
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

      <div class="block-button-container">
        <div class="van-field__error-message">{{ error }}</div>

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
import {user} from '@/mixin/user'
import {code} from '@/mixin/code'

export default {
  name: "index",
  mixins: [user, code],
  computed: {
    rightText() {
      return this.formData.loginType === '0' ? '验证码登录' : (this.formData.loginType === '1' ? '密码登录' : '');
    }
  },
  methods: {
    // 用户登录请求
    handleLogin() {
      this.$store.dispatch('user/login', this.formData).then(() => {
        const dstRoute = this.$route.query.redirect ? this.$route.query.redirect : '/home'
        this.$router.replace(dstRoute)
      }).catch(err => {
        this.error = err.data || err.desc
      });
    },
  }
}
</script>

<style scoped>
.block-button-container {
  margin: 8px 16px;
}

.van-field__error-message {
  margin-bottom: 8px;
}
</style>
