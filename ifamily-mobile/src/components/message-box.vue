<template>
  <van-cell-group>
    <van-cell center v-for="i in 20" :key="i">
      <template #title>
        <van-image round width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
        <div class="desc">
          <p class="name">光头勇</p>
          <p class="van-ellipsis">自动省略多余的文本。</p>
        </div>
      </template>

      <template #right-icon>
        <span>2023-02-23 18:55:23</span>
      </template>
    </van-cell>
  </van-cell-group>
</template>

<script>
export default {
  name: "message-box"
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.desc {
  margin-left: 10px;
}

.desc p {
  margin: 0 0;
}

.desc .name {
  font-size: large;
  font-weight: bold;
}
</style>