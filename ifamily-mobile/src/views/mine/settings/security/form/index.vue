<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace('/mine/settings/security')"/>

    <van-form @submit="handleUpdate">
      <van-field size="large" :label="title" :placeholder="title" v-model.trim="formData.account"
                 :rules="[{ required: true, validator: validatePhoneOrEmail, message: `请输入正确格式的${title}`, trigger: 'onChange' }]"/>

      <van-field size="large" label="验证码" placeholder="验证码" v-model.trim="formData.code" type="number" maxlength="6"
                 :rules="[{ required: true, pattern: /^\d{6}$/, message: '验证码为 6 位长度数字' }]">
        <template #button>
          <van-button size="small" type="primary" @click="handleSendCode" :disabled="countdown > 0 || !accountType">
            {{ buttonText }}
          </van-button>
        </template>
      </van-field>

      <div class="block-button-container">
        <van-button block type="info">保存</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {verifyCode} from "@/mixin/verify-code";

export default {
  name: "index",
  mixins: [verifyCode],
  data() {
    return {
      formData: {
        account: undefined,
        code: undefined
      },
      // [0]邮箱 [1]手机
      type: '0',
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0' || type === '1')) {
      type = '0'
    }
    this.type = type
  },
  computed: {
    user() {
      return this.$store.state.user.user || {}
    },
    title() {
      return this.type === '0' ? '新邮箱' : '新手机';
    },
  },
  methods: {
    handleUpdate() {
      // 校验信息内容是否发生更改
      if (this.type === '0') {
        if (this.formData.account === this.user.email) {
          this.$toast({message: '新旧邮箱一致，请重新输入', position: 'bottom'})
          return
        }
      } else {
        if (this.formData.account === this.user.phone) {
          this.$toast({message: '新旧手机一致，请重新输入', position: 'bottom'})
          return
        }
      }

      // [1]用户名 [2]邮箱 [3]手机
      const type = this.type === '0' ? 2 : 3
      this.$api.user.updateUserPrivacy(this.formData.account, this.formData.code, type).then(() => {
        // 更新成功，查询修改后最新的用户信息
        this.$store.dispatch('user/currentUser').then(() => {
          this.$router.replace('/mine/settings/security')
          this.$toast.success('更新成功')
        }).catch(msg => {
          this.$toast({message: msg, position: 'bottom'})
        })
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>
