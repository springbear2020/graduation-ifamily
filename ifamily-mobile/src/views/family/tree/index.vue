<template>
  <div>
    <van-nav-bar title="家族树谱" left-arrow @click-left="$router.replace(dstRoute)" @click-right="showPopover = true">
      <template #right>
        <!-- 右上角操作气泡弹出框 -->
        <van-popover placement="bottom-end" trigger="click"
                     v-model="showPopover" :actions="actions" @select="onSelect">
          <template #reference>
            <van-icon name="apps-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 家族系谱图 -->
    <FamilyTree :tree="tree" @click-node="clickPeopleNode" ref="familyTree"/>

    <!-- 成员搜索动作面板 -->
    <van-action-sheet v-model="showSearchSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="家族成员姓名" @search="locateMemberNode(memberName)">
        <template #action>
          <div @click="locateMemberNode(memberName)">搜索</div>
        </template>
      </van-search>
      <van-empty description="无内容"/>
    </van-action-sheet>
    <!-- 树节点点击操作面板 -->
    <van-action-sheet v-model="showPeopleSheet" cancel-text="取消" description="光头勇">
      <van-grid square :border="false">
        <van-grid-item icon="add-o" text="添加亲人" to="/family/manage/members/add/0"/>
        <van-grid-item icon="delete-o" text="移除此人" @click="removeFamilyPeople"/>
        <van-grid-item icon="edit" text="编辑信息" to="/family/manage/members/edit/0"/>
        <van-grid-item icon="manager-o" text="ta 的主页" to="/family/members/people/0"/>
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
      rotate: 0,
      // [0]家族 [1]列表成员 [2]成员管理
      type: '0'
    }
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    dstRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = '/family'
      } else if (this.type === '1' || this.type === '2') {
        dst = '/family/members/people/' + this.type
      }
      return dst
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
    clickPeopleNode(node) {
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
        // 定位节点旋转动画
        this.$nextTick(() => {
          personElement.style.transform = `rotate(${this.rotate += 360}deg)`;
          this.rotate = this.rotate > 360 ? 0 : this.rotate;
        })
      })
      this.showSearchSheet = false
    },
    removeFamilyPeople() {
      this.$dialog.confirm({
        title: '删除提示',
        message: '您确定要删除《光头勇》这个家族成员吗？',
      }).then(() => {
        this.$toast.success('删除成功')
        this.memberManageSheet = false
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>

<style scoped>
.van-action-sheet__gap {
  height: 0;
}
</style>