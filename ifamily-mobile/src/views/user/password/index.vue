<template>
  <div>
    <van-nav-bar
        left-arrow
        :title="title"
        @click-left="backPasswordLogin"
    />

    <logo-charts/>

    <van-form @submit="registerOrPassword">
      <van-field
          autofocus
          v-model="mobile"
          type="tel"
          name="mobile"
          label="手机号"
          placeholder="手机号"
      />

      <van-field
          v-model="code"
          type="number"
          name="code"
          label="验证码"
          placeholder="验证码"
          maxlength="6"
      >
        <template #button>
          <van-button size="small">发送验证码</van-button>
        </template>
      </van-field>

      <van-field
          v-model="password"
          type="password"
          name="password"
          label="密码"
          placeholder="密码"
          right-icon="closed-eye"
      />

      <van-field
          v-model="rePassword"
          type="password"
          name="password"
          label="确认密码"
          placeholder="确认密码"
          right-icon="closed-eye"
      />

      <van-field name="agree">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          <a href="#">《服务条款》</a>和<a href="#">《隐私协议》</a>
        </template>
      </van-field>

      <div style="margin: 16px;">
        <van-button block type="info" native-type="submit">确认</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import LogoCharts from "@/components/logo-charts";

export default {
  name: "index",
  components: {LogoCharts},
  data() {
    return {
      mobile: '',
      code: '',
      password: '',
      rePassword: '',
      agree: false,
      title: ''
    };
  },
  mounted() {
    // [1]用户注册 [2]忘记密码 [3]修改密码
    const type = this.$route.query.type
    this.title = type === '2' ? '忘记密码' : (type === '3' ? '修改密码' : '用户注册')
  },
  methods: {
    login(data) {
      console.log(data)
    },
    // 用户注册或找回密码
    registerOrPassword(data) {
      console.log(data)
      this.$router.replace('/user/login')
    },
    backPasswordLogin() {
      this.$router.replace('/user/login');
    }
  },
}
</script>