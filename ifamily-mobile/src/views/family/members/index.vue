<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="$router.replace('/family')" @click-right="showSearchSheet = true">
      <template #right>
        <van-icon name="search" size="20"/>
      </template>
    </van-nav-bar>

    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="number in indexList" :key="number">
        <!-- 世代 -->
        <van-index-anchor :index="number">第 {{ number }} 世</van-index-anchor>

        <!-- 家族成员男 -->
        <van-cell is-link center @click="$router.push('/family/members/people/1')" class="flex-cell-container">
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
      </div>
    </van-index-bar>

    <!-- 成员搜索动作面板 -->
    <van-action-sheet v-model="showSearchSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="家族成员姓名" @search="$toast(memberName)">
        <template #action>
          <div @click="$toast(memberName)">搜索</div>
        </template>
      </van-search>
      <van-empty description="无内容"/>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      indexList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      memberName: '',
      showSearchSheet: false,
    }
  }
}
</script>