<template>
  <div>
    <!-- 手机登录页面右上角展示密码登录，密码登录页面右上角展示手机登录 -->
    <van-nav-bar title="手机登录" right-text="密码登录"
                 @click-right="type='0'" v-if="type==='1'"
    />
    <van-nav-bar title="密码登录" right-text="手机登录"
                 @click-right="type='1'" v-else
    />

    <!-- echarts 绘制 LOGO 图标文字：百家谱 -->
    <logo-pattern/>

    <van-form @submit="handleUserLogin">
      <van-field v-model="mobile" type="tel" name="mobile" label="手机号" placeholder="手机号" autofocus/>

      <!-- 手机登录页面展示发送验证码按钮，密码登录页面展示密码输入框 -->
      <div v-if="type==='1'">
        <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
          <template #button>
            <van-button size="small" type="primary">发送验证码</van-button>
          </template>
        </van-field>
      </div>
      <van-field type="password" name="password" label="密码" placeholder="密码" right-icon="closed-eye"
                 v-model="password" v-else/>

      <!-- 服务条款、隐私协议 -->
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
    <van-nav-bar right-text="忘记密码" left-text="用户注册" :border="false" v-if="type==='0'"
                 @click-left="$router.push('/user/reset/0')" @click-right="$router.push('/user/reset/1')"
    />
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
      // [0]密码登录 [1]手机登录
      type: '0'
    };
  },
  methods: {
    handleUserLogin(data) {
      console.log(data)
      this.$router.replace('/mine')
    }
  }
}
</script>