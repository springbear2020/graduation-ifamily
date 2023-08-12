<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="backFamily"/>

    <van-search show-action v-model="memberName" placeholder="请输入家族成员姓名" @search="searchMember">
      <template #action>
        <div @click="searchMember">搜索</div>
      </template>
    </van-search>

    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="number in indexList" :key="number">
        <van-index-anchor :index="number">第 {{ number }} 世</van-index-anchor>

        <van-cell is-link center @click="viewMember">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="text-margin">
              <span>光头勇</span>
              <van-icon name="male" color="#1989fa" class="iconfont" class-prefix="icon" size="15"/>
            </p>
          </template>
          <template #default>
            <van-tag type="primary">健在</van-tag>
          </template>
        </van-cell>

        <van-cell is-link center @click="viewMember">
          <template #title>
            <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <p class="text-margin">
              <span>小仙女</span>
              <van-icon name="female" color="#ee0a24" class="iconfont" class-prefix="icon" size="15"/>
            </p>
          </template>
          <template #default>
            <van-tag type="default">已故</van-tag>
          </template>
        </van-cell>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
export default {
  name: "index.vue",
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
    viewMember() {
      this.$router.push('/family/people')
    },
    searchMember() {
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
  margin-left: 2px;
}
</style>