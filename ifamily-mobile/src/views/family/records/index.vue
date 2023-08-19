<template>
  <div>
    <van-nav-bar title="修谱记录" left-arrow @click-left="$router.replace('/family')"/>

    <van-tabs v-model="active">
      <!-- 修谱日志 -->
      <van-tab title="修谱日志">
        <van-steps direction="vertical" center active-color="#343a40">
          <van-step v-for="i in 10" :key="i">
            <h3>2016-07-12 12:40</h3>
            <p>光头勇添加了光头强</p>
            <p>光头勇删除了光头强</p>
            <p>光头勇修改了光头强</p>
          </van-step>
        </van-steps>
      </van-tab>
      <!-- 访问记录 -->
      <van-tab title="访问记录">
        <van-steps direction="vertical" center active-color="#343a40">
          <van-step v-for="i in 10" :key="i">
            <h3>2016-07-12 12:40</h3>
            <p>光头勇访问了家族</p>
          </van-step>
        </van-steps>
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
    }
  }
}
</script>

<style scoped>
.van-step__title h3 {
  margin: 5px 0;
}

.van-step__title p {
  margin: 1px 0;
}
</style>