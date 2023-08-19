<template>
  <div>
    <svg-icon :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" @click="click"/>
  </div>
</template>

<script>
import screenfull from 'screenfull'

export default {
  name: "ScreenFull",
  data() {
    return {
      isFullscreen: false
    }
  },
  mounted() {
    if (screenfull.enabled) {
      screenfull.on('change', this.change)
    }
  },
  beforeDestroy() {
    if (screenfull.enabled) {
      screenfull.off('change', this.change)
    }
  },
  methods: {
    click() {
      if (!screenfull.enabled) {
        this.$message({
          message: '很抱歉，您的浏览器不支持此功能',
          type: 'warning'
        })
        return false
      }
      screenfull.toggle()
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    }
  }
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
