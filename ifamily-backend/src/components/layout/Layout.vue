<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device === 'mobile' && sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <the-sidebar class="sidebar-container"/>
    <div :class="{hasTagsView: needTagsView}" class="main-container">
      <div :class="{'fixed-header': fixedHeader}">
        <the-navbar/>
        <the-tags-view v-if="needTagsView"/>
      </div>
      <the-app-main/>
      <the-settings v-if="showSettings"/>
    </div>
  </div>
</template>

<script>
import TheSidebar from "@/components/layout/sidebar/TheSidebar";
import TheTagsView from "@/components/layout/tags/TheTagsView";
import TheAppMain from "@/components/layout/main/TheAppMain";
import TheNavbar from "@/components/layout/navbar/TheNavbar";
import TheSettings from "@/components/layout/settings/TheSettings";
import {mapGetters, mapState} from 'vuex'
import resizeHandler from '@/components/layout/resize-handler';

export default {
  name: "Layout",
  mixins: [resizeHandler],
  components: {TheSidebar, TheTagsView, TheAppMain, TheNavbar, TheSettings},
  computed: {
    ...mapGetters(['sidebar', 'device']),
    ...mapState({
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', {withoutAnimation: false})
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
@import "~@/styles/variables.module.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px)
}

.mobile .fixed-header {
  width: 100%;
}
</style>

