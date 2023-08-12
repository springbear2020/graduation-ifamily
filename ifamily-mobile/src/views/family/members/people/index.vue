<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow @click-left="backMembers" @click-right="handleLocatePeople">
      <template #right>
        <van-icon name="aim"/>
      </template>
    </van-nav-bar>

    <!-- 头像、姓名、签名 -->
    <van-grid :border="false" :column-num="1">
      <van-grid-item>
        <template #icon>
          <van-image round height="100" width="100" :src="url" @click="viewImage"/>
        </template>
        <template #text>
          <p class="name">冯勇贤 <van-icon name="male" color="#007bff" class="iconfont" class-prefix="icon"/></p>
          <p class="signature">长相思兮长相忆，短相思兮无穷极。</p>
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
        <desc-tag title="健在" color="#28a745"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="年龄" content="35" color="#6610f2"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="字辈" content="勇" color="#dc3545"/>
      </van-grid-item>
    </van-grid>

    <!-- 手机、现居地 -->
    <van-cell-group>
      <van-cell :border="false" center title="手机" label="13898564256" is-link @click="handleStarPeople">
        <template #right-icon>
          <van-icon name="star-o"/>
        </template>
      </van-cell>
      <van-cell :border="false" title="现居地" label="湖北省/武汉市/洪山区/洪山街道武汉理工大学南湖校区智园"/>
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
    <van-cell title="家庭关系">
      <template #label>
        <p>父亲：
          <people-tag :name="'冯世元'" :sex="0" @click.native="handleViewPeople"/>
        </p>
        <p>母亲：</p>
        <p>配偶：</p>
        <p>子女：
          <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="handleViewPeople"/>
          <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="handleViewPeople"/>
          <people-tag :name="'冯学良'" :sex="0" :right="true" :bottom="true" @click.native="handleViewPeople"/>
        </p>
        <p>同胞：</p>
      </template>
    </van-cell>

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
    return  {
      url: 'https://img01.yzcdn.cn/vant/cat.jpeg',
    }
  },
  methods: {
    backMembers() {
      this.$router.replace('/family/members')
    },
    handleLocatePeople() {
      this.$toast.fail('家族树定位')
    },
    handleStarPeople() {
      this.$toast.success('收藏联系人')
    },
    handleViewPeople() {
      this.$toast.success('查看人员信息')
    },
    viewImage() {
      let images = []
      images.push(this.url)
      ImagePreview({images})
    },
  }
}
</script>

<style scoped>
.van-grid-item__content p {
  margin: 0;
}

.name {
  color: #323233;
  font-size: 14px;
  line-height: 24px;
  font-weight: bold;
}

.signature {
  color: #969799;
  font-size: 12px;
  line-height: 18px;
}
</style>