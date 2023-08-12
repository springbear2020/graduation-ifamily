<template>
  <div>
    <van-nav-bar title="家族" @click-right="myFamily">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-card tag="李" title="相亲相爱" desc="湖北省/武汉市/江岸区" @click="familyInfo" centered
              thumb="https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg">
      <template #tags>
        <van-tag plain type="primary">总 524</van-tag>
        <van-tag plain type="success">男 345</van-tag>
        <van-tag plain type="success">女 234</van-tag>
        <van-tag plain type="warning">生 453</van-tag>
        <van-tag plain type="warning">逝 325</van-tag>
      </template>
    </van-card>

    <van-grid square :gutter="8" :column-num="4">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="setting-o" text="家族管理" to="/family/manage"/>
    </van-grid>

    <van-tabs v-model="active">
      <van-tab title="家族动态">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="家族公告">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="家族日志">
        <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
          <van-empty description="无内容"/>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      active: 0,
      isLoading: false,
    };
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    },
    myFamily() {
      this.$router.push('/family/list')
    },
    familyInfo() {
      this.$router.push('/family/info')
    }
  },
}
</script>