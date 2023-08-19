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
/* 封面图片居中显示 */
.flex-container {
  display: flex;
}

.flex-container .cover {
  margin: auto auto;
  padding: 30px
}

/* 块级按钮 */
.block-btn-container {
  margin: 16px;
}

/* 详细地址文本域背景色 */
.full-address-container .van-field {
  background-color: #f7f8fa;
}

/* 头像右侧文本间距和字体 */
.portrait-title-container {
  margin-left: 10px;
}

.portrait-title-container p {
  margin: 0 0;
}

.portrait-title-container .title {
  font-weight: bold;
}

/* 步骤条文字间距 */
.van-step__title h3 {
  margin: 5px 0;
}

.van-step__title p {
  margin: 1px 0;
}

/* 滑动单元格按钮 */
.van-swipe-cell button {
  height: 100%;
}

/* 右侧、底部间距 */
.right {
  margin-right: 1px;
}

.bottom {
  margin-bottom: 1px;
}

/* 弹性单元格标题 */
.flex-cell-title .van-cell__title {
  display: flex;
}

/* 人员信息 */
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
</style>