<template>
  <div>
    <!-- 手机登录页面右上角展示密码登录，密码登录页面右上角展示手机登录 -->
    <van-nav-bar title="手机登录" right-text="密码登录" left-arrow @click-right="type='1'" @click-left="backMine"
                 v-if="type==='2'"/>
    <van-nav-bar title="密码登录" right-text="手机登录" left-arrow @click-right="type='2'" @click-left="backMine"
                 v-else/>

    <!-- 绘制 LOGO 字体 -->
    <logo-pattern/>

    <van-form @submit="handleUserLogin">
      <van-field v-model="mobile" type="tel" name="mobile" label="手机号" placeholder="手机号" autofocus/>

      <!-- 手机登录页面展示发送验证码按钮，密码登录页面展示密码输入框 -->
      <div v-if="type==='2'">
        <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
          <template #button>
            <van-button size="small" type="primary">发送验证码</van-button>
          </template>
        </van-field>
      </div>
      <van-field v-model="password" type="password" name="password" label="密码" placeholder="密码"
                 right-icon="closed-eye" v-else/>

      <van-field name="agree">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          <a href="#">《服务条款》</a>和<a href="#">《隐私协议》</a>
        </template>
      </van-field>

      <div class="block-btn-container">
        <van-button block type="info" native-type="submit">登录</van-button>
      </div>
    </van-form>

    <!-- 密码登录页面展示 “用户注册” 和 “忘记密码” -->
    <van-nav-bar right-text="用户注册" left-text="忘记密码" :border="false"
                 @click-left="forgetPassword" @click-right="userRegister" v-if="type==='1'"/>
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
      type: '1'
    };
  },
  methods: {
    handleUserLogin(data) {
      console.log(data)
      this.$router.replace('/mine')
    },
    userRegister() {
      this.$router.push('/mine/password/1')
    },
    forgetPassword() {
      this.$router.push('/mine/password/2')
    },
    backMine() {
      this.$router.replace('/mine')
    }
  },
}
</script>