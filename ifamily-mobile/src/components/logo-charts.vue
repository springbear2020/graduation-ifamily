<template>
  <div ref="logoCharts" style="width: 100%; height: 10rem;"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "logo",
  mounted() {
    echarts.init(this.$refs.logoCharts).setOption({
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
            stroke: '#1989FA',
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
                // Stop for a while.
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