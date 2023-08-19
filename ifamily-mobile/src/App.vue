<template>
  <div id="app">
    <router-view/>
    <footerTab v-show="$route.meta.footerShow"/>
  </div>
</template>

<script>
import footerTab from "@/components/footer-tab";

export default {
  name: 'App',
  components: {footerTab},
}
</script>

<style>
.flex-container {
  display: flex;
}

.flex-container .cover {
  margin: auto auto;
  padding: 30px
}

.block-btn-container {
  margin: 16px;
}

.full-address-container .van-field {
  background-color: #f7f8fa;
}

.title-container {
  margin-left: 10px;
}

.title-container p {
  margin: 0 0;
}

.title-container .title {
  font-weight: bold;
}

.van-step__title p {
  margin: 1px 0;
}

.van-step__title h3 {
  margin: 5px 0;
}
</style>