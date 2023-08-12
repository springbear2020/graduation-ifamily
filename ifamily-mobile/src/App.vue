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