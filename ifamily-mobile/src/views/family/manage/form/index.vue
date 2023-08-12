<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="backFamilyList" @click-right="handleCreateFamily">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-form>
      <!-- 图片 -->
      <div class="flex-container">
        <van-uploader v-model="imgList" class="cover" max-count="1"/>
      </div>

      <!-- 名称、姓氏 -->
      <van-field required label="家族名称" placeholder="家族名称" clearable autofocus v-model="formData.title"/>
      <van-field required label="家族姓氏" placeholder="家族姓氏" clearable v-model="formData.surname"/>
      <!-- 地址、祖籍 -->
      <van-field label="家族地址" readonly clickable name="area" placeholder="点击选择省市区" required
                 v-model="formData.familyAddress" @click="showAreaPopup = true; areaType = '1'"/>
      <van-field label="家族祖籍" readonly clickable name="area" placeholder="点击选择省市区"
                 v-model="formData.familyAncestry" @click="showAreaPopup = true; areaType = '2'"/>
      <!-- 简介、字辈歌 -->
      <van-field v-model="formData.introduction" rows="2" label="家族简介" type="textarea" maxlength="100"
                 placeholder="家族简介" show-word-limit clearable/>
      <van-field v-model="formData.generationSong" rows="2" label="字辈歌" type="textarea" maxlength="100"
                 placeholder="字辈谱" show-word-limit clearable/>
    </van-form>

    <!-- 地址选择弹出层 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <div class="full-address-container">
            <van-field rows="1" label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
                       v-model="fullAddress"/>
          </div>
        </template>
      </van-area>
    </van-popup>
  </div>
</template>

<script>
import {areaList} from '@vant/area-data'

export default {
  name: "index",
  data() {
    return {
      title: '创建家族',
      showAreaPopup: false,
      areaList: [],
      fullAddress: '',
      // [1]家族地址 [2]家族祖籍
      areaType: '1',
      imgList: [{url: 'https://res.dps.cn/template/thn/202109/8107a70605b27e46979a6958c442e640.jpg'}],
      formData: {
        cover: '',
        title: '',
        surname: '',
        familyAddress: '',
        familyAncestry: '',
        introduction: '',
        generationSong: ''
      }
    }
  },
  mounted() {
    this.areaList = areaList
    // [1]编辑家族 [2]创建家族
    this.title = this.$route.params.type === '1' ? '编辑家族' : '创建家族'
  },
  methods: {
    backFamilyList() {
      this.$router.replace('/family/list')
    },
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress : area;
      if (this.areaType === '1') {
        this.formData.familyAddress = area
      } else {
        this.formData.familyAncestry = area
      }
      this.showAreaPopup = false;
    },
    handleCreateFamily() {
      console.log(this.formData)
      this.formData.cover = this.imgList[0].url
      this.$toast.success('创建家族成功')
    }
  }
}
</script>