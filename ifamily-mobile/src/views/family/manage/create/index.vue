<template>
  <div>
    <van-nav-bar title="创建家族" left-arrow @click-left="backFamilyList" @click-right="handleCreateFamily">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-form>
      <div class="cover-container">
        <van-uploader v-model="imgList" class="cover" max-count="1"/>
      </div>

      <van-field required label="家族名称" placeholder="家族名称" clearable autofocus/>
      <van-field required label="家族姓氏" placeholder="家族姓氏" clearable/>
      <van-field required label="家族地址" readonly clickable name="area" :value="area" placeholder="点击选择省市区"
                 @click="showArea = true"/>
      <van-field rows="2" label="家族简介" type="textarea" maxlength="100" placeholder="家族简介" show-word-limit clearable/>
      <van-field rows="2" label="字辈谱" type="textarea" maxlength="100" placeholder="字辈谱" show-word-limit clearable/>
    </van-form>

    <!-- 地址选择组件 -->
    <van-popup v-model="showArea" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showArea = false"/>
    </van-popup>
  </div>
</template>

<script>
import {areaList} from '@vant/area-data'

export default {
  name: "index",
  data() {
    return {
      showArea: false,
      areaList: [],
      area: '',
      imgList: [
        {url: 'https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg'}
      ],
    }
  },
  mounted() {
    this.areaList = areaList
  },
  methods: {
    backFamilyList() {
      this.$router.replace('/family/list')
    },
    confirmArea(value) {
      this.area = value
          .filter((item) => !!item)
          .map((item) => item.name)
          .join('/');
      this.showArea = false
    },
    handleCreateFamily() {
      this.$toast.success('创建家族成功')
    }
  }
}
</script>