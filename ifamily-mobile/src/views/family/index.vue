<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"
                  v-if="defaultGenealogy.id"/>
      </template>
    </van-nav-bar>

    <div v-if="defaultGenealogy.id">
      <!-- 家族概况卡片 -->
      <family-info-card @click.native="$router.push('/family/info')" :genealogy="defaultGenealogy"/>

      <!-- 家族操作宫格 -->
      <van-grid :column-num="3" :gutter="8">
        <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree/0"/>
        <van-grid-item icon="friends-o" text="家族成员" to="/family/member"/>
        <van-grid-item icon="setting-o" text="家族管理"/>
        <van-grid-item icon="volume-o" text="家族公告" to="/family/notice"/>
        <van-grid-item icon="photo-o" text="家族相册"/>
        <van-grid-item icon="video-o" text="家族视频"/>
        <van-grid-item icon="clock-o" text="家族历史"/>
        <van-grid-item icon="notes-o" text="家族大事"/>
        <van-grid-item icon="label-o" text="修谱记录" to="/family/revision"/>
      </van-grid>
    </div>

    <van-empty class="empty" description="空空如也~" v-if="emptyShow" :image="require('@/assets/img/empty-image-default.png')">
      <van-button icon="plus" type="info" to="/family/info/form/0">创建家族</van-button>
    </van-empty>

    <!-- 设置用户家族资料 -->
    <van-popup v-model="showPopup" position="top" :close-on-click-overlay="false"
               @opened="$toast({message: `检测到《${defaultGenealogy.title}》家族中不存在您的个人信息，请填写并保存您的家族个人信息`, position: 'bottom'})">
      <van-nav-bar title="您的资料"/>
      <member-form @save="saveUserPeople" @hidden-form="$router.replace('/')"/>
    </van-popup>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";
import MemberForm from "@/components/basis/member-form";

export default {
  name: "index",
  components: {FamilyInfoCard, MemberForm},
  data() {
    return {
      emptyShow: false,
      showPopup: false
    }
  },
  computed: {
    defaultGenealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"]
    }
  },
  mounted() {
    // 初始化家族列表数据
    this.initGenealogies();
  },
  methods: {
    initGenealogies() {
      // 未查询过家族列表信息则进行查询
      const list = this.$store.state.genealogy.genealogyList
      if (!list || list.length <= 0) {
        this.$store.dispatch('genealogy/listGenealogyList').then(() => {
          this.initUserPeople()
        }).catch(() => {
          this.emptyShow = true
        })
      } else {
        this.initUserPeople()
      }
    },
    initUserPeople() {
      // 若用户默认家族存在，则校验家族中是否存在 “我” 用户-家族成员数据，不存在则进行查询
      const userPeople = this.$store.state.genealogy.userPeople
      if (!userPeople.id) {
        this.$store.dispatch('genealogy/getPeopleOfUser').then(() => {
        }).catch(() => {
          // 家族中不存在当前用户的个人信息，提醒用户前往添加，否则无法进行后续家族操作
          this.showPopup = true
        })
      }
    },
    saveUserPeople(formData) {
      this.$api.people.saveUserPeople(formData).then(() => {
        // 查询最新的家族信息
        this.$store.dispatch('genealogy/logout')
        this.$toast.success('保存成功');
        this.showPopup = false
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
/deep/ .van-card {
  margin: 8px;
  background-color: #ffffff;
}

/deep/ .van-tag {
  margin-right: 2px;
}

.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
</style>
