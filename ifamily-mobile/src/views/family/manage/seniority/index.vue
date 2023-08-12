<template>
  <div>
    <van-nav-bar title="字辈管理" left-arrow @click-left="backFamilyManagement" @click-right="addSeniority">
      <template #right>
        <van-icon name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-cell-group>
      <van-cell is-link v-for="(item, index) in seniorityList" :key="item.id" @click="editSeniority(index)"
                :title="'世代 ' + item.generation" :value="item.seniority"/>
    </van-cell-group>

    <!-- 世代、字辈 -->
    <van-action-sheet v-model="actionSheetShow" :title="title">
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
      actionSheetShow: false,
      title: '',
      seniority: '',
      seniorityList: [
        {id: 1, generation: 1, seniority: '春'},
        {id: 2, generation: 2, seniority: '春'},
        {id: 3, generation: 3, seniority: '春'},
        {id: 4, generation: 4, seniority: '春'},
        {id: 5, generation: 5, seniority: '春'}
      ],
      // [1]添加字辈 [2]编辑字辈
      type: '1'
    }
  },
  methods: {
    backFamilyManagement() {
      this.$router.replace('/family/manage')
    },
    addSeniority() {
      this.title = '世代 ' + (this.seniorityList.length + 1);
      this.type = 1
      this.actionSheetShow = true
    },
    confirmSeniority() {
      if (this.type === '2') {
        let generation = this.seniorityList.length + 1
        this.seniorityList.push({id: generation, generation: generation, seniority: this.seniority})
      }
      this.actionSheetShow = false
    },
    editSeniority(index) {
      this.title = '世代 ' + this.seniorityList[index].generation;
      this.seniority = this.seniorityList[index].seniority;
      this.type = '2'
      this.actionSheetShow = true
    }
  }
}
</script>