<template>
  <div>
    <van-nav-bar title="成员管理" left-arrow @click-left="backFamilyManagement"/>

    <!-- 搜索框 -->
    <van-search show-action v-model="memberName" placeholder="家族成员姓名" @search="handleSearchMember">
      <template #action>
        <div @click="handleSearchMember">搜索</div>
      </template>
    </van-search>

    <!-- 家族成员 -->
    <van-cell-group>
      <van-cell is-link center @click="memberManageSheet = true">
        <template #title>
          <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
          <p class="name-container">
            <span>光头勇</span>
            <van-icon name="male" color="#007bff" class="iconfont" class-prefix="icon" size="16"/>
          </p>
        </template>
        <template #default>
          <van-tag color="#28a745">健在</van-tag>
        </template>
      </van-cell>
    </van-cell-group>

    <!-- 成员管理操作面板 -->
    <van-action-sheet v-model="memberManageSheet" cancel-text="取消" description="光头勇">
      <van-grid :gutter="8" square :border="false">
        <van-grid-item icon="add-o" text="添加亲人" to="/family/manage/members/add"/>
        <van-grid-item icon="delete-o" text="移除此人" @click="removeFamilyPeople"/>
        <van-grid-item icon="edit" text="编辑信息" to="/family/manage/members/edit"/>
        <van-grid-item icon="manager-o" text="ta 的主页" to="/family/members/people"/>
      </van-grid>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      memberName: '',
      memberManageSheet: false
    }
  },
  methods: {
    backFamilyManagement() {
      this.$router.replace('/family/manage')
    },
    handleSearchMember() {
      this.$toast.success('搜索成员')
    },
    removeFamilyPeople() {
      this.$dialog.confirm({
        title: '删除提示',
        message: '您确定要删除《光头勇》这个家族成员吗？',
      }).then(() => {
        this.$toast.success('删除成功')
        this.memberManageSheet = false
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.name-container {
  margin-left: 10px;
}

.iconfont {
  margin-left: 5px;
}
</style>