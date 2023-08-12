<template>
  <div>
    <van-nav-bar title="访问控制" left-arrow @click-left="$router.replace('/family/manage')" @click-right="confirmAccess">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 访问权限单选 -->
    <van-radio-group v-model="whoCanSee">
      <van-cell-group>
        <van-cell title="所有人" clickable @click="whoCanSee = '1'">
          <template #right-icon>
            <van-radio name="1"/>
          </template>
        </van-cell>
        <van-cell title="管理员" clickable @click="whoCanSee = '2'">
          <template #right-icon>
            <van-radio name="2"/>
          </template>
        </van-cell>
        <van-cell title="创建者" clickable @click="whoCanSee = '3'">
          <template #right-icon>
            <van-radio name="3"/>
          </template>
        </van-cell>
        <van-cell title="家族成员" clickable @click="whoCanSee = '4'">
          <template #right-icon>
            <van-radio name="4"/>
          </template>
        </van-cell>
      </van-cell-group>
    </van-radio-group>

    <!-- 额外人员、不给谁看 -->
    <van-collapse v-model="activeName" accordion>
      <van-collapse-item title="额外人员" name="1">
        <!-- 搜索框 -->
        <van-search show-action v-model="uid" placeholder="uid" @search="accessMemberAdd">
          <template #action>
            <div @click="accessMemberAdd">搜索</div>
          </template>
        </van-search>
        <portrait-desc :person="person" :more="true" v-for="i in 5" :key="i"/>
      </van-collapse-item>
      <van-collapse-item title="不给谁看" name="2">
        <!-- 搜索框 -->
        <van-search show-action v-model="uid" placeholder="uid" @search="accessMemberAdd">
          <template #action>
            <div @click="accessMemberAdd">搜索</div>
          </template>
        </van-search>
        <portrait-desc :person="person" :more="true" v-for="i in 10" :key="i"/>
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      whoCanSee: '4',
      activeName: ['1'],
      uid: '',
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: 'UID:05482352'
      },
    }
  },
  methods: {
    accessMemberAdd() {
      this.$toast.success('添加访问人员')
    },
    confirmAccess() {
      this.$toast.success('修改成功');
    }
  }
}
</script>