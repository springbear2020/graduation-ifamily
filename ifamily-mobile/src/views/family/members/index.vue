<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="$router.replace('/family')"/>

    <!-- 搜索框 -->
    <van-search show-action v-model="memberName" placeholder="家族成员姓名" @search="$toast(memberName)">
      <template #action>
        <div @click="$toast(memberName)">搜索</div>
      </template>
    </van-search>

    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="number in indexList" :key="number">
        <!-- 世代 -->
        <van-index-anchor :index="number">第 {{ number }} 世</van-index-anchor>

        <!-- 家族成员男 -->
        <van-cell is-link center @click="$router.push('/family/members/people/0')" class="flex-cell-title">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="name-container">
              <span>光头勇</span>
              <sex-tag/>
            </p>
          </template>
          <template #default>
            <van-tag color="#28a745">健在</van-tag>
          </template>
        </van-cell>
        <!-- 家族成员女 -->
        <van-cell is-link center @click="$router.push('/family/members/people/0')" class="flex-cell-title">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="name-container">
              <span>小仙女</span>
              <sex-tag :sex="1"/>
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
  }
}
</script>

<style scoped>
.name-container {
  margin-left: 10px;
}
</style>