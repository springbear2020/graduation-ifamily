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

<style>
/* 弹性单元格标题内容 */
.flex-cell-container .van-cell__title {
  display: flex;
}

/* 头像标题内容容器 */
.portrait-title-container {
  margin-left: 10px;
}

.portrait-title-container p {
  margin: 0 0;
}

.portrait-title-container .title {
  font-weight: bold;
}

/* 右、下间距 */
.right {
  margin-right: 1px;
}

.bottom {
  margin-bottom: 1px;
}

/* 块级按钮 */
.block-btn-container {
  margin: 16px;
}

/* 弹性容器 */
.flex-container {
  display: flex;
}

.flex-container .cover {
  margin: auto auto;
  padding: 30px
}

/* 封面下内容间间距 */
.van-grid-item__content p {
  margin: 0;
}

.cover-name {
  color: #323233;
  font-size: 14px;
  line-height: 24px;
  font-weight: bold;
}

.cover-signature {
  color: #969799;
  font-size: 12px;
  line-height: 18px;
}

/* 全地址输入框背景色 */
.full-address-container .van-field {
  background-color: #f7f8fa;
}

/* 滑动单元格操作按钮 */
.van-swipe-cell button {
  height: 100%;
}

/* 人员信息卡片左边距 */
.name-container {
  margin-left: 10px;
}
</style>