<template>
  <div>
    <van-nav-bar
        title="首页"
        @click-right="notification"
    >
      <template #right>
        <van-icon name="envelop-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
    </van-swipe>

    <van-tabs v-model="active">
      <van-tab title="家族">
        <van-empty description="无内容"/>
      </van-tab>
      <van-tab title="关注">
        <van-empty description="无内容"/>
      </van-tab>
      <van-tab title="资讯">
        <van-empty description="无内容"/>
      </van-tab>
    </van-tabs>


  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      images: [
        'https://img01.yzcdn.cn/vant/apple-1.jpg',
        'https://img01.yzcdn.cn/vant/apple-2.jpg',
      ],
      active: 1
    };

  },
  methods: {
    notification() {
      this.$router.push('/home/announcement')
    }
  }
}
</script>

<style>
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>