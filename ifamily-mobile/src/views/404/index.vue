<template>
  <div>
    <van-nav-bar left-arrow title="404" @click-left="$router.replace('/')"/>

    <van-empty description="很抱歉，您请求的页面不存在" :image="require('@/assets/img/404.jpg')" class="empty">
      <van-button round type="info" replace to="/" icon="arrow-left">返回首页</van-button>
    </van-empty>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>
