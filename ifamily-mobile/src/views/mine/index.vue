<template>
  <div>
    <van-nav-bar title="我的" @click-right="$router.push('/mine/settings')">
      <template #right>
        <van-icon name="setting-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 头像、昵称、签名 -->
    <van-card centered title="光头勇" desc="长相思兮长相忆，短相思兮无穷极。"
              thumb="https://img01.yzcdn.cn/vant/leaf.jpg" @click-thumb="$router.push('/family/members/people/3')">
      <template #footer>
        <van-button size="mini" type="primary" to="/user/login" plain>登录/注册</van-button>
      </template>
    </van-card>

    <van-cell-group>
      <van-cell title="我的资料" icon="user-circle-o" is-link to="/family/members/people/3"/>
      <van-cell title="我的动态" icon="guide-o" is-link to="/mine/moments"/>
      <van-cell title="我的亲人" icon="friends-o" is-link to="/mine/relatives"/>
      <van-cell title="家庭相册" icon="photo-o" is-link to="/mine/family/album"/>
      <van-cell title="联系客服" icon="service-o" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>