<template>
  <div>
    <van-nav-bar fixed title="家族树谱" left-arrow @click-left="backFamily" @click-right="showPopover = true" style="z-index: 1">
      <template #right>
        <!-- 右上角操作弹出层 -->
        <van-popover placement="bottom-end" trigger="click"
                     v-model="showPopover" :actions="popoverActions" @select="onSelect">
          <template #reference>
            <van-icon name="apps-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 家族系谱图 -->
    <FamilyTree :tree="tree" @click-node="handleViewPeople"/>

    <!-- 家族成员搜索动作面板 -->
    <van-action-sheet v-model="memberSearchSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="家族成员姓名" @search="onSearch">
        <template #action>
          <div @click="onSearch">搜索</div>
        </template>
      </van-search>
      <van-empty description="无内容"/>
    </van-action-sheet>
    <!-- 点击树节点人员操作面板 -->
    <van-action-sheet v-model="peopleClickSheet" cancel-text="取消" description="光头勇">
      <van-grid :column-num="3" :gutter="8" square :border="false">
        <van-grid-item icon="cluster-o" text="ta 的关系"/>
        <van-grid-item icon="manager-o" text="ta 的主页" @click="handlePeopleHomePage"/>
        <van-grid-item icon="add-o" text="添加亲人" @click="addRelatives"/>
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
      popoverActions: [
        {text: '展开全部', icon: 'expand-o'},
        {text: '折叠全部', icon: 'shrink'},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      memberSearchSheet: false,
      memberName: '',
      peopleClickSheet: false
    }
  },
  watch: {
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
    handleViewPeople(node) {
      console.log(node)
      this.peopleClickSheet = true
    },
    backFamily() {
      this.$router.replace('/family')
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
          this.memberSearchSheet = true
          break;
        default:
      }
    },
    handlePeopleHomePage() {
      this.$router.push('/family/members/people')
    },
    onSearch() {
      this.$toast.success('搜索成员')
    },
    addRelatives() {
      this.$router.push('/family/manage/members/add')
    }
  }
}
</script>

<style scoped>
.van-action-sheet__gap {
  height: 0;
}
</style>