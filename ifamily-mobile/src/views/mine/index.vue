<template>
  <div>
    <van-nav-bar title="我的" @click-right="$router.push('/mine/about')">
      <template #right>
        <van-icon name="info-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 头像、昵称、签名 -->
    <van-card centered
              thumb="https://img01.yzcdn.cn/vant/leaf.jpg" @click-thumb="$router.push('/family/members/people/3')">
      <template #desc>
        <p class="mine-portrait-content van-ellipsis">UID：0015468295</p>
        <p class="mine-portrait-content van-ellipsis">昵称：你不懂我&我不怪你</p>
        <p class="mine-portrait-content van-ellipsis">签名：长相思兮长相忆，短相思兮无穷极。</p>
      </template>
      <template #footer>
        <van-button size="mini" type="primary" to="/user/login" plain>登录 / 注册</van-button>
      </template>
    </van-card>

    <van-grid :column-num="2">
      <van-grid-item icon="contact" text="联系人"/>
      <van-grid-item icon="calendar-o" text="纪念日" @click="$router.push('/mine/memorial')"/>
    </van-grid>

    <van-cell title="我的资料" icon="user-circle-o" is-link to="/family/members/people/3"/>
    <van-cell title="我的动态" icon="flag-o" is-link to="/mine/moments"/>
    <van-cell title="我的亲人" icon="friends-o" is-link to="/mine/family"/>
    <van-cell title="家庭相册" icon="photo-o" is-link to="/mine/family/album"/>
    <van-cell title="账号安全" icon="shield-o" is-link to="/mine/security"/>
    <van-cell title="联系客服" icon="service-o" is-link/>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>

<style scoped>
.van-card__content p {
  margin: 2px 0;
}

.mine-portrait-content {
  color: #323233;
  font-size: 12px;
  line-height: 18px;
}
</style>