<template>
  <div>
    <van-nav-bar title="用户登录" right-text="新用户注册" @click-right="$router.push('/user/reset/0')"/>

    <van-empty :image="require('@/assets/img/ifamily.png')"/>

    <van-form @submit="handleLogin">
      <van-field size="large" label="账号" placeholder="UID / 手机 / 邮箱" autofocus
                 :rules="[{ required: true }]" v-model.trim="formData.account"
      />

      <van-field size="large" label="密码" placeholder="密码" autocomplete="on"
                 :rules="[{ required: true }]" v-model.trim="formData.password"
                 :type="passwordFieldType" :right-icon="passwordFieldType === 'password' ? 'closed-eye' : 'eye-o'"
                 @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
      />

      <van-field size="large" :rules="[{ required: true, message: '请阅读并勾选同意《服务条款》和《隐私协议》'}]">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          <span class="van-nav-bar__text" @click="$toast({message: '服务条款', position: 'bottom'})">《服务条款》</span>
          <span class="van-nav-bar__text" @click="$toast({message: '隐私协议', position: 'bottom'})">《隐私协议》</span>
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
        account: "jiabaoyu",
        password: "jiabaoyu",
      },
      agree: false,
      passwordFieldType: 'password'
    }
  },
  methods: {
    handleLogin() {
      this.$store.dispatch('user/login', this.formData).then(() => {
        const redirect = this.$route.query.redirect
        this.$router.replace(redirect ? redirect : '/')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>
