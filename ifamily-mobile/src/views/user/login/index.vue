<template>
  <div>
    <van-nav-bar title="用户登录" right-text="新用户注册" @click-right="$router.push('/user/reset/0')"/>

    <logo-pattern/>

    <van-form @submit="handleLogin">
      <van-field size="large" type="text" label="账号" autofocus placeholder="UID / 手机 / 邮箱"
                 :rules="[{ required: true }]" v-model.trim="formData.account"
      />

      <van-field size="large" label="密码" placeholder="密码" autocomplete="on"
                 :type="passwordFieldType" :right-icon="rightIcon"
                 :rules="[{ required: true }]" v-model.trim="formData.password"
                 @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
      />

      <van-field :rules="[{ required: true }]" size="large">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          《服务条款》和《隐私协议》
        </template>
      </van-field>

      <div class="block-button-container">
        <van-button block type="info">登录</van-button>
      </div>
    </van-form>

    <van-nav-bar right-text="忘记密码？" class="grey-background"
                 :border="false" @click-right="$router.push('/user/reset/1')"
    />
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      formData: {
        account: undefined,
        password: undefined,
      },
      agree: false,
      passwordFieldType: 'password'
    }
  },
  computed: {
    rightIcon() {
      return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
    }
  },
  methods: {
    handleLogin() {
      this.$store.dispatch('user/login', this.formData).then(() => {
        const redirect = this.$route.query.redirect
        this.$router.replace(redirect ? redirect : '/')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      });
    }
  }
}
</script>
