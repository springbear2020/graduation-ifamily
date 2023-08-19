<template>
  <div>
    <van-nav-bar title="成员管理" left-arrow @click-left="$router.replace('/family/admin')"/>

    <!-- 成员搜索框 -->
    <van-form>
      <van-search shape="round" show-action placeholder="请输入家族人员姓名"
                  v-model="memberName" @input="searchPeople"
                  @cancel="showPopup = false; memberName = ''; searchList = []"/>
    </van-form>

    <!-- 成员搜索结果列表 -->
    <van-cell is-link center v-for="people in searchList" :key="people.id"
              @click="clickedPeople = people; showSheet = true">
      <template #title>
        <van-image round width="52" height="52" :src="people.portrait || defaultPortrait(people.gender)"/>
        <p>
          <span>{{ people.name }}</span>
          <sex-tag :sex="people.gender"/>
        </p>
      </template>
      <template #default>
        <van-tag color="#6c757d" v-if="people.deathDate">已逝</van-tag>
        <van-tag color="#28a745" v-else>健在</van-tag>
      </template>
    </van-cell>

    <!-- 人员管理动作面板 -->
    <van-action-sheet v-model="showSheet" cancel-text="取消" :description="clickedPeople.name">
      <van-grid square :border="false">
        <van-grid-item icon="add-o" text="添加亲人"
                       @click="$router.push(`/family/admin/member/add?pid=${clickedPeople.id}`)"/>
        <van-grid-item icon="delete-o" text="移除此人" @click="removeGenealogyPeople"/>
        <van-grid-item icon="edit" text="编辑信息"
                       @click="$router.push(`/family/admin/member/edit?pid=${clickedPeople.id}`)"/>
        <van-grid-item icon="manager-o" :text="`${clickedPeople.gender === 0 ? '他' : '她'}的主页`"
                       @click="$router.push(`/family/member/info/2?pid=${clickedPeople.id}`)"/>
      </van-grid>
    </van-action-sheet>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import {defaultPortrait} from "@/mixin/common-utils";
import MemberForm from "@/components/business/member-form";

export default {
  name: "index",
  components: {SexTag, MemberForm},
  mixins: [defaultPortrait],
  data() {
    return {
      memberName: undefined,
      searchList: [],
      showSheet: false,
      clickedPeople: {},
    }
  },
  watch: {
    memberName(newVal) {
      if (newVal.length === 0) {
        this.searchList = []
      }
    }
  },
  methods: {
    searchPeople() {
      if (this.memberName.length === 0) {
        return
      }

      this.$api.member.listMemberByName({name: this.memberName}).then(list => {
        this.searchList = list
      }).catch((err) => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      });
    },
    removeGenealogyPeople() {
      this.showSheet = false
      const genealogyName = this.$store.getters["genealogy/defaultGenealogy"].title

      this.$dialog.confirm({
        title: '移除成员',
        message: `您确定要从《${genealogyName}》家族中移除<p style="color: #ee0a24">${this.clickedPeople.name}</p>这个家族成员吗？`,
      }).then(() => {
        this.$api.people.removePeople({peopleId: this.clickedPeople.id}).then(() => {
          this.$toast.success('移除成功')
          this.memberName = ''
          // 更新家族仓库中的信息
          this.$store.dispatch('genealogy/updateGenealogyStore')
        }).catch(err => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      }).catch(() => {
      })
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
