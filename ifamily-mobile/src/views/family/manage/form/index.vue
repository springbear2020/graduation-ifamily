<template>
  <div>
    <van-nav-bar :title="type === '0' ? '创建家族' : '编辑家族'" left-arrow
                 @click-left="back" @click-right="handleCreateOrEdit"
    >
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-form>
      <!-- 图片 -->
      <div class="flex-container">
        <van-uploader v-model="imgList" class="cover" max-count="1"/>
      </div>

      <van-cell-group>
        <!-- 名称、姓氏 -->
        <van-field required label="家族名称" placeholder="家族名称" clearable autofocus v-model="formData.title"
                   :border="false"/>
        <van-field required label="家族姓氏" placeholder="家族姓氏" clearable v-model="formData.surname" :border="false"/>
      </van-cell-group>
      <van-cell-group>
        <!-- 地址、祖籍 -->
        <van-field label="家族地址" readonly clickable name="area" placeholder="点击选择省市区" required :border="false"
                   v-model="formData.familyAddress" @click="showAreaPopup = true; areaType = '0'"/>
        <van-field label="家族祖籍" readonly clickable name="area" placeholder="点击选择省市区" :border="false"
                   v-model="formData.familyAncestry" @click="showAreaPopup = true; areaType = '1'"/>
      </van-cell-group>
      <van-cell-group>
        <!-- 简介、字辈歌 -->
        <van-field v-model="formData.introduction" rows="2" autosize label="家族简介" type="textarea" maxlength="100"
                   placeholder="家族简介" :border="false" show-word-limit clearable/>
        <van-field v-model="formData.generationSong" rows="2" autosize label="字辈歌" type="textarea" maxlength="100"
                   placeholder="字辈歌" :border="false" show-word-limit clearable/>
      </van-cell-group>
    </van-form>

    <!-- 地址选择弹出层 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <div class="full-address-container">
            <van-field rows="1" autosize label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
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
      // [0]创建家族 [1]编辑家族
      type: '0',
      showAreaPopup: false,
      areaList: [],
      fullAddress: '',
      // [0]家族地址 [1]家族祖籍
      areaType: '0',
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
    this.type = this.$route.params.type
  },
  methods: {
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress : area;
      if (this.areaType === '0') {
        this.formData.familyAddress = area
      } else if (this.areaType === '1') {
        this.formData.familyAncestry = area
      }
      this.showAreaPopup = false;
    },
    handleCreateOrEdit() {
      console.log(this.formData)
      this.formData.cover = this.imgList[0].url
      this.$toast.success('操作成功')
    },
    back() {
      if (this.type === '0') {
        this.$router.replace('/family/list')
      } else if (this.type === '1') {
        this.$router.replace('/family/manage')
      }
    }
  }
}
</script>