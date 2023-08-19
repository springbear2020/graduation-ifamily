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
    <FamilyTree :tree="tree" @click-node="clickPeopleCard" ref="familyTree"/>

    <!-- 家族成员搜索动作面板 -->
    <van-action-sheet v-model="showSearchSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="家族成员姓名" @search="locateMemberNode(memberName)">
        <template #action>
          <div @click="locateMemberNode(memberName)">搜索</div>
        </template>
      </van-search>
      <van-empty description="无内容"/>
    </van-action-sheet>
    <!-- 查看人员信息操作面板 -->
    <van-action-sheet v-model="showPeopleSheet" cancel-text="取消" description="光头勇">
      <van-grid :column-num="3" :gutter="8" square :border="false">
        <van-grid-item icon="cluster-o" text="ta 的关系" @click="$toast.success('树状关系')"/>
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
      showPopover: false,
      actions: [
        {text: '展开全部', icon: 'expand-o', disabled: false},
        {text: '折叠全部', icon: 'shrink', disabled: true},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      showSearchSheet: false,
      memberName: '',
      showPeopleSheet: false,
      // [true]全部节点折叠 [false]!全部节点折叠
      isAllFold: true,
      rotate: 0
    }
  },
  watch: {
    isAllFold: {
      immediate: true,
      handler(newVal) {
        // 统一设置树的每个节点的展开状态
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

        // 修改展开全部和折叠全部的禁用状态
        this.actions[0].disabled = !newVal
        this.actions[1].disabled = newVal
      }
    }
  },
  methods: {
    clickPeopleCard(node) {
      console.log(node)
      this.showPeopleSheet = true
    },
    onSelect(action, index) {
      // [0]展开全部 [1]折叠全部 [2]定位到我 [3]搜索成员
      switch (index) {
        case 0:
          this.isAllFold = false;
          this.locateMemberNode(this.tree.id)
          break;
        case 1:
          this.isAllFold = true;
          this.locateMemberNode(this.tree.id)
          break;
        case 2:
          this.isAllFold = false
          this.locateMemberNode(21)
          break;
        case 3:
          this.isAllFold = false
          this.showSearchSheet = true
          break;
        default:
      }
    },
    locateMemberNode(pid) {
      this.$nextTick(() => {
        const personElement = document.getElementsByClassName(`pid-${pid}`)[0]
        // 调用子组件的树节点居中定位方法
        this.$refs.familyTree.centerNode(personElement)
        // 聚焦节点旋转动画
        this.$nextTick(() => {
          personElement.style.transform = `rotate(${this.rotate += 360}deg)`;
          this.rotate = this.rotate > 360 ? 0 : this.rotate;
        })
      })
      this.showSearchSheet = false
    }
  }
}
</script>

<style scoped>
.van-action-sheet__gap {
  height: 0;
}
</style>