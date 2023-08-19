<template>
  <div>
    <van-nav-bar title="家族树谱" left-arrow @click-left="$router.replace(dstRoute)" @click-right="showPopover = true">
      <template #right>
        <!-- 右上角操作气泡弹出框 -->
        <van-popover v-if="tree.id" placement="bottom-end" trigger="click" v-model="showPopover" :actions="actions"
                     @select="onSelect">
          <template #reference>
            <van-icon name="apps-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 家族系谱图 -->
    <FamilyTree v-if="tree.id" :tree="tree" @click-node="clickPeopleNode" ref="familyTree"/>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~">
      <van-button icon="plus" type="info" to="/family/manage/member/init">添加成员</van-button>
    </van-empty>
  </div>
</template>

<script>
import FamilyTree from '@/components/tree/family-tree';
import MemberForm from "@/components/basis/member-form";

export default {
  name: "index",
  components: {MemberForm, FamilyTree},
  data() {
    return {
      tree: {},
      showPopover: false,
      actions: [
        {text: '展开全部', icon: 'expand-o', disabled: false},
        {text: '折叠全部', icon: 'shrink', disabled: true},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      // [true]全部节点折叠 [false]!全部节点折叠
      isAllFold: true,
      // [0]家族
      type: '0',
      // 树节点旋转角度
      rotate: 0,
      emptyShow: false
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0')) {
      type = '0'
    }
    this.type = type

    this.initTree()
  },
  computed: {
    dstRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = '/family'
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
    initTree() {
      // 先从仓库获取家族树结构数据，不存在则进行查询
      let tree = this.$store.state.genealogy.tree
      if (!tree.id) {
        this.$store.dispatch('genealogy/getGenealogyTree').then(() => {
          tree = this.$store.state.genealogy.tree
          this.tree = tree
        }).catch(err => {
          this.emptyShow = true
          this.$toast.fail(err.data || err.desc)
        })
      }
      this.tree = tree
    },
    clickPeopleNode(node) {
      this.$toast(node.name)
    },
    onSelect(action, index) {
      switch (index) {
        case 0:
          // [0]展开全部
          this.isAllFold = false;
          this.locateMemberNode(this.tree.id)
          break;
        case 1:
          // [1]折叠全部
          this.isAllFold = true;
          this.locateMemberNode(this.tree.id)
          break;
        case 2:
          // [2]定位到我
          this.locateMe()
          break;
        case 3:
          // [3]搜索成员
          this.isAllFold = false
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
    },
    locateMe() {
      this.isAllFold = false
      this.locateMemberNode(21)
    }
  }
}
</script>

<style scoped>
.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
</style>
