<template>
  <div>
    <!-- 密码登录页面右上角展示手机登录 -->
    <van-nav-bar v-if="loginType==='1'" title="密码登录" right-text="手机登录" @click-right="loginType='2'" left-arrow
                 @click-left="backUser"/>
    <!-- 手机登录页面右上角展示密码登录 -->
    <van-nav-bar v-else-if="loginType==='2'" title="手机登录" right-text="密码登录" @click-right="loginType='1'" left-arrow
                 @click-left="backUser"/>

    <!-- 绘制 LOGO 字体 -->
    <logo-character/>

    <van-form @submit="login">
      <van-field v-model="mobile" type="tel" name="mobile" label="手机号" placeholder="手机号" autofocus/>

      <!-- 密码登录页面展示密码登录输入框 -->
      <van-field v-if="loginType==='1'" v-model="password" type="password" name="password" label="密码" placeholder="密码"
                 right-icon="closed-eye"/>
      <!-- 手机登录页面展示验证码输入框 -->
      <div v-else-if="loginType==='2'">
        <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
          <template #button>
            <van-button size="small">发送验证码</van-button>
          </template>
        </van-field>
      </div>

      <van-field name="agree">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          <a href="#">《服务条款》</a>和<a href="#">《隐私协议》</a>
        </template>
      </van-field>

      <div class="login-btn">
        <van-button block type="info" native-type="submit">登录</van-button>
      </div>
    </van-form>

    <!-- 密码登录页面，展示 “用户注册” 和 “忘记密码” -->
    <van-nav-bar v-if="loginType==='1'" class="login-margin" left-text="忘记密码" @click-left="forgetPassword"
                 right-text="用户注册" @click-right="userRegister"/>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      mobile: '',
      code: '',
      password: '',
      agree: false,
      // [1]密码登录 [2]手机登录
      loginType: ''
    };
  },
  mounted() {
    this.loginType = this.$route.query.type ? this.$route.query.type : '1'
  },
  methods: {
    login(data) {
      console.log(data)
      this.$router.replace('/user')
    },
    userRegister() {
      this.$router.push('/user/password?type=1')
    },
    forgetPassword() {
      this.$router.push('/user/password?type=2')
    },
    backUser() {
      this.$router.replace('/user')
    }
  },
}
</script>

<style scoped>
.login-btn {
  margin: 16px;
}
</style>