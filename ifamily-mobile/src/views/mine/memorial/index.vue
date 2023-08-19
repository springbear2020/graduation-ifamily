<template>
  <div>
    <van-nav-bar title="纪念日" left-arrow @click-left="$router.replace('/mine')"/>

    <van-tabs v-model="active">
      <!-- 生日 -->
      <van-tab title="生日">
        <van-cell center title="光头勇" value="3 天后" label="2023-03-05"/>
        <van-cell center title="光头强" value="10 天后" label="农历 二零二三年三月三日"/>
      </van-tab>
      <!-- 祭日 -->
      <van-tab title="祭日">
        <van-cell center title="光头强" value="10 天后" label="农历 二零二三年三月三日"/>
        <van-cell center title="光头勇" value="3 天后" label="2023-03-05"/>
        <van-cell center title="光头强" value="10 天后" label="农历 二零二三年三月三日"/>
        <van-cell center title="光头勇" value="3 天后" label="2023-03-05"/>
        <van-cell center title="光头勇" value="3 天后" label="2023-03-05"/>
        <van-cell center title="光头强" value="10 天后" label="农历 二零二三年三月三日"/>
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