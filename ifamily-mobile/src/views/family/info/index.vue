<template>
  <div>
    <van-nav-bar title="家族信息" left-arrow @click-left="backFamily"/>

    <!-- 家族照片 -->
    <div class="img-portrait-father">
      <van-image class="img-portrait" width="100" height="100" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
    </div>

    <!-- 家族成员组成概况 -->
    <van-grid direction="horizontal" :border="false" :column-num="5">
      <van-grid-item>
        <desc-tag text="男" number="234" type="primary" size="medium" @click.native="viewConditionalMembers"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="女" number="423" type="danger" size="medium" @click.native="viewConditionalMembers"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="总" number="657" type="warning" size="medium" @click.native="viewConditionalMembers"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="生" number="600" type="success" size="medium" @click.native="viewConditionalMembers"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag text="逝" number="57" type="default" size="medium" @click.native="viewConditionalMembers"/>
      </van-grid-item>
    </van-grid>

    <van-cell-group>
      <van-cell title="家族名称" value="相亲相爱"/>
      <van-cell title="家族姓氏" value="李"/>
      <van-cell title="家族地址" value="湖北省/武汉市/洪山区"/>
      <van-collapse v-model="activeNames">
        <van-collapse-item title="家族简介" name="1">
          众所周知，字辈是中国传承千年的重要起名形式，像老一辈很多人名字都是按家谱起的，父姓+中间的家谱，家长起好最后一个字就好了，这样的起名方式挺简单省事的
        </van-collapse-item>
        <van-collapse-item title="字辈谱" name="2">万代荣禄传后名，<br/>万代荣禄传后名，<br/>万代荣禄传后名，<br/>万代荣禄传后名。</van-collapse-item>
        <van-collapse-item title="管理员" name="3">
          <people-tag name="光头勇" :right="true" :bottom="true" :sex="0" @click.native="viewManager"/>
          <people-tag name="光头勇" :right="true" :sex="1" @click.native="viewManager"/>
          <people-tag name="光头勇" :right="true" :bottom="true" :sex="0" @click.native="viewManager"/>
          <people-tag name="光头勇" :right="true" :sex="1" @click.native="viewManager"/>
          <people-tag name="光头勇" :right="true" :bottom="true" :sex="0" @click.native="viewManager"/>
          <people-tag name="光头勇" :right="true" :sex="1" @click.native="viewManager"/>
        </van-collapse-item>
      </van-collapse>
      <van-cell title="创建者">
        <people-tag name="光头勇" @click.native="viewManager" :sex="1"/>
      </van-cell>
      <van-cell title="创建时间" value="2023-02-12 16:02:34"/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      activeNames: ['1', '2', '3'],
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    viewManager() {
      this.$toast.success('查看管理员信息');
    },
    viewConditionalMembers() {
      this.$toast.success('查看特定条件家族成员');
    }
  }
}
</script>