<template>
  <div>
    <van-nav-bar title="我的" @click-right="$router.push('/mine/settings')">
      <template #right>
        <van-icon name="setting-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-card centered :thumb="avatar" @click-thumb="previewImage(avatar)">
      <template #desc>
        <van-cell is-link center @click="$router.push('/mine/info')">
          <p class="van-ellipsis">UID：{{ userInfo.username }}</p>
          <p class="van-ellipsis">昵称：{{ userInfo.nickname }}</p>
          <p class="van-ellipsis">签名：{{ userInfo.signature }}</p>
        </van-cell>
      </template>
    </van-card>

    <van-grid :column-num="3" class="top">
      <van-grid-item icon="manager-o" text="联系人"/>
      <van-grid-item icon="calendar-o" text="纪念日"/>
      <van-grid-item icon="idcard" text="通讯录"/>
    </van-grid>

    <van-cell title="我的动态" icon="flag-o" is-link class="top" to="/mine/moment"/>
    <van-cell title="个人档案" icon="user-circle-o" is-link/>
    <van-cell title="家族列表" icon="cluster-o" is-link class="top"/>
    <van-cell title="亲人列表" icon="friends-o" is-link/>
    <van-cell title="联系客服" icon="service-o" is-link class="top"/>
  </div>
</template>

<script>
import {previewImage} from "@/mixin/common-utils";

export default {
  name: "index",
  mixins: [previewImage],
  computed: {
    userInfo() {
      return this.$store.state.user.user || {}
    },
    avatar() {
      return this.userInfo.avatar || 'img/avatar.jpg'
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}

/deep/ .van-card {
  background-color: white;
}

.van-card p {
  margin: 0;
  font-size: 12px;
}

.van-card__content .van-cell {
  padding: 0;
}
</style>
