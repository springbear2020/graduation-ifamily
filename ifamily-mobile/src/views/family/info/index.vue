<template>
  <div>
    <van-nav-bar title="家族信息" left-arrow @click-left="backFamily" @click-right="exitFamily">
      <template #right>
        <van-icon name="exit" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <div class="img-portrait-father">
      <van-image
          class="img-portrait"
          width="100"
          height="100"
          src="https://img01.yzcdn.cn/vant/cat.jpeg"
      />
    </div>

    <van-cell-group>
      <van-cell title="家族名称" value="相亲相爱"/>
      <van-cell title="家族姓氏" value="李"/>
      <van-cell title="家族地址" value="湖北省/武汉市/洪山区"/>
      <van-collapse v-model="activeNames">
        <van-collapse-item title="家族简介" name="1">
          众所周知，字辈是中国传承千年的重要起名形式，像老一辈很多人名字都是按家谱起的，父姓+中间的家谱，家长起好最后一个字就好了，这样的起名方式挺简单省事的
        </van-collapse-item>
        <van-collapse-item title="字辈谱" name="2">万代荣禄传后名</van-collapse-item>
      </van-collapse>
      <van-cell title="创建者">
        <van-icon name="manager-o"/>
      </van-cell>
      <van-cell title="管理员" class="van-ellipsis">
        <van-tag type="primary" plain size="large">小明</van-tag>
        <van-tag type="primary" plain size="large">小红</van-tag>
        <van-tag type="primary" plain size="large">小花</van-tag>
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
      activeNames: [],
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    exitFamily() {
      this.$dialog.confirm({
        title: '退出家族',
        message: '您确定要退出《相亲相爱》这个家族吗？',
      }).then(() => {
        this.$toast.success('退出家族成功')
      }).catch(() => {
        // on cancel
      });
    }
  }
}
</script>