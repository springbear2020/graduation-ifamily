<template>
  <div>
    <van-nav-bar title="家族树谱" left-arrow @click-left="$router.replace('/family')" @click-right="showPopover = true">
      <template #right>
        <!-- 家族树右上角操作气泡弹出框 -->
        <van-popover placement="bottom-end" trigger="click"
                     v-model="showPopover" :actions="actions" @select="onSelect">
          <template #reference>
            <van-icon name="apps-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 家族系谱图 -->
    <FamilyTree :tree="tree" @click-node="clickPeopleCard"/>

    <!-- 家族成员搜索动作面板 -->
    <van-action-sheet v-model="showSearchSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="家族成员姓名" @search="$toast.success('搜索成员')">
        <template #action>
          <div @click="$toast.success('搜索成员')">搜索</div>
        </template>
      </van-search>
      <van-empty description="无内容"/>
    </van-action-sheet>
    <!-- 查看人员信息操作面板 -->
    <van-action-sheet v-model="showPeopleSheet" cancel-text="取消" description="光头勇">
      <van-grid :column-num="3" :gutter="8" square :border="false">
        <van-grid-item icon="cluster-o" text="ta 的关系"/>
        <van-grid-item icon="manager-o" text="ta 的主页" @click="$router.push('/family/members/people/1')"/>
        <van-grid-item icon="add-o" text="添加亲人" @click="$router.push('/family/manage/members/add/0')"/>
      </van-grid>
    </van-action-sheet>
  </div>
</template>

<script>
import FamilyTree from '@/views/family/tree/family-tree';
import stone from '@/assets/json/stone-story.json'

export default {
  name: "index",
  components: {FamilyTree},
  data() {
    return {
      tree: stone,
      // [true]折叠全部节点 [false]展开全部节点
      isAllFold: true,
      showPopover: false,
      actions: [
        {text: '展开全部', icon: 'expand-o'},
        {text: '折叠全部', icon: 'shrink'},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      showSearchSheet: false,
      memberName: '',
      showPeopleSheet: false
    }
  },
  watch: {
    // TODO 展开一个后不可全部折叠
    isAllFold: {
      immediate: true,
      handler(newVal) {
        let extendKey = function (jsonData) {
          jsonData.extend = !newVal;
          if (Array.isArray(jsonData.children)) {
            jsonData.children.forEach(c => {
              extendKey(c)
            })
          }
          return jsonData;
        }
        extendKey(this.tree)
      }
    }
  },
  methods: {
    clickPeopleCard(node) {
      console.log(node)
      this.showPeopleSheet = true
    },
    onSelect(action, index) {
      switch (index) {
        case 0:
          this.isAllFold = false;
          break;
        case 1:
          this.isAllFold = true;
          break;
        case 2:
          this.$toast.success('定位到我')
          break;
        case 3:
          this.showSearchSheet = true
          break;
        default:
      }
    }
  }
}
</script>

<style scoped>
.van-action-sheet__gap {
  height: 0;
}
</style>