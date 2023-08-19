<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="$router.replace(dstRoute)"/>

    <logo-pattern/>

    <van-form @submit="handleRegisterOrReset">
      <van-field size="large" type="text" label="账号" autofocus
                 :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱', trigger: 'onChange' }]"
                 placeholder="手机 / 邮箱" v-model.trim="formData.account"
      />

      <van-field size="large" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6"
                 :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]"
                 v-model.trim="formData.code"
      >
        <template #button>
          <van-button size="small" type="primary"
                      @click.prevent="handleSendCode" :disabled="countdown > 0 || !accountType"
          >
            {{ buttonText }}
          </van-button>
        </template>
      </van-field>

      <van-field size="large" :label="label" :placeholder="label" autocomplete="on"
                 :type="passwordFieldType" :right-icon="rightIcon"
                 :rules="[{ required: true }]" v-model.trim="formData.password"
                 @click-right-icon="passwordFieldType = passwordFieldType === 'password' ? 'text' : 'password'"
      />

      <van-field name="agree" :border="false" :rules="[{ required: true }]">
        <template #input>
          <van-checkbox v-model="agree">已阅读并同意</van-checkbox>
          《服务条款》和《隐私协议》
        </template>
      </van-field>

      <div class="block-button-container">
        <van-button block type="info" native-type="submit">确认</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {code} from '@/mixin/code'

export default {
  name: "index",
  mixins: [code],
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
    dstRoute() {
      return this.type === '2' ? '/mine/settings/security' : '/user/login'
    },
    label() {
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
      } else if (this.type === '1' || this.type === '2') {
        this.resetPassword();
      }
    },
    userRegister() {
      const {account, password, code} = this.formData
      this.$api.user.register({account, password, code}).then((res) => {
        // 注册成功，设置用户令牌信息
        this.$store.dispatch('user/signIn', res)
        this.$router.replace('/home')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    resetPassword() {
      const {account, password, code} = this.formData
      this.$api.user.reset({account, password, code}).then(() => {
        // 退出登录，清除仓库信息
        this.$store.dispatch('user/logout')
        this.$store.dispatch('genealogy/logout')
        this.$toast.success('重置成功\n正在前往登录');
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

<style scoped>
.block-button-container {
  margin: 8px 16px;
}
</style>
