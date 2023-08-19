<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace(dstRoute)"/>

    <van-form @submit="handleSubmit">
      <!-- 图片 -->
      <div class="flex-container">
        <van-field :rules="[{ required: true, message: '请选择您的家族封面图片' }]">
          <template #input>
            <van-uploader max-count="1" :after-read="afterRead" v-model="fileList" :max-size="5 * 1024 * 1024"
                          @oversize="$toast('文件大小不能超过 5MB')" :before-read="beforeRead"/>
          </template>
        </van-field>
      </div>

      <!-- 名称、姓氏 -->
      <van-field required label="家族名称" placeholder="家族名称" size="large" clearable autofocus
                 v-model.trim="formData.title"
                 :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写家族名称, 长度不大于 30' }]"/>
      <van-field required label="家族姓氏" placeholder="家族姓氏" size="large" clearable
                 v-model.trim="formData.surname"
                 :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写家族姓氏, 长度不大于 30' }]"/>

      <!-- 地址、祖籍 -->
      <van-field label="家族地址" readonly clickable name="area" size="large" placeholder="点击选择省市区" required
                 :rules="[{ required: true, message: '请选择家族地址' }]"
                 v-model.trim="formData.address" @click="showAreaPopup = true; areaType = '0'"/>
      <van-field label="家族祖籍" readonly clickable name="area" size="large" placeholder="点击选择省市区"
                 v-model.trim="formData.ancestryAddress" @click="showAreaPopup = true; areaType = '1'"/>

      <!-- 简介、字辈歌 -->
      <van-field rows="2" autosize label="家族简介" type="textarea" maxlength="100" size="large" placeholder="家族简介"
                 v-model.trim="formData.introduction" show-word-limit clearable/>
      <van-field rows="2" autosize label="字辈歌" type="textarea" maxlength="100" size="large" placeholder="字辈歌"
                 v-model.trim="formData.generationSong" show-word-limit clearable/>

      <div class="block-button-container">
        <van-button type="info" block>{{ type === '0' ? '立即创建' : '保存' }}</van-button>
      </div>
    </van-form>

    <!-- 地址选择弹出层 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <div class="full-address-wrapper">
            <van-field rows="1" autosize label="详细地址" type="textarea" maxlength="100" placeholder="详细地址"
                       show-word-limit v-model="fullAddress"/>
          </div>
        </template>
      </van-area>
    </van-popup>
  </div>
</template>

<script>
import {areaList} from '@vant/area-data'
import {imageUploader} from "@/mixin/image-uploader";

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
      fileList: [],
      formData: {
        id: '',
        cover: '',
        title: '',
        surname: '',
        address: '',
        ancestryAddress: '',
        introduction: '',
        generationSong: ''
      }
    }
  },
  mixins: [imageUploader],
  mounted() {
    this.areaList = areaList
    // [0]创建家族 [1]编辑家族
    let type = this.$route.params.type
    if (!(type === '0' || type === '1')) {
      type = '0'
    }
    this.type = type
    // 修改图片类型为家族封面 [1]用户头像 [2]家族封面
    this.imgType = '2';
    // 如果是编辑家族，则取出仓库中的默认家族信息赋值给表单
    this.initFormData()
  },
  watch: {
    // 图片上传成功,将家族封面链接保存到表单信息中
    imgUrl() {
      this.formData.cover = this.imgUrl
    }
  },
  computed: {
    title() {
      return this.type === '0' ? '创建家族' : '编辑家族'
    },
    dstRoute() {
      return this.type === '0' ? '/family' : '/family/info'
    }
  },
  methods: {
    initFormData() {
      // 如果是编辑家族，则取出仓库中的默认家族信息赋值给表单
      if (this.type === '1') {
        const defaultGenealogy = this.$store.getters['genealogy/defaultGenealogy']
        if (defaultGenealogy.id) {
          const {id, cover, title, surname, address, ancestryAddress, introduction, generationSong} = defaultGenealogy
          Object.assign(this.formData, {
            id,
            cover,
            title,
            surname,
            address,
            ancestryAddress,
            introduction,
            generationSong
          })
          this.fileList.push({url: cover})
        }
      }
    },
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress : area;
      if (this.areaType === '0') {
        this.formData.address = area
      } else if (this.areaType === '1') {
        this.formData.ancestryAddress = area
      }
      this.showAreaPopup = false;
    },
    handleSubmit() {
      if (this.type === '0') {
        this.saveGenealogy()
      } else if (this.type === '1') {
        this.editGenealogy()
      }
    },
    saveGenealogy() {
      this.$api.genealogy.createGenealogy(this.formData).then(() => {
        // 查询最新的家族列表信息
        this.$store.dispatch('genealogy/listGenealogyList')
        this.$toast.success('创建成功')
        this.$router.replace("/family")
      }).catch(() => {
        this.$toast.fail(`${this.title}失败`)
      })
    },
    editGenealogy() {
      this.$api.genealogy.updateDefaultGenealogy(this.formData).then(() => {
        // 查询最新的家族列表信息
        this.$store.dispatch('genealogy/listGenealogyList').then(() => {
          this.$toast.success('更新成功')
          this.$router.replace("/family/info")
        }).catch(err => {
          this.$toast.fail(err.data || err.desc)
        })
      }).catch(err => {
        this.$toast.fail(err.data || err.desc)
      })
    }
  }
}
</script>

<style scoped>
.flex-container {
  display: flex;
}

.van-uploader {
  margin: auto auto;
  padding: 24px;
}

/deep/ .van-uploader__preview {
  margin: 0;
}

/deep/ .van-uploader__upload {
  margin: 0;
}

.block-button-container {
  padding: 8px 16px;
}

/deep/ .flex-container .van-field__error-message {
  text-align: center;
}

.full-address-wrapper .van-field {
  background-color: #f7f8fa;
}
</style>
