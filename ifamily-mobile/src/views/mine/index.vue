<template>
  <div>
    <van-nav-bar title="我的" @click-right="$router.push('/mine/settings')">
      <template #right>
        <van-icon name="setting-o" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 头像、昵称、签名 -->
    <van-card centered title="你不懂我&我不怪你" desc="长相思兮长相忆，短相思兮无穷极。"
              thumb="https://img01.yzcdn.cn/vant/leaf.jpg"/>

    <van-cell-group>
      <van-cell title="个人资料" icon="user-circle-o" is-link/>
      <van-cell title="我的家庭" icon="wap-home-o" is-link/>
      <van-cell title="我的动态" icon="guide-o" is-link/>
      <van-cell title="我的相册" icon="photo-o" is-link/>
      <van-cell title="联系客服" icon="service-o" is-link/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>