<template>
  <div>
    <van-nav-bar title="家族" @click-right="familyList">
      <template #right>
        <van-icon name="exchange" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 家族概况卡片 -->
    <family-info-card @click.native="familyInfo" :disabled="true"/>

    <van-grid square :gutter="8" :column-num="4">
      <van-grid-item icon="cluster-o" text="家族树谱" to="/family/tree"/>
      <van-grid-item icon="friends-o" text="家族成员" to="/family/members"/>
      <van-grid-item icon="photo-o" text="家族相册" to="/family/album"/>
      <van-grid-item icon="setting-o" text="家族管理"/>
    </van-grid>

    <van-tabs v-model="active">
      <van-tab title="成员动态">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-list isFinished-text="没有更多了" v-model="isLoading" :isFinished="isFinished" @load="onLoad"
                    :error.sync="error" error-text="请求失败，点击重新加载">
            <social-moments :data-list="dataList" v-on:post-comment="handlePostComment"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>

      <van-tab title="家族公告">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-cell-group v-for="item in 10" :key="item">
            <portrait-desc :person="person"/>
            <van-cell
                value="族谱作为一个记载以血缘关系为主体的家族世代繁衍和重要人物事迹的特殊图书体裁，
                对考古学、社会学、地方史等均具有及其重要的价值，是中华五千年文明史中最具有平民特色的文献。"/>
          </van-cell-group>
        </van-pull-refresh>
      </van-tab>

      <van-tab title="修谱日志">
        <van-pull-refresh v-model="isRefreshing" success-text="刷新成功" @refresh="onRefresh">
          <van-steps direction="vertical" center>
            <van-step v-for="i in 10" :key="i">
              <h3>2016-07-12 12:40</h3>
              <p>光头勇添加了一个家族成员，修改了光头强的个人信息</p>
            </van-step>
          </van-steps>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import FamilyInfoCard from "@/views/family/list/family-info-card";
import momentsList from '@/assets/json/moments.json'

export default {
  name: "index",
  components: {FamilyInfoCard},
  data() {
    return {
      active: 0,
      isRefreshing: false,
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: '2023-02-25 12:05:53'
      },
      dataList: [],
      isLoading: false,
      isFinished: false,
      error: false
    };
  },
  mounted() {
    this.dataList = momentsList && momentsList.length > 0 ? momentsList : []
  },
  methods: {
    familyList() {
      this.$router.push('/family/list')
    },
    familyInfo() {
      this.$router.push('/family/info')
    },
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
    onLoad() {
      setTimeout(() => {
        this.isLoading = false;
        this.error = true;
      }, 1000);
    },
    handlePostComment(content, moment) {
      this.$toast.success('评论成功')
      console.log(content, moment)
    }
  },
}
</script>

<style scoped>
.van-step__title h3 {
  font-size: 15px;
}
</style>