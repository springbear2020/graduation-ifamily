<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace('/mine/security')"
                 @click-right="$toast('更改成功')">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 修改昵称和签名 -->
    <div v-if="type === '0'" class="van-hairline--bottom">
      <van-field v-model="nickname" type="text" name="nickname" label="昵称" placeholder="昵称" autofocus/>
      <van-field rows="3" autosize type="textarea" maxlength="100" label="签名" placeholder="签名" show-word-limit
                 v-model="signature"/>
    </div>

    <!-- 修改手机、修改邮箱 -->
    <van-form v-else>
      <van-field v-model="mobile" type="tel" name="mobile" label="手机号" placeholder="手机号" autofocus v-if="type === '1'"/>
      <van-field v-model="email" type="text" name="email" label="邮箱" placeholder="邮箱" autofocus
                 v-else-if="type === '2'"/>

      <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
        <template #button>
          <van-button size="small" type="primary">发送验证码</van-button>
        </template>
      </van-field>
    </van-form>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      // [0]昵称 [1]手机 [2]邮箱
      type: '0',
      mobile: '',
      email: '',
      code: '',
      nickname: '',
      signature: ''
    }
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    title() {
      let title = '家族'
      if (this.type === '0') {
        title = '修改信息'
      } else if (this.type === '1') {
        title = '修改手机'
      } else if (this.type === '2') {
        title = '修改邮箱'
      }
      return title
    }
  }
}
</script>