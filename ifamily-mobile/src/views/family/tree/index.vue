<template>
  <div>
    <van-nav-bar fixed title="家族树谱" left-arrow @click-left="backFamily" @click-right="showPopover = true">
      <template #right>
        <!-- 家族树操作弹出层 -->
        <van-popover v-model="showPopover" trigger="click" :actions="actions" placement="bottom-end" @select="onSelect">
          <template #reference>
            <van-icon name="apps-o"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <FamilyTree :jsonData="jsonData" @click-node="clickNode"/>

    <!-- 家族成员搜索动作面板 -->
    <van-action-sheet v-model="showActionSheet" title="搜索成员">
      <van-search v-model="memberName" show-action placeholder="请输入家族成员姓名"/>
      <van-empty description="无内容"/>
    </van-action-sheet>

    <!-- 查看树节点弹出层 -->
    <van-popup v-model="showPopup">内容</van-popup>
  </div>
</template>

<script>
import FamilyTree from '@/views/family/tree/family-tree';
import stone from '@/assets/json/ADreamInRedMansions.json'

export default {
  name: "index",
  components: {FamilyTree},
  data() {
    return {
      jsonData: stone,
      // [true]折叠全部节点 [false]展开全部节点
      isAllFold: true,
      showPopover: false,
      showActionSheet: false,
      showPopup: false,
      actions: [
        {text: '展开全部', icon: 'expand-o'},
        {text: '折叠全部', icon: 'shrink'},
        {text: '定位到我', icon: 'aim'},
        {text: '搜索成员', icon: 'search'},
      ],
      memberName: ''
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
        extendKey(this.jsonData)
      }
    }
  },
  methods: {
    clickNode(node) {
      console.log(node)
      this.showPopup = true
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
          this.isAllFold = true
          this.showActionSheet = true
          break;
        default:
      }
    }
  }
}
</script>