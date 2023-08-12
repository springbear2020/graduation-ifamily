<template>
  <div id="app">
    <router-view></router-view>
    <footerTab v-show="$route.meta.footerShow"></footerTab>
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
.img-portrait-father {
  display: flex;
}

.img-portrait {
  margin: auto auto;
  padding: 30px
}

.bottom-btn {
  margin: 16px;
}
</style>