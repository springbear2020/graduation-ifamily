<template>
  <div>
    <van-nav-bar title="设置" left-arrow @click-left="$router.replace('/mine')" @click-right="$toast('退出登录')">
      <template #right>
        <van-icon name="exit" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-cell title="账号安全" to="/mine/settings/security" is-link/>
    <van-cell title="联系我们" is-link/>
    <van-cell title="意见反馈" is-link/>
    <van-cell title="投诉举报" is-link/>
    <van-cell title="关于我们" is-link/>
    <van-cell title="服务协议" is-link/>
    <van-cell title="隐私政策" is-link/>
    <van-cell title="产品介绍" is-link/>
    <van-cell title="使用帮助" is-link/>
    <van-cell title="版本信息" is-link/>
    <van-cell title="权限管理" is-link/>
    <van-cell title="清空缓存" is-link/>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>