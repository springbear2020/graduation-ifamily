<template>
  <div id="app">
    <router-view/>
    <footerTab v-show="$route.meta.footerShow"/>
  </div>
</template>

<script>
import footerTab from "@/components/layout/footer-tab";

export default {
  name: 'App',
  components: {footerTab},
}
</script>

<style>#nprogress .bar {
  background: #007bff !important;
}</style>
