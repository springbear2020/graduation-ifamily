<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow @click-left="backMembers" @click-right="locateFamilyPeople">
      <template #right>
        <van-icon name="aim"/>
      </template>
    </van-nav-bar>

    <!-- 人员信息头像和姓名 -->
    <van-grid :border="false" :column-num="1">
      <van-grid-item>
        <template #icon>
          <van-image height="100" width="100" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
        </template>
        <template #text>
          <span>冯勇贤</span>
        </template>
      </van-grid-item>
    </van-grid>

    <van-grid direction="horizontal" :border="false" :column-num="5">
      <van-grid-item>
        <desc-tag text="世代" number="5" type="primary"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="排行" number="2" type="success"/>
      </van-grid-item>
      <van-grid-item>
        <van-icon name="male" color="#1989fa" class="iconfont" class-prefix="icon"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="年龄" number="35" type="warning"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="已故" type="default"/>
      </van-grid-item>
    </van-grid>

    <van-cell-group>
      <van-cell :border="false" center title="手机" label="13898564256" is-link @click="favoritePeople">
        <template #right-icon>
          <van-icon name="star-o"/>
          <van-icon name="star"/>
        </template>
      </van-cell>
      <van-cell :border="false" title="现居地" label="湖北省/武汉市/洪山区"/>
    </van-cell-group>

    <van-cell-group>
      <van-cell :border="false" title="生于" label="农历 一九三五年冬月十三"/>
      <van-cell :border="false" title="出生地" label="广东省/深圳市/南山区"/>
    </van-cell-group>

    <van-cell-group>
      <van-cell :border="false" title="逝于" label="公历 2007-09-12"/>
      <van-cell :border="false" title="埋葬地" label="广东省/深圳市/南山区"/>
    </van-cell-group>

    <van-cell title="家庭关系">
      <template #label>
        <p>父亲：
          <people-tag :name="'冯世元'" :sex="0" @click.native="viewPeople"/>
        </p>
        <p>母亲：</p>
        <p>配偶：</p>
        <p>子女：
          <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="viewPeople"/>
          <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="viewPeople"/>
          <people-tag :name="'冯学良'" :sex="0" :right="true" :bottom="true" @click.native="viewPeople"/>
        </p>
        <p>同胞：</p>
      </template>
    </van-cell>

    <van-cell title="个人简介" label="入我相思门，知我相思苦。"/>

    <van-cell title="个人相册">
      <template #label>
        <van-image width="100" height="100" v-for="(img, index) in imgList" :src="img.url" :key="index"
                   @click="viewImage(index)"/>
      </template>
    </van-cell>

    <van-cell title="个人视频">
      <template #label>
        <van-empty description="无内容"/>
      </template>
    </van-cell>

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
  name: "members-people",
  data() {
    return {
      imgList: [
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
        {url: 'https://img01.yzcdn.cn/vant/cat.jpeg'},
      ],
    }
  },
  methods: {
    backMembers() {
      this.$router.replace('/family/members')
    },
    locateFamilyPeople() {
      this.$router.push('/family/tree')
    },
    favoritePeople() {
      this.$toast.success('收藏成功')
    },
    // TODO images preview
    viewImage(index) {
      let images = []
      this.imgList.forEach(item => {
        images.push(item.url)
      })
      ImagePreview({images: images, startPosition: index, closeable: true, showIndicators: true})
    },
    viewPeople() {
      this.$toast.success('查看人员信息')
    }
  }
}
</script>

<style scoped>
.van-image {
  /*TODO bottom padding?*/
  margin-right: 8px;
}
</style>