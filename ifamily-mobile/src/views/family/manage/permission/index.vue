<template>
  <div>
    <van-nav-bar title="权限设置" left-arrow @click-left="$router.replace('/family/manage')"/>

    <!-- 人员类型 -->
    <van-radio-group v-model="memberType" direction="horizontal">
      <div class="member-type-container">
        <van-radio name="0">家族成员</van-radio>
        <van-radio name="1">管理员</van-radio>
      </div>
    </van-radio-group>

    <!-- 权限列表 -->
    <van-collapse v-model="activeName" accordion>
      <!-- 家族管理 -->
      <van-collapse-item title="家族管理" name="1">
        <van-cell center title="删除家族">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑家族">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看家族">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
      <!-- 家族树谱 -->
      <van-collapse-item title="家族树谱" name="2">
        <van-cell center title="查看树谱">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="搜索成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="操作成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
      <!-- 家族成员 -->
      <van-collapse-item title="家族成员" name="3">
        <van-cell center title="新增成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看成员">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
      <!-- 家族相册 -->
      <van-collapse-item title="家族相册" name="4">
        <van-cell center title="发布相册">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除相册">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑相册">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看相册">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="审核相册">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
      <!-- 家族公告 -->
      <van-collapse-item title="家族公告" name="5">
        <van-cell center title="发布公告">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="删除公告">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="编辑公告">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看公告">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="审核公告">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
      <!-- 修谱记录 -->
      <van-collapse-item title="修谱记录" name="6">
        <van-cell center title="查看日志">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
        <van-cell center title="查看记录">
          <template #right-icon>
            <van-switch v-model="status" size="20"/>
          </template>
        </van-cell>
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      activeName: "0",
      // [0]家族成员 [1]管理员
      memberType: '0',
      status: false
    }
  }
}
</script>

<style scoped>
.member-type-container {
  display: flex;
  margin: auto;
  padding: 20px 0
}
</style>