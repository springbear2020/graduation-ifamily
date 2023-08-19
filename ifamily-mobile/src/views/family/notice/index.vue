<template>
  <div>
    <van-nav-bar title="家族公告" left-arrow @click-left="$router.replace('/family')" @click-right="showActionSheet = true">
      <template #right>
        <van-icon name="notes-o" size="20"/>
      </template>
    </van-nav-bar>

    <div v-for="i in 10" :key="i">
      <van-cell-group>
        <portrait-desc :person="person" @click.native="$toast('查看人员')"/>
        <van-cell class="cell-value-top"
                  value="族谱作为一个记载以血缘关系为主体的家族世代繁衍和重要人物事迹的特殊图书体裁。"
        />
      </van-cell-group>
    </div>

    <!-- 家族公告发布面板 -->
    <van-action-sheet v-model="showActionSheet" title="发布公告">
      <van-field rows="5" autosize type="textarea" maxlength="1000" placeholder="发布公告" show-word-limit
                 v-model="notice"/>
      <van-button type="info" block @click="$toast('管理员审核'); showActionSheet = false">确定</van-button>
    </van-action-sheet>
  </div>
</template>

<script>
import PortraitDesc from '@/components/portrait-desc';

export default {
  name: "index",
  components: {PortraitDesc},
  data() {
    return {
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '2023-02-25 12:05'
      },
      showActionSheet: false,
      notice: ''
    }
  }
}
</script>

<style scoped>
.cell-value-top {
  padding-top: 0;
}
</style>
