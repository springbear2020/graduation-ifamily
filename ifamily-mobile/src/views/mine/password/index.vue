<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="backPasswordLogin"/>

    <!-- 绘制 LOGO 文字 -->
    <logo-pattern/>

    <van-form @submit="registerOrResetPassword">
      <van-field autofocus v-model="mobile" type="tel" name="mobile" label="手机号" placeholder="手机号"/>

      <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
        <template #button>
          <van-button size="small" type="primary">发送验证码</van-button>
        </template>
      </van-field>

      <van-field v-model="password" type="password" name="password" label="密码" placeholder="密码"
                 right-icon="closed-eye"/>

      <van-field v-model="rePassword" type="password" name="password" label="确认密码" placeholder="确认密码"
                 right-icon="closed-eye"/>

      <van-field name="agree">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          <a href="#">《服务条款》</a>和<a href="#">《隐私协议》</a>
        </template>
      </van-field>

      <div class="login-btn-container">
        <van-button block type="info" native-type="submit">确认</van-button>
      </div>
    </van-form>
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
      rePassword: '',
      agree: false,
      type: '1',
      title: ''
    };
  },
  mounted() {
    // [1]用户注册 [2]忘记密码 [3]修改密码
    this.type = this.$route.params.type
    this.title = this.type === '2' ? '忘记密码' : (this.type === '3' ? '修改密码' : '用户注册')
  },
  methods: {
    // 用户注册或重置密码
    registerOrResetPassword(data) {
      console.log(data)
      this.$router.replace('/mine/login')
    },
    backPasswordLogin() {
      this.$router.replace('/mine/login');
    }
  },
}
</script>