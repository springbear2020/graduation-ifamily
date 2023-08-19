<template>
  <div>
    <!-- 搜索框 -->
    <van-search show-action v-model="uid" placeholder="UID" @search="$toast('不给谁看')">
      <template #action>
        <div @click="$toast('不给谁看')">搜索</div>
      </template>
    </van-search>
    <!-- 人员列表 -->
    <van-swipe-cell v-for="i in 5" :key="i">
      <portrait-desc :person="person" :more="false"/>
      <template #right>
        <van-button square type="danger" text="移除" @click="$toast('移除')"/>
      </template>
    </van-swipe-cell>
  </div>
</template>

<script>
import PortraitDesc from '@/components/portrait-desc'

export default {
  name: "search-people-list",
  components: {PortraitDesc},
  data() {
    return {
      uid: '',
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: 'UID:05482352'
      },
    }
  }
}
</script>
