<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="$router.replace(dstRoute)"/>

    <!-- echarts 绘制 LOGO 图标文字：百家谱 -->
    <logo-pattern/>

    <!-- 密码表单 -->
    <van-form @submit="handleRegisterOrReset">
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

      <div class="block-btn-container">
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
      // [0]用户注册 [1]忘记密码 [2]修改密码
      type: '0',
    };
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    title() {
      return this.type === '0' ? '用户注册' : (this.type === '1' ? '忘记密码' : '修改密码')
    },
    dstRoute() {
      return this.type === '2' ? '/mine/security' : '/user/login'
    }
  },
  methods: {
    // 用户注册或重置密码
    handleRegisterOrReset(data) {
      this.$toast('注册或重置密码')
      this.$router.replace('/user/login')
    }
  }
}
</script>