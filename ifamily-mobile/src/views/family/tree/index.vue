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
    <FamilyTree v-if="tree.id" :tree="tree" @click-node="(node) => {this.clickedNode = node; this.showSheet = true}"
                ref="familyTree"/>

    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>

    <!-- 成员搜索弹出层 -->
    <van-popup v-model="showPopup" position="top" :close-on-click-overlay="false">
      <form>
        <van-search shape="round" show-action placeholder="请输入家族人员姓名" @cancel="showPopup = false; content = ''"
                    v-model="content" @input="searchPeople"/>
      </form>
      <contact-card v-for="people in searchList" :key="people.id" :people="people"
                    @click.native.stop="locateMemberNode(people.id); showPopup = false; content = ''"/>
    </van-popup>

    <!-- 树节点点击操作面板 -->
    <van-action-sheet v-model="showSheet" cancel-text="取消" :description="clickedNode.name">
      <van-grid square :border="false">
        <van-grid-item icon="add-o" text="添加亲人" @click="$router.push(`/family/member/add/0?pid=${clickedNode.id}`)"/>
        <van-grid-item icon="delete-o" text="移除此人" @click="removeGenealogyPeople"/>
        <van-grid-item icon="edit" text="编辑信息"
                       @click="$router.push(`/family/member/people/edit/0?pid=${clickedNode.id}`)"/>
        <van-grid-item icon="manager-o" :text="`${clickedNode.gender === 0 ? '他' : '她'}的主页`"
                       @click="$router.push(`/family/member/people/0?pid=${clickedNode.id}`)"/>
      </van-grid>
    </van-action-sheet>
  </div>
</template>

<script>
import FamilyTree from '@/components/tree/family-tree';
import MemberForm from "@/components/basis/member-form";
import ContactCard from "@/components/business/contact-card";
import {listMemberByName} from "@/api/genealogy/member";

export default {
  name: "index",
  components: {ContactCard, MemberForm, FamilyTree},
  data() {
    return {
      tree: {},
      // 右上角气泡弹出框
      showPopover: false,
      actions: [
        {text: '展开全部', icon: 'expand-o', disabled: false},
        {text: '折叠全部', icon: 'shrink', disabled: true},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      // [true]全部节点折叠 [false]!全部节点折叠
      isAllFold: true,
      // [0]家族首页
      type: '0',
      // 树节点旋转角度
      rotate: 0,
      // 空状态
      emptyShow: false,
      // 成员搜索弹出层
      showPopup: false,
      searchList: [],
      content: undefined,
      // 节点操作动作面板
      showSheet: false,
      clickedNode: {},
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0')) {
      type = '0'
    }
    this.type = type

    // 初始化家族树谱数据
    this.initTree()

    // 定位路由 query 参数 pid 不为空的人员
    const pid = this.$route.query.pid
    if (pid) {
      this.isAllFold = false
      this.locateMemberNode(pid)
    }
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
    },
    content(newVal) {
      if (newVal.length === 0) {
        // 搜索成员姓名为空时清空结果列表
        this.searchList = []
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
          this.locateMemberNode(tree.id)
        }).catch((err) => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
          this.emptyShow = true
        })
      } else {
        this.tree = tree
      }
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
          this.isAllFold = false
          this.locateMe()
          break;
        case 3:
          // [3]搜索成员
          this.showPopup = true
          this.isAllFold = false
          break;
        default:
      }
    },
    locateMemberNode(pid) {
      if (pid) {
        this.$nextTick(() => {
          const personElement = document.getElementsByClassName(`pid-${pid}`)[0]
          if (personElement) {
            // 调用子组件的树节点居中定位方法
            this.$refs.familyTree.centerNode(personElement)
            // 定位节点旋转动画
            this.$nextTick(() => {
              personElement.style.transform = `rotate(${this.rotate += 360}deg)`;
              this.rotate = this.rotate > 360 ? 0 : this.rotate;
            })
          }
        })
      }
    },
    locateMe() {
      // 取出仓库中的 “我” 用户-家族成员数据，不存在则进行查询
      let people = this.$store.state.genealogy.userPeople
      if (!people.id) {
        this.$store.dispatch('genealogy/getPeopleOfUser').then(() => {
          people = this.$store.state.genealogy.userPeople
          this.locateMemberNode(people.id)
        }).catch(err => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      } else {
        this.locateMemberNode(people.id)
      }
    },
    searchPeople() {
      if (this.content.length === 0) {
        return
      }
      this.$api.member.listMemberByName({name: this.content}).then(list => {
        this.searchList = list
      }).catch(() => {
      });
    },
    removeGenealogyPeople() {
      this.showSheet = false
      const genealogyName = this.$store.getters["genealogy/defaultGenealogy"].title

      this.$dialog.confirm({
        title: '移除成员',
        message: `您确定要从《${genealogyName}》家族中移除<p style="color: #ee0a24">${this.clickedNode.name}</p>这个家族成员吗？`,
      }).then(() => {
        this.$api.people.removePeople({peopleId: this.clickedNode.id}).then(() => {
          this.$toast.success('移除成功')
          // 重新查询家族树谱信息
          this.$store.commit('genealogy/REMOVE_TREE');
          this.initTree()
        }).catch(err => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      }).catch(() => {
      });
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
