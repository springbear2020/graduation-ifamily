<template>
  <div>
    <van-nav-bar title="我的" @click-right="userSettings">
      <template #right>
        <van-icon name="ellipsis" size="20"/>
      </template>
    </van-nav-bar>

    <van-card
        title="你不懂我&我不怪你"
        desc="长相思兮长相忆，短相思兮无穷极。"
        thumb="https://img01.yzcdn.cn/vant/cat.jpeg"
        @click="userPersonal"
    />

    <van-grid :column-num="3">
      <van-grid-item icon="contact" text="3"/>
      <van-grid-item icon="star-o" text="32"/>
      <van-grid-item icon="envelop-o" text="21"/>
    </van-grid>

    <van-cell-group>
      <van-cell title="个人资料" icon="user-o" is-link to="/user/personal"/>
      <van-cell title="我的家庭" icon="friends-o" is-link/>
      <van-cell title="我的相册" icon="photo-o" is-link/>
      <van-cell title="我的视频" icon="video-o" is-link/>
      <van-cell title="联系客服" icon="service-o" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    userSettings() {
      this.$router.push('/user/settings')
    },
    userPersonal() {
      this.$router.push('/user/personal')
    }
  }
}
</script>

<style scoped>
/deep/ .van-card__title {
  margin: 16px;
  line-height: 20px;
}

/deep/ .van-card__desc {
  margin: 0 16px;
}
</style>