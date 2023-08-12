<template>
  <div>
    <van-nav-bar title="我的" @click-right="settings">
      <template #right>
        <van-icon name="ellipsis" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 用户头像、昵称、个性签名卡片 -->
    <van-card centered title="你不懂我&我不怪你" desc="长相思兮长相忆，短相思兮无穷极。" @click-thumb="userPersonal"
              thumb="https://whut.springbear2020.cn/static/img/WHUT.png">
      <!-- TODO remove the button -->
      <template #footer>
        <van-button to="/mine/login" type="primary" plain size="mini">登录/注册</van-button>
      </template>
    </van-card>

    <!-- TODO 宫格展示用户个人资料：如通讯录等 -->
    <van-grid :column-num="3">
      <van-grid-item icon="contact" text="通讯录"/>
      <van-grid-item icon="contact" text="通讯录"/>
      <van-grid-item icon="contact" text="通讯录"/>
    </van-grid>

    <van-cell-group>
      <van-cell title="个人资料" icon="user-o" is-link to="/mine/personal"/>
      <van-cell title="我的家庭" icon="cluster-o" is-link to="/mine/personal"/>
      <van-cell title="我的动态" icon="friends-o" is-link/>
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
    settings() {
      this.$router.push('/mine/settings')
    },
    userPersonal() {
      this.$router.push('/mine/personal')
    }
  }
}
</script>