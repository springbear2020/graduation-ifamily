<template>
  <div>
    <van-nav-bar title="账号安全" left-arrow @click-left="$router.replace('/mine/settings')"/>

    <van-cell title="UID" value="0124212442"/>
    <van-cell title="手机" value="13853239825" is-link/>
    <van-cell title="邮箱" value="springbear@163.com" is-link/>
    <van-cell title="登录密码" to="/user/reset/2" is-link/>
    <van-cell title="登录设备" is-link/>
    <van-cell title="注销账号" is-link/>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>