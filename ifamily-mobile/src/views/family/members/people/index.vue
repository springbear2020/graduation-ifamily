<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow
                 @click-left="$router.replace(backRoute)"
                 @click-right="$router.push(toRoute)"
    >
      <template #right>
        <!-- 人员家族树定位 -->
        <van-icon v-if="type === '1' || type === '2'" name="aim" size="20"/>
        <!-- 编辑人员信息 -->
        <van-icon v-else-if="type === '3'" name="edit" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 头像、姓名、签名 -->
    <van-grid :border="false" :column-num="1">
      <van-grid-item>
        <template #icon>
          <van-image round height="100" width="100" :src="url" @click="previewImage"/>
        </template>
        <template #text>
          <p class="cover-name">冯勇贤
            <sex-tag/>
          </p>
          <p class="cover-signature">长相思兮长相忆，短相思兮无穷极。</p>
        </template>
      </van-grid-item>
    </van-grid>

    <!-- 信息概况 -->
    <van-grid direction="horizontal" :border="false" :column-num="5">
      <van-grid-item>
        <desc-tag title="世代" content="5" color="#ffc107"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="排行" content="2" color="#17a2b8"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="健在" content="" color="#28a745"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="年龄" content="35" color="#20c997"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="字辈" content="勇" color="#dc3545"/>
      </van-grid-item>
    </van-grid>

    <!-- 手机、常住地 -->
    <van-cell-group>
      <van-cell :border="false" title="手机" label="13898564256"/>
      <van-cell :border="false" title="常住地" label="湖北省/武汉市/洪山区/洪山街道武汉理工大学南湖校区智园"/>
    </van-cell-group>
    <!-- 生于、出生地 -->
    <van-cell-group>
      <van-cell :border="false" title="生于" label="农历 一九三五年冬月十三"/>
      <van-cell :border="false" title="出生地" label="广东省/深圳市/南山区"/>
    </van-cell-group>
    <!-- 逝于、埋葬地 -->
    <van-cell-group>
      <van-cell :border="false" title="逝于" label="公历 2007-09-12"/>
      <van-cell :border="false" title="埋葬地" label="广东省/深圳市/南山区"/>
    </van-cell-group>

    <!-- 家庭关系 -->
    <van-cell-group>
      <family-relationship :add-button="false" class="family-relationships"/>
      <van-cell title="备注" :border="false" label="备注与其父母亲的特殊关系，如养子、养女、继子、继女等" class="family-member-remarks"/>
    </van-cell-group>

    <!-- 个人动态 -->
    <van-cell title="个人动态">
      <template #label>
        <van-empty description="无内容"/>
      </template>
    </van-cell>
  </div>
</template>

<script>
import {ImagePreview} from "vant";

export default {
  name: "index",
  data() {
    return {
      url: 'https://img01.yzcdn.cn/vant/cat.jpeg',
      // [0]家族树 [1]成员列表 [2]家族人员管理 [3]我的
      type: '0',
    }
  },
  mounted() {
    this.type = this.$route.params.type
  },
  computed: {
    backRoute() {
      let dst = '/'
      if (this.type === '0') {
        dst = '/family/tree/' + this.type
      } else if (this.type === '1') {
        dst = '/family/members'
      } else if (this.type === '2') {
        dst = '/family/manage/members'
      } else if (this.type === '3') {
        dst = '/mine'
      }
      return dst;
    },
    toRoute() {
      return this.type === '3' ? '/family/manage/members/edit/2' : `/family/tree/${this.type}`
    }
  },
  methods: {
    previewImage() {
      let images = []
      images.push(this.url)
      ImagePreview({images})
    }
  }
}
</script>

<style scoped>
.family-relationships {
  padding-bottom: 0;
}

.family-member-remarks {
  padding-top: 0;
}
</style>