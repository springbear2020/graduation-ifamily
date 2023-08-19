<template>
  <div>
    <van-nav-bar title="通讯录" left-arrow @click-left="$router.replace('/mine')"/>

    <!-- 联系人搜索框 -->
    <van-search show-action v-model="peopleName" placeholder="搜索" @search="$toast(peopleName)">
      <template #action>
        <div @click="$toast(peopleName)">搜索</div>
      </template>
    </van-search>

    <!-- 联系人列表 -->
    <van-index-bar :index-list="indexList" ref="indexBar">
      <div v-for="(item, index) in indexList" :key="index">
        <van-index-anchor :index="item"/>
        <!-- 联系人信息 -->
        <van-cell class="flex-cell-container" is-link center @click="showPeopleSheet = true">
          <template #title>
            <van-image width="50" height="50" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
            <!-- 标题、内容 -->
            <p class="contact-name">光头勇</p>
          </template>
        </van-cell>
      </div>
    </van-index-bar>

    <!-- 联系人操作面板 -->
    <van-action-sheet v-model="showPeopleSheet" cancel-text="取消" description="光头勇">
      <van-grid square :border="false" :column-num="3">
        <van-grid-item icon="chat-o" text="发起聊天" @click="$router.push('/home/message/chat')"/>
        <van-grid-item icon="delete-o" text="移除此人" @click="removeContact"/>
        <van-grid-item icon="manager-o" text="ta 的主页" @click="$router.push('/family/members/people/3')"/>
      </van-grid>
    </van-action-sheet>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      indexList: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#'],
      peopleName: '',
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '长相思兮长相忆，短相思兮无穷极。'
      },
      showPeopleSheet: false
    }
  },
  methods: {
    removeContact() {
      this.showPeopleSheet = false
      this.$dialog.confirm({
        title: '删除提示',
        message: '您确定要从您的通讯录中移除《光头勇》这个联系人吗？',
      }).then(() => {
        this.$toast.success('移除成功')
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>

<style scoped>
/deep/ .van-image__img {
  border-radius: 15%;
}

.contact-name {
  line-height: 24px;
  font-weight: bold;
  margin-left: 10px;
}
</style>