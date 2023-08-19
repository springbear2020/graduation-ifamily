<template>
  <div>
    <van-nav-bar title="我的" @click-right="$router.push('/mine/settings')">
      <template #right>
        <van-icon name="setting-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-card centered :thumb="user.avatar ? user.avatar : 'img/avatar.jpg'">
      <template #desc>
        <van-cell is-link center @click="$router.push('/mine/info')">
          <p class="van-ellipsis">UID：{{ user.username }}</p>
          <p class="van-ellipsis">昵称：{{ user.nickname }}</p>
          <p class="van-ellipsis">签名：{{ user.signature }}</p>
        </van-cell>
      </template>
    </van-card>

    <van-grid :column-num="3">
      <van-grid-item icon="contact" text="通讯录"/>
      <van-grid-item icon="calendar-o" text="纪念日"/>
      <van-grid-item icon="star-o" text="收藏夹"/>
    </van-grid>

    <van-cell title="我的资料" icon="user-circle-o" is-link/>
    <van-cell title="我的动态" icon="flag-o" is-link/>
    <van-cell title="我的相册" icon="photo-o" is-link/>
    <van-cell title="家族列表" icon="cluster-o" is-link class="top"/>
    <van-cell title="亲人列表" icon="friends-o" is-link/>
    <van-cell title="联系客服" icon="service-o" is-link class="top"/>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: "index",
  computed: {
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    })
  }
}
</script>

<style scoped>
.van-card {
  margin-bottom: 8px;
}

.van-card__content p {
  margin: 0;
}

.van-card p {
  color: #323233;
  font-size: 12px;
  line-height: 2;
}

/deep/ .van-cell {
  padding: 20px 16px;
}

.van-card__content .van-cell {
  padding: 0;
  background-color: inherit;
}

/deep/ .van-card {
  background-color: #ffffff;
}

.van-grid {
  margin-bottom: 8px;
}

.top {
  margin-top: 8px;
}
</style>
