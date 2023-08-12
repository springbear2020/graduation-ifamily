<template>
  <div>
    <van-nav-bar title="权限设置" left-arrow @click-left="$router.replace('/family/manage')"/>

    <van-tabs v-model="active">
      <van-tab title="管理员">
        <van-cell center title="删除家族">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑家族">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看家族">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑个人">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="退出家族">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="新增成员">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除成员">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑成员">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看成员">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="发布公告">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除公告">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑公告">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="审核相册">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="发布相册">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除相册">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑相册">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
      </van-tab>
      <van-tab title="家族成员">
        <van-cell center title="编辑个人信息">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="添加亲人">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
      </van-tab>
      <van-tab title="陌生人">
        <van-cell center title="查看家族信息">
          <template #right-icon>
            <van-switch v-model="stranger.familyInfo" size="20"/>
          </template>
        </van-cell>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      active: 1,
      stranger: {
        familyInfo: false
      }
    }
  }
}
</script>