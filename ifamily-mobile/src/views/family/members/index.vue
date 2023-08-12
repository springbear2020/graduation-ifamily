<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="backFamily"/>

    <van-search show-action v-model="memberName" placeholder="请输入家族成员姓名" @search="handleSearchMember">
      <template #action>
        <div @click="handleSearchMember">搜索</div>
      </template>
    </van-search>

    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="number in indexList" :key="number">
        <van-index-anchor :index="number">第 {{ number }} 世</van-index-anchor>

        <!-- 家族成员信息 -->
        <van-cell is-link center @click="handleViewMember">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="text-margin">
              <span>光头勇</span>
              <van-icon name="male" color="#007bff" class="iconfont" class-prefix="icon" size="15"/>
            </p>
          </template>
          <template #default>
            <van-tag color="#28a745">健在</van-tag>
          </template>
        </van-cell>
        <van-cell is-link center @click="handleViewMember">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="text-margin">
              <span>小仙女</span>
              <van-icon name="female" color="#e83e8c" class="iconfont" class-prefix="icon" size="15"/>
            </p>
          </template>
          <template #default>
            <van-tag color="#6c757d">已故</van-tag>
          </template>
        </van-cell>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      indexList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      memberName: '',
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    handleViewMember() {
      this.$router.push('/family/members/people')
    },
    handleSearchMember() {
      this.$toast.success(this.memberName);
    },
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.text-margin {
  margin-left: 10px;
}

.iconfont {
  margin-left: 5px;
}
</style>