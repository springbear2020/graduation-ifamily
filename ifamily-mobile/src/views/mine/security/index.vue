<template>
  <div>
    <van-nav-bar title="账号安全" left-arrow @click-left="$router.replace('/mine')"/>

    <van-cell title="昵称" value="你不懂我&我不怪你" is-link to="/mine/security/form/0"/>
    <van-cell title="手机" value="13853239825" is-link to="/mine/security/form/1"/>
    <van-cell title="邮箱" value="springbear@163.com" is-link to="/mine/security/form/2"/>
    <van-cell title="登录密码" is-link to="/user/reset/2"/>
    <van-cell title="登录设备" is-link to="/mine/security/devices"/>
    <van-cell title="注销账号" is-link @click="$toast('注销成功')"/>

    <div class="block-btn-container">
      <van-button color="linear-gradient(to right, #ff6034, #ee0a24)" block @click="logout">退出登录</van-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    async logout() {
      // 移除 store 和 localStorage 中的 token 信息
      await this.$store.dispatch('user/logout')
      await this.$router.replace('/user/login')
    }
  }
}
</script>
