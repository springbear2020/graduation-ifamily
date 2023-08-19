<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="$router.replace(dstRoute)"/>

    <logo-pattern/>

    <van-form @submit="handleRegisterOrReset">
      <van-field size="large" type="text" label="账号" autofocus
                 :rules="[{ required: true, validator: validatePhoneOrEmail, message: '请输入正确的手机或邮箱', trigger: 'onChange' }]"
                 :border="false" placeholder="手机 / 邮箱" v-model.trim="formData.account"
      />

      <van-field size="large" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6"
                 :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]" :border="false"
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
                 :type="passwordFieldType" :right-icon="rightIcon" :border="false"
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
        <div class="van-field__error-message">{{ error }}</div>

        <van-button block type="info" native-type="submit">确认</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {user} from '@/mixin/user'
import {setToken} from "@/utils/auth";

export default {
  name: "index",
  mixins: [user],
  data() {
    return {
      // [0]用户注册 [1]忘记密码 [2]修改密码
      type: '0',
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
    }
  },
  methods: {
    handleRegisterOrReset() {
      if (this.accountType === '1') {
        this.$toast.fail('手机验证码服务暂不可用')
        return;
      }

      if (this.type === '0') {
        this.userRegister();
      } else if (this.type === '1' || this.type === '2') {
        this.resetPassword();
      }
    },
    userRegister() {
      const {account, password, code} = this.formData
      this.$api.user.register({account, password, code}).then((token) => {
        // 派发 mutation 设置用户登录信息，并将 token 信息存储到 localStorage 中
        setToken(token);
        this.$store.commit('user/SET_TOKEN')
        this.$router.replace('/home')
      }).catch(err => {
        this.error = err.data || err.desc
      })
    },
    resetPassword() {
      const {account, password, code} = this.formData
      this.$api.user.reset({account, password, code}).then(() => {
        // 派发 action 清除登录用户信息
        this.$store.dispatch('user/logout')
        this.$toast.success('重置成功');
        this.$notify({
          type: 'success',
          message: '密码重置成功，即将前往登录页',
          duration: 3000,
          onClose: () => this.$router.replace('/user/login')
        })
      }).catch(err => {
        this.error = err.data || err.desc
      })
    }
  }
}
</script>

<style scoped>
.block-button-container {
  margin: 8px 16px;
}

.van-field__error-message {
  margin-bottom: 8px;
}
</style>
