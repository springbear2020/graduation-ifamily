<template>
  <div>
    <van-nav-bar title="家族公告" left-arrow @click-left="$router.replace('/family')"/>

    <div v-for="i in 10" :key="i">
      <van-cell-group>
        <portrait-desc :person="person" @click.native="$toast('查看人员')"/>
        <van-cell class="cell-top"
                  value="族谱作为一个记载以血缘关系为主体的家族世代繁衍和重要人物事迹的特殊图书体裁。"
        />
      </van-cell-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '2023-02-25 12:05'
      },
    }
  }
}
</script>

<style scoped>
.cell-top {
  padding-top: 0;
}
</style>