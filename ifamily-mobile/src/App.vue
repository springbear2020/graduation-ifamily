<template>
  <div id="app">
    <router-view/>
    <footerTab v-show="$route.meta.footerShow"/>
    <loading/>
  </div>
</template>

<script>
import footerTab from "@/components/layout/footer-tab";
import Loading from "@/components/basis/loading";

export default {
  name: 'App',
  components: {Loading, footerTab}
}
</script>
