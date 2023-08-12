<template>
  <div>
    <van-nav-bar title="人员信息" left-arrow @click-left="backMembers" @click-right="locateFamilyPeople">
      <template #right>
        <van-icon name="aim"/>
      </template>
    </van-nav-bar>

    <van-grid :border="false" :column-num="1">
      <van-grid-item text="李世民">
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
        <van-tag type="warning">第 5 世</van-tag>
      </van-grid-item>
      <van-grid-item>
        <van-tag type="default">排行 5</van-tag>
      </van-grid-item>
      <van-grid-item>
        <van-icon name="male" color="#1989fa" class="iconfont" class-prefix="icon"/>
      </van-grid-item>
      <van-grid-item>
        <van-tag type="primary">健在</van-tag>
      </van-grid-item>
      <van-grid-item>
        <van-tag type="success">39 岁</van-tag>
      </van-grid-item>
    </van-grid>

    <van-cell-group>
      <van-cell :border="false" title="手机" label="13898564256" is-link/>
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
          <family-people-tag :name="'冯世元'" :sex="0"/>
        </p>
        <p>母亲：</p>
        <p>配偶： </p>
        <p>子女：
          <family-people-tag :name="'冯学慧'" :sex="1"/>
          <family-people-tag :name="'刘纯洲'" :sex="1"/>
          <family-people-tag :name="'冯学良'" :sex="0"/>
        </p>
        <p>同胞：</p>
      </template>
    </van-cell>

    <van-cell title="个人简介" label="入我相思门，知我相思苦。"/>

    <van-cell title="个人相册">
      <template #label>
        <van-image v-for="index in 5" :key="index" width="100" height="100" style="margin-left:5px"
                   src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
      </template>
    </van-cell>

    <van-cell title="个人视频">
      <template #label>
        <van-empty description="无内容"/>
      </template>
    </van-cell>
  </div>
</template>

<script>
export default {
  name: "index",
  methods: {
    backMembers() {
      this.$router.replace('/family/members')
    },
    locateFamilyPeople() {
      this.$router.push('/family/tree')
    }
  }
}
</script>