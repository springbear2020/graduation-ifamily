<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow @click-left="backFamily" @click-right="createFamily">
      <template #right>
        <van-icon name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-card tag="李" title="相亲相爱一家人" desc="湖北省/武汉市/江岸区" centered
              thumb="https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg"
              @click="viewFamily">
      <template #tags>
        <van-tag plain type="primary">总 524</van-tag>
        <van-tag plain type="success">男 345</van-tag>
        <van-tag plain type="success">女 234</van-tag>
        <van-tag plain type="warning">生 453</van-tag>
        <van-tag plain type="warning">逝 325</van-tag>
      </template>
      <template #footer>
        <van-button v-model="checked" hairline size="mini" type="info" plain disabled>默认家族</van-button>
      </template>
    </van-card>

    <van-card tag="云" title="云族谱示例家庭" desc="广东省/深圳市/南山区" centered v-for="i in 10" :key="i"
              thumb="https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg"
              @click="viewFamily">
      <template #tags>
        <van-tag plain type="primary">总 524</van-tag>
        <van-tag plain type="success">男 345</van-tag>
        <van-tag plain type="success">女 234</van-tag>
        <van-tag plain type="warning">生 453</van-tag>
        <van-tag plain type="warning">逝 325</van-tag>
      </template>
    </van-card>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      checked: true
    }
  },
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    createFamily() {
      this.$router.push('/family/create')
    },
    viewFamily() {
      this.$router.replace('/family')
    },
    setDefaultFamily() {
      this.$toast.success('设为默认家族')
    }
  }
}
</script>