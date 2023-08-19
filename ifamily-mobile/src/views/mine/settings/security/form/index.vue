<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace('/mine/settings/security')">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-field v-model="content" type="text" :label="title" :placeholder="title" @change="changed = true"/>

    <van-field v-model="code" type="number" name="code" label="验证码" placeholder="验证码" maxlength="6">
      <template #button>
        <van-button size="small" type="primary" :disabled="!changed">发送验证码</van-button>
      </template>
    </van-field>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      content: '',
      code: '',
      // [0]邮箱 [1]手机
      type: '0',
      changed: false
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
    title() {
      return this.type === '0' ? '新邮箱' : '新手机';
    },
  }
}
</script>
