<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"
                  v-if="defaultGenealogy.id"/>
      </template>
    </van-nav-bar>

    <div v-if="defaultGenealogy.id">
      <family-info-card :genealogy="defaultGenealogy" @click.native="$router.push('/family/info')"/>

      <van-grid :column-num="3" :gutter="8">
        <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
        <van-grid-item icon="friends-o" text="家族成员" to="/family/member"/>
        <van-grid-item icon="setting-o" text="家族管理" to="/family/admin"/>
        <van-grid-item icon="volume-o" text="家族公告" to="/family/notice"/>
        <van-grid-item icon="photo-o" text="家族图片" to="/family/album"/>
        <van-grid-item icon="bookmark-o" text="家族大事" to="/family/memorabilia"/>
        <van-grid-item icon="notes-o" text="人物事迹"/>
        <van-grid-item icon="fire-o" text="祖坟祠堂"/>
        <van-grid-item icon="clock-o" text="修谱记录" to="/family/revision"/>
        <!-- 家规祖训 -->
      </van-grid>

      <van-empty description="更多功能，敬请期待" :image="require('@/assets/img/family.png')"/>
    </div>

    <van-empty class="empty" description="空空如也~" v-if="emptyShow" :image="require('@/assets/img/empty.png')">
      <van-button icon="plus" type="info" to="/family/admin/info/form/0">创建家族</van-button>
    </van-empty>

    <!-- 初始化用户家族资料弹出层 -->
    <van-popup v-model="showPopup" position="left" class="full-popup"
               @opened="$toast({message: `检测到《${defaultGenealogy.title}》家族中不存在您的信息，请填写并保存您的家族个人信息`, position: 'bottom'})">
      <van-nav-bar title="您的资料" @click-left="$router.replace('/')">
        <template #left>
          <van-icon name="cross" size="20"/>
        </template>
      </van-nav-bar>
      <member-form @save="saveUserPeople" @hidden-form="$router.replace('/')"/>
    </van-popup>
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/basis/family-info-card";
import MemberForm from "@/components/business/member-form";

export default {
  name: "index",
  components: {FamilyInfoCard, MemberForm},
  data() {
    return {
      emptyShow: false,
      showPopup: false,
    }
  },
  computed: {
    defaultGenealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"] || {}
    }
  },
  mounted() {
    this.initGenealogies()
  },
  methods: {
    initGenealogies() {
      const list = this.$store.state.genealogy.genealogyList
      if (!list || list.length === 0) {
        this.$store.dispatch('genealogy/genealogies').then(() => {
          this.initUserPeople()
        }).catch(() => {
          this.emptyShow = true
        })
      } else {
        this.initUserPeople()
      }
    },
    initUserPeople() {
      const userPeople = this.$store.state.genealogy.userPeople
      // 若用户默认家族存在，则校验家族中是否存在 “我” 用户家族成员数据，不存在则进行查询
      if (!userPeople.id) {
        this.$store.dispatch('genealogy/genealogyUserPeople').then(() => {
        }).catch(() => {
          // 默认家族中不存在当前用户的个人信息，提醒用户前往添加，否则无法进行后续家族操作
          this.showPopup = true
        })
      }
    },
    saveUserPeople(formData) {
      this.$api.genealogy.saveCurrentUserPeople(formData).then(() => {
        this.$store.commit('genealogy/CLEAR_STATE')
        this.showPopup = false
        this.initGenealogies()
        this.$toast.success('保存成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
/deep/ .van-card {
  margin: 8px;
  background-color: white;
}
</style>
