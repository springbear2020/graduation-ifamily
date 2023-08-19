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
/* portrait-desc.vue, message-box.vue, family-members.vue, family-manage-members.vue */
.flex-cell-container .van-cell__title {
  display: flex;
}

.portrait-title-container {
  margin-left: 10px;
}

.portrait-title-container p {
  margin: 0 0;
}

.portrait-title-container .title {
  font-weight: bold;
}

/* desc-tag.vue, people-tag.vue, image-list.vue */
.right {
  margin-right: 1px;
}

.bottom {
  margin-bottom: 1px;
}

/* user-login.vue, user-reset.vue, mine-security.vue */
.block-btn-container {
  margin: 16px;
}

/* family-info.vue, family-manage-form.vue */
.flex-container {
  display: flex;
}

.flex-container .cover {
  margin: auto auto;
  padding: 30px
}

/* members-people.vue */
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

/* family-manage-form.vue */
.full-address-container .van-field {
  background-color: #f7f8fa;
}

/* message-box.vue, family-manage-access.vue */
.van-swipe-cell button {
  height: 100%;
}

/* members.vue, manage-members.vue*/
.name-container {
  margin-left: 10px;
}
</style>