<template>
  <!-- echarts 绘制 LOGO 图标文字：百家谱 -->
  <div ref="logoPattern" class="logo-container"/>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "logo-pattern",
  mounted() {
    echarts.init(this.$refs.logoPattern).setOption({
      graphic: {
        elements: [{
          type: 'text',
          left: 'center',
          top: 'center',
          style: {
            text: '百家谱',
            fontSize: 55,
            fontWeight: 'bold',
            lineDash: [0, 200],
            lineDashOffset: 0,
            fill: 'transparent',
            stroke: '#6f42c1',
            lineWidth: 1
          },
          keyframeAnimation: {
            duration: 1500,
            loop: false,
            keyframes: [{
              percent: 0.7,
              style: {
                fill: 'transparent',
                lineDashOffset: 200,
                lineDash: [200, 0]
              }
            },
              {
                percent: 0.8,
                style: {
                  fill: 'transparent'
                }
              },
              {
                percent: 1,
              }]
          }
        }]
      }
    })
  }
}
</script>

<style>
.logo-container {
  width: 100%;
  height: 10em;
}
</style>