<template>
  <div>
    <van-nav-bar title="家族树谱" left-arrow @click-left="$router.replace('/family')" @click-right="showPopover = true">
      <template #right>
        <!-- 右上角操作气泡弹出框 -->
        <van-popover placement="bottom-end" trigger="click"
                     v-if="tree.id" v-model="showPopover" :actions="actions" @select="onSelect">
          <template #reference>
            <van-icon name="apps-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 家族系谱图 -->
    <FamilyTree ref="familyTree" @click-node="(node) => $router.push(`/family/member/info/0?pid=${node.id}`)"
                :tree="tree" v-if="tree.id"/>

    <!-- 空状态 -->
    <van-empty class="empty" v-if="emptyShow" description="空空如也~"/>

    <!-- 成员搜索弹出层 -->
    <van-popup v-model="showPopup" position="top" :close-on-click-overlay="false">
      <van-form>
        <van-search shape="round" show-action placeholder="请输入家族人员姓名"
                    v-model="content" @input="searchPeople" @cancel="showPopup = false; content = ''"/>
      </van-form>

      <van-cell is-link center v-for="people in searchList" :key="people.id"
                @click="locateMemberNode(people.id); showPopup = false; content = ''">
        <template #title>
          <van-image round width="52" height="52" :src="people.portrait || defaultPortrait(people.gender)"/>
          <p><span>{{ people.name }}</span></p>
        </template>
      </van-cell>
    </van-popup>
  </div>
</template>

<script>
import FamilyTree from '@/components/tree/family-tree';
import {defaultPortrait} from "@/mixin/common-utils";

export default {
  name: "index",
  components: {FamilyTree},
  mixins: [defaultPortrait],
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
      // 树节点旋转角度
      rotate: 0,
      // 空状态
      emptyShow: false,
      // 成员搜索弹出层
      showPopup: false,
      searchList: [],
      content: undefined,
    }
  },
  mounted() {
    // 初始化家族树谱数据
    this.initTree()

    // 定位路由 query 参数 pid 不为空的人员
    const pid = this.$route.query.pid
    if (pid) {
      this.isAllFold = false
      this.locateMemberNode(pid)
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
      // 搜索成员姓名为空时清空结果列表
      if (newVal.length === 0) {
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
          this.tree = this.$store.state.genealogy.tree
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
          // 取出仓库中的 “我” 用户-家族成员数据
          this.locateMemberNode(this.$store.state.genealogy.userPeople.id)
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
    searchPeople() {
      if (this.content.length === 0) {
        return
      }

      this.$api.member.listMemberByName({name: this.content}).then(list => {
        this.searchList = list
      }).catch((err) => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      });
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell__title {
  display: flex;
}

.van-cell__title p {
  margin-left: 8px;
}
</style>
