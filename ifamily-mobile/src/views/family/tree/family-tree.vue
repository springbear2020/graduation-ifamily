<template>
  <!-- wrapper高度 = viewport的高度 - 顶部导航栏高度46px - 底部1px占位高度 -->
  <div ref="wrapper" class="wrapper" :style="{height: `${viewport.h - 47}px`}"
       @mousedown="dragstart" @mousemove="drag" @mouseup="dragend" @mouseleave="dragend"
       @touchstart="dragstart" @touchmove="drag" @touchend="dragend" @touchcancel="dragend" @touchleave="dragend"
  >
    <div ref="container" class="container" :style="{top: `${position.y}px`, left: `${position.x}px`}">
      <family-tree-branch :tree="tree" @click-node="$emit('click-node', $event)" @click-arrow="clickArrow"/>
    </div>
  </div>
</template>

<script>
import FamilyTreeBranch from "@/views/family/tree/family-tree-branch";

export default {
  name: "family-tree",
  components: {FamilyTreeBranch},
  props: ['tree'],
  data() {
    return {
      mouseChangeDiff: 2,
      preventMouseEvents: false,
      dragAndDrop: {
        dragStarted: false,
        dragStartX: 0,
        dragStartY: 0,
        diffX: 0,
        diffY: 0,
        mouseCursor: "default",
      },
      position: {x: 0, y: 0},
      // 页面视口的宽度和高度
      viewport: {w: 0, h: 0},
    };
  },
  mounted() {
    this.viewport.w = document.documentElement.clientWidth
    this.viewport.h = document.documentElement.clientHeight
    // 页面缩放宽、高变化，重新设置视口大小
    window.onresize = () => {
      this.viewport.w = document.documentElement.clientWidth
      this.viewport.h = document.documentElement.clientHeight
    }
  },
  computed: {
    // wrapper 中心点坐标
    wrapperMiddle() {
      let wm = {x: 0, y: 0};
      wm.x = this.viewport.w / 2
      // (this.viewport.y - 47) / 2 为 wrapper 高度的一半，再减去顶部46px导航栏以减轻视觉误差（视觉上误以为未居中）
      wm.y = (this.viewport.h - 47) / 2 - 46
      return wm
    }
  },
  watch: {
    wrapperMiddle: {
      immediate: true,
      deep: true,
      handler(mid) {
        // 设置 container 的坐标使得树的根节点居中展示
        this.position.x = mid.x - 64
        this.position.y = mid.y - 48
      }
    }
  },
  methods: {
    clickArrow($event) {
      const eventX = $event.clientX
      const eventY = $event.clientY
      console.log('event-x: ', eventX, 'event-y: ', eventY)
    },
    dragstart(event) {
      // 设置拖拽点的起始坐标
      this.dragAndDrop.dragStartX = event.pageX || event.touches[0].pageX;
      this.dragAndDrop.dragStartY = event.pageY || event.touches[0].pageY;
      this.dragAndDrop.dragStarted = true;
    },
    drag(event) {
      if (this.dragAndDrop.dragStarted) {
        // 计算拖拽发生的位移为 diffX 和 diffY
        this.dragAndDrop.diffX = (event.pageX || event.touches[0].pageX) - this.dragAndDrop.dragStartX;
        this.dragAndDrop.diffY = (event.pageY || event.touches[0].pageY) - this.dragAndDrop.dragStartY;
        if (this.dragAndDrop.diffX > this.mouseChangeDiff || this.dragAndDrop.diffX < -this.mouseChangeDiff || this.dragAndDrop.diffY > this.mouseChangeDiff || this.dragAndDrop.diffX < -this.mouseChangeDiff) {
          this.preventMouseEvents = true;
          this.dragAndDrop.mouseCursor = "grabbing";
        }
        // 设置 container 的位置
        this.position.x += this.dragAndDrop.diffX;
        this.position.y += this.dragAndDrop.diffY;
        // 更新拖拽点的起始坐标
        this.dragAndDrop.dragStartX = event.pageX || event.touches[0].pageX;
        this.dragAndDrop.dragStartY = event.pageY || event.touches[0].pageY;
      }
    },
    dragend() {
      this.dragAndDrop.dragStarted = false;
      this.dragAndDrop.mouseCursor = "default";
      setTimeout(() => {
        this.preventMouseEvents = false;
      }, 150);
    }
  },
};
</script>

<style scoped>
.wrapper {
  overflow: hidden;
  cursor: auto;
  position: relative;
  width: 100%;
  z-index: 0;
  background-color: #39a9ed;
}

.container {
  position: absolute;
  background-color: red;
}
</style>