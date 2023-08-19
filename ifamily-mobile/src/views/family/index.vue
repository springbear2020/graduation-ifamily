<template>
  <div>
    <van-nav-bar title="家族" @click-right="$router.push('/family/list')">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族概况卡片 -->
    <family-info-card @click.native="$router.push('/family/info')" :disabled="true"/>

    <!-- 家族操作宫格 -->
    <van-grid square :gutter="8" :column-num="4">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="setting-o" text="家族管理" to="/family/manage"/>
    </van-grid>

    <van-tabs v-model="active">
      <!-- 修谱日志 -->
      <van-tab title="修谱日志">
        <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
          <van-steps direction="vertical" center active-color="#ff976a">
            <van-step v-for="i in 10">
              <h3>2016-07-12 12:40</h3>
              <p>光头勇添加了光头强</p>
              <p>光头勇删除了光头强</p>
              <p>光头勇修改了光头强</p>
            </van-step>
          </van-steps>
        </van-pull-refresh>
      </van-tab>
      <!-- 家族公告 -->
      <van-tab title="家族公告">
        <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
          <van-cell-group v-for="i in 10">
            <portrait-desc :person="person"/>
            <van-cell class="cell-top"
                      value="族谱作为一个记载以血缘关系为主体的家族世代繁衍和重要人物事迹的特殊图书体裁。"
            />
          </van-cell-group>
        </van-pull-refresh>
      </van-tab>
      <!-- 访问记录 -->
      <van-tab title="访问记录">
        <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
          <van-steps direction="vertical" center active-color="#1989fa">
            <van-step v-for="i in 10">
              <h3>2016-07-12 12:40</h3>
              <p>光头勇访问了家族</p>
            </van-step>
          </van-steps>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import FamilyInfoCard from "@/views/family/list/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
  data() {
    return {
      active: 1,
      isRefreshing: false,
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '2023-02-25 12:05'
      },
      isLoading: false,
      isFinished: false,
      error: false
    };
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    }
  }
}
</script>

<style scoped>
.cell-top {
  padding-top: 0;
}
</style>