<template>
  <div>
    <van-nav-bar title="主页" @click-right="messageCenter">
      <template #right>
        <van-badge content="99+">
          <van-icon name="bell" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <van-swipe :autoplay="3000" indicator-color="white">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
      <van-swipe-item>5</van-swipe-item>
    </van-swipe>

    <van-tabs v-model="active">
      <van-tab title="动态">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="资讯">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      active: 0,
      isLoading: false,
    };

  },
  methods: {
    messageCenter() {
      this.$router.push('/home/message')
    },
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
  },
}
</script>

<style scoped>
/* TODO remove when the image url added */
.van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>