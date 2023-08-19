<template>
  <!-- wrapper高度 = viewport的高度 - 顶部导航栏高度46px -->
  <div ref="wrapper" class="wrapper" :style="{height: `${viewport.h - 46}px`}"
       @mousedown="dragstart" @mousemove="drag" @mouseup="dragend" @mouseleave="dragend"
       @touchstart="dragstart" @touchmove="drag" @touchend="dragend" @touchcancel="dragend" @touchleave="dragend"
  >
    <div ref="container" class="container" style="top: 0; left: 0">
      <family-tree-branch :tree="tree" @click-node="$emit('click-node', $event)" @center-node="centerNode"/>
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
      // wrapper 拖拽点
      point: {
        dragStarted: false,
        x: 0,
        y: 0,
        mouseCursor: "default",
      },
      // 视口宽度和高度
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
    // 树的根节点居中展示
    const rootElement = document.getElementsByClassName('root-node')[0]
    this.centerNode(rootElement)
  },
  methods: {
    centerNode(element) {
      this.$nextTick(() => {
        /*
         * 计算页面中心点的坐标为 (centerX, centerY)
         *  - 理论上 centerX = document.body.clientWidth，为使得 element 容器居中展示，需再使 centerX -= width / 2
         *  - 理论上 centerY = document.body.clientHeight / 2，但因页面顶部导航栏占据高度 46px，为减小视觉上的中心点误差，故再使 centerY -= 46
         */
        const {top, left, width} = element.getBoundingClientRect();
        const centerY = document.body.clientHeight / 2 - 46
        const centerX = document.body.clientWidth / 2 - width / 2;
        // 计算 element 容器左上角距页面中心点 (centerX, centerY) 的距离以得出 element 父容器 container 的移动距离
        const moveY = centerY - top;
        const moveX = centerX - left;
        //  根据 container 容器的原始坐标和需要移动的距离计算得到其新位置从而使得 element 居中
        const containerElement = document.getElementsByClassName('container')[0];
        const {top: originY, left: originX} = containerElement.style;
        containerElement.style.top = parseInt(originY) + moveY + 'px';
        containerElement.style.left = parseInt(originX) + moveX + 'px';
      })
    },
    dragstart(event) {
      // 设置拖拽点的起始坐标
      this.point.x = event.pageX || event.touches[0].pageX;
      this.point.y = event.pageY || event.touches[0].pageY;
      this.point.dragStarted = true;
      this.$emit("dragstart", event);
    },
    drag(event) {
      if (this.point.dragStarted) {
        // 计算得到拖拽时发生的位移
        const moveX = (event.pageX || event.touches[0].pageX) - this.point.x;
        const moveY = (event.pageY || event.touches[0].pageY) - this.point.y;
        const {mouseChangeDiff} = this
        if (moveX > mouseChangeDiff || moveX < -mouseChangeDiff || moveY > mouseChangeDiff || moveX < -mouseChangeDiff) {
          this.preventMouseEvents = true;
          this.point.mouseCursor = "grabbing";
        }
        //  设置 container 的位置实现拖拽移动效果
        const containerElement = document.getElementsByClassName('container')[0];
        const {top: originY, left: originX} = containerElement.style;
        containerElement.style.top = parseInt(originY) + moveY + 'px';
        containerElement.style.left = parseInt(originX) + moveX + 'px';
        // 更新拖拽点的起始坐标
        this.point.x = event.pageX || event.touches[0].pageX;
        this.point.y = event.pageY || event.touches[0].pageY;
      }
    },
    dragend() {
      this.point.dragStarted = false;
      this.point.mouseCursor = "default";
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
}

.container {
  position: absolute;
}
</style>