<template>
  <div>
    <van-nav-bar title="家族管理" left-arrow @click-left="$router.replace('/family')"/>

    <van-cell title="家族资料" is-link to="/family/admin/info/form/1"/>
    <van-cell title="成员管理" is-link to="/family/admin/member" class="top"/>
    <van-cell title="发布公告" is-link to="/family/admin/notice/form" class="top"/>
    <van-cell title="上传图片" is-link to="/family/admin/album/form"/>
    <van-cell title="编写大事" is-link to="/family/admin/memorabilia/form"/>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}
</style>
