<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="$router.replace('/family')" @click-right="$toast('搜索成员')">
      <template #right>
        <van-icon name="search" size="20"/>
      </template>
    </van-nav-bar>

    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="number in indexList" :key="number">
        <!-- 世代 -->
        <van-index-anchor :index="number">第 {{ number }} 世</van-index-anchor>

        <!-- 家族成员男 -->
        <van-cell is-link center @click="$router.push('/family/member/people/1')">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p>
              <span>光头勇</span>
              <sex-tag :sex="1"/>
            </p>
          </template>
          <template #default>
            <van-tag color="#28a745">健在</van-tag>
          </template>
        </van-cell>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'

export default {
  name: "index",
  components: {SexTag},
  data() {
    return {
      indexList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell__title {
  display: flex;
}

.van-cell__title p {
  margin-left: 8px;
}
</style>
