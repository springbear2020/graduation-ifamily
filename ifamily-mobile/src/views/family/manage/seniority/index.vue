<template>
  <div>
    <van-nav-bar title="字辈管理" left-arrow @click-left="$router.replace('/family/manage')" @click-right="addSeniority">
      <template #right>
        <van-icon name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族世代字辈信息 -->
    <van-cell-group>
      <van-cell is-link v-for="(item, index) in seniorityList" :key="item.id" @click="editSeniority(index)"
                :title="'世代 ' + item.generation" :value="item.seniority"/>
    </van-cell-group>

    <!-- 字辈管理动作面板 -->
    <van-action-sheet v-model="showActionSheet" :title="sheetTitle">
      <van-field label="字辈" placeholder="字辈" v-model="seniority"/>
      <van-button type="primary" block @click="confirmSeniority">确定</van-button>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      sheetTitle: '',
      showActionSheet: false,
      seniority: '',
      seniorityList: [
        {id: 1, generation: 1, seniority: '春'},
        {id: 2, generation: 2, seniority: '春'},
        {id: 3, generation: 3, seniority: '春'},
        {id: 4, generation: 4, seniority: '春'},
        {id: 5, generation: 5, seniority: '春'}
      ],
      // [0]添加字辈 [1]编辑字辈
      type: '1',
      selectedIndex: -1
    }
  },
  methods: {
    addSeniority() {
      this.type = '0'
      this.seniority = ''
      this.sheetTitle = '世代 ' + (this.seniorityList.length + 1);
      this.showActionSheet = true
    },
    editSeniority(index) {
      this.selectedIndex = index
      this.type = '1'
      this.title = '世代 ' + this.seniorityList[index].generation;
      this.seniority = this.seniorityList[index].seniority;
      this.showActionSheet = true
    },
    confirmSeniority() {
      // [0]添加字辈 [1]编辑字辈
      if (this.type === '0') {
        let generation = this.seniorityList.length + 1
        this.seniorityList.push({id: generation, generation: generation, seniority: this.seniority})
      } else if (this.type === '1') {
        this.seniorityList[this.selectedIndex].seniority = this.seniority
      }
      this.showActionSheet = false
    }
  }
}
</script>