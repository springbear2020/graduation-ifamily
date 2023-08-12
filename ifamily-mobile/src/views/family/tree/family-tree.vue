<template>
  <div ref="wrapper" class="wrapper"
       @mousedown="dragstart" @mousemove="drag" @mouseup="dragend" @mouseleave="dragend"
       @touchstart="dragstart" @touchmove="drag" @touchend="dragend" @touchcancel="dragend" @touchleave="dragend"
  >
    <div ref="container" style="position: absolute" :style="{top: `${position.y}px`, left: `${position.x}px`}">
      <family-tree-branch :tree="tree" @click-node="$emit('click-node', $event)"/>
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
      position: {x: 0, y: 0},
      dragAndDrop: {
        dragStarted: false,
        dragStartX: 0,
        dragStartY: 0,
        diffX: 0,
        diffY: 0,
        mouseCursor: "default",
      }
    };
  },
  methods: {
    mouseover(region) {
      if (!this.preventMouseEvents) {
        this.$emit("mouseover", region);
      }
    },
    mouseleave(region) {
      if (!this.preventMouseEvents) {
        this.$emit("mouseleave", region);
      }
    },
    dragstart(event) {
      if (this.mobilePreventScroll) {
        const breakpoint = this.mobilePreventScroll.breakpoint || 1024;
        const selector = this.mobilePreventScroll.selector || "body";
        const mql = window.matchMedia(`(max-width: ${breakpoint}px)`);
        if (mql.matches) {
          const $el = document.querySelector(selector);
          this.previousMobileOverflowType = $el.style.overflow;
          $el.style.overflow = "hidden";
        }
      }
      this.dragAndDrop.dragStartX = event.pageX || event.touches[0].pageX;
      this.dragAndDrop.dragStartY = event.pageY || event.touches[0].pageY;
      this.dragAndDrop.dragStarted = true;
      this.$emit("dragstart", event);
    },
    drag(event) {
      if (this.dragAndDrop.dragStarted) {
        this.dragAndDrop.diffX = (event.pageX || event.touches[0].pageX) - this.dragAndDrop.dragStartX;
        this.dragAndDrop.diffY = (event.pageY || event.touches[0].pageY) - this.dragAndDrop.dragStartY;
        if (this.dragAndDrop.diffX > this.mouseChangeDiff || this.dragAndDrop.diffX < -this.mouseChangeDiff || this.dragAndDrop.diffY > this.mouseChangeDiff || this.dragAndDrop.diffX < -this.mouseChangeDiff) {
          this.preventMouseEvents = true;
          this.dragAndDrop.mouseCursor = "grabbing";
        }
        this.position.x += this.dragAndDrop.diffX;
        this.position.y += this.dragAndDrop.diffY;
        this.dragAndDrop.dragStartX = event.pageX || event.touches[0].pageX;
        this.dragAndDrop.dragStartY = event.pageY || event.touches[0].pageY;
        this.$emit("drag", event);
      }
    },
    dragend() {
      this.dragAndDrop.dragStarted = false;
      this.dragAndDrop.mouseCursor = "default";
      if (this.mobilePreventScroll) {
        const selector = this.mobilePreventScroll.selector || "body";
        const $el = document.querySelector(selector);
        $el.style.overflow = this.previousMobileOverflowType;
      }
      setTimeout(() => {
        this.preventMouseEvents = false;
      }, 150);
      this.$emit("dragend", event);
    },
    getContainerClientRect() {
      return this.$refs.container.getBoundingClientRect();
    },
    getWrapperClientRect() {
      return this.$refs.wrapper.getBoundingClientRect();
    },
    centerTree() {
      return new Promise((resolve, reject) => {
        try {
          const wrapperCenterX = this.getWrapperClientRect().width / 2;
          const wrapperCenterY = this.getWrapperClientRect().height / 2;
          const mapCenterX = this.getContainerClientRect().width / 2;
          const mapCenterY = this.getContainerClientRect().height / 2;
          this.position.x = wrapperCenterX - mapCenterX;
          this.position.y = wrapperCenterY - mapCenterY;
          this.$emit("center-map");
          resolve(true);
        } catch (e) {
          reject(e);
        }
      });
    },
  },
};
</script>

<style scoped>
.wrapper {
  overflow: hidden;
  cursor: auto;
  position: relative;
  width: 100%;
  height: 500px;
  z-index: 0
}
</style>