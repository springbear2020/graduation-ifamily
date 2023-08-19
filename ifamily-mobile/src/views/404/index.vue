<template>
  <div>
    <van-nav-bar left-arrow title="页面不存在" @click-left="$router.replace('/')"/>

    <van-empty description="您访问的页面不存在" image="https://img01.yzcdn.cn/vant/custom-empty-image.png"/>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>