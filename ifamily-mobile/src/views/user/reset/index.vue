<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="$router.replace(backRoute)"/>

    <logo-pattern/>

    <van-form @submit="handleRegisterOrReset">
      <van-field size="large" type="text" label="账号" autofocus placeholder="手机 / 邮箱" v-model.trim="formData.account"
                 :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱', trigger: 'onChange' }]"
      />

      <!-- 验证码输入框与获取按钮 -->
      <van-field size="large" type="number" label="验证码" placeholder="验证码" maxlength="6" v-model.trim="formData.code"
                 :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]">
        <template #button>
          <van-button size="small" type="primary"
                      @click.prevent="handleSendCode" :disabled="countdown > 0 || !accountType">
            {{ buttonText }}
          </van-button>
        </template>
      </van-field>

      <van-field size="large" autocomplete="on" :label="passwordLabel" :placeholder="passwordLabel"
                 :type="passwordFieldType" :right-icon="rightIcon" v-model.trim="formData.password"
                 :rules="[{ required: true, pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/, message: '密码限 6-16 个字符，至少一个字母和数字'}]"
                 @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
      />

      <van-field :rules="[{ required: true }]" size="large">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          《服务条款》和《隐私协议》
        </template>
      </van-field>

      <div class="block-button-container">
        <van-button block type="info">确认</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {verifyCode} from '@/mixin/verify-code'

export default {
  name: "index",
  mixins: [verifyCode],
  data() {
    return {
      // [0]用户注册 [1]忘记密码 [2]修改密码
      type: '0',
      formData: {
        account: undefined,
        password: undefined,
        code: undefined,
      },
      agree: false,
      passwordFieldType: 'password'
    };
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0' || type === '1' || type === '2')) {
      type = '0'
    }
    this.type = type
  },
  computed: {
    title() {
      return this.type === '0' ? '用户注册' : (this.type === '1' ? '忘记密码' : '修改密码');
    },
    backRoute() {
      return this.type === '2' ? '/mine/settings/security' : '/user/login'
    },
    passwordLabel() {
      return this.type === '0' ? '密码' : '新密码';
    },
    rightIcon() {
      return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
    }
  },
  methods: {
    handleRegisterOrReset() {
      if (this.type === '0') {
        this.userRegister();
      } else {
        this.resetPassword();
      }
    },
    userRegister() {
      this.$api.user.register(this.formData).then((res) => {
        // 注册成功，设置用户令牌信息，跳转到主页
        this.$store.dispatch('user/signIn', res)
        this.$router.replace('/')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    resetPassword() {
      this.$api.user.reset(this.formData).then(() => {
        // 重置成功，清除仓库信息，跳转到登录页
        this.$store.dispatch('user/logout')
        this.$store.commit('genealogy/CLEAR_STATE')
        this.$toast.success('重置成功\n正在前往登录')
        setTimeout(() => {
          this.$router.replace('/user/login')
        }, 2000)
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>
