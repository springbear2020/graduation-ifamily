<template>
  <div>
    <van-nav-bar
        left-arrow
        title="通讯录"
        @click-right="addFriend"
        @click-left="backMessages"
    >
      <template #right>
        <van-icon name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-search v-model="friendName" placeholder="请输入朋友名字"/>

    <van-index-bar>
      <van-index-anchor index="A"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>

      <van-index-anchor index="B"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
      <van-cell title="文本"/>
    </van-index-bar>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      friendName: ''
    }
  },
  methods: {
    addFriend() {
    },
    backMessages() {
      this.$router.replace('/message')
    }
  }
}
</script>