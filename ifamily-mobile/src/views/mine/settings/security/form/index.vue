<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace('/mine/settings/security')"
                 @click-right="handleUpdate">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-field v-model.trim="formData.account" type="text" :label="title" :placeholder="title"/>

    <van-field v-model.trim="formData.code" type="number" label="验证码" placeholder="验证码" maxlength="6">
      <template #button>
        <van-button size="small" type="primary" @click="handleSendCode" :disabled="countdown > 0">
          {{ buttonText }}
        </van-button>
      </template>
    </van-field>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {code} from '@/mixin/code'

export default {
  name: "index",
  mixins: [code],
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
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    }),
    title() {
      return this.type === '0' ? '新邮箱' : '新手机';
    },
  },
  methods: {
    handleUpdate() {
      // 验证手机号或邮箱格式
      if (!this.validatePhoneOrEmail()) {
        const msg = this.type === '0' ? '邮箱地址格式不正确，请重新输入' : '手机号格式不正确，请重新输入'
        this.$toast({message: msg, position: 'bottom'})
        return
      }

      // 信息内容是否未更改
      if (this.type === '0') {
        if (this.formData.account === this.user.email) {
          this.$toast({message: '新旧邮箱一致，请重新输入', position: 'bottom'})
          return
        }
      } else {
        if (this.formData.account === this.user.phone) {
          this.$toast({message: '新旧手机号一致，请重新输入', position: 'bottom'})
          return
        }
      }

      // 验证验证码格式
      if (!/^\d{6}$/.test(this.formData.code)) {
        this.$toast({message: '验证码为 6 位长度数字', position: 'bottom'})
        return
      }

      // 处理更新用户邮箱或手机号请求：[1]用户名 [2]邮箱 [3]手机
      const type = this.type === '0' ? 2 : 3
      this.$api.user.updateUserPrivacy(this.formData.account, this.formData.code, type).then(() => {
        this.$toast.success('更新成功')
        this.formData.code = ''
        // 重新查询最新信息
        this.$store.dispatch('user/getUser')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>
