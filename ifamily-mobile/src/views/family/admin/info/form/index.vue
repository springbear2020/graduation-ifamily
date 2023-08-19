<template>
  <div>
    <van-nav-bar :title="title" left-arrow @click-left="$router.replace(backRoute)"/>

    <!-- 家族信息表单 -->
    <van-form @submit="handleSubmit">
      <div class="flex-container">
        <van-field :rules="[{ required: true, message: '请选择您的家族封面图片' }]">
          <template #input>
            <van-uploader max-count="1" v-model="fileList"
                          :before-read="beforeRead" :after-read="afterRead" :max-size="5 * 1024 * 1024"
                          @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
            />
          </template>
        </van-field>
      </div>

      <!-- 名称、姓氏 -->
      <van-field required label="家族名称" placeholder="家族名称" size="large" autofocus v-model.trim="formData.title"
                 :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写家族名称, 长度不大于 30' }]"/>
      <van-field required label="家族姓氏" placeholder="家族姓氏" size="large" v-model.trim="formData.surname"
                 :rules="[{ required: true, pattern: /^.{1,30}$/, message: '请填写家族姓氏, 长度不大于 30' }]"/>

      <!-- 地址、祖籍 -->
      <van-field label="家族地址" readonly clickable size="large" placeholder="点击选择省市区" required
                 :rules="[{ required: true, message: '请选择家族地址' }]"
                 v-model.trim="formData.address" @click="showAreaSheet = true; areaType = '0'"/>
      <van-field label="家族祖籍" readonly clickable size="large" placeholder="点击选择省市区"
                 v-model.trim="formData.ancestryAddress" @click="showAreaSheet = true; areaType = '1'"/>

      <!-- 简介、字辈歌 -->
      <van-field rows="2" label="家族简介" type="textarea" maxlength="1000" size="large" placeholder="家族简介"
                 v-model.trim="formData.introduction" :autosize="true" show-word-limit/>
      <van-field rows="2" label="字辈歌" type="textarea" maxlength="1000" size="large" placeholder="字辈歌"
                 v-model.trim="formData.generationSong" :autosize="true" show-word-limit/>

      <div class="block-button-container">
        <van-button type="info" block>{{ type === '0' ? '创建家族' : '保存' }}</van-button>
      </div>
    </van-form>

    <!-- 地址选择动作面板 -->
    <van-action-sheet v-model="showAreaSheet">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaSheet = false" title="选择省市区">
        <template #columns-bottom>
          <div class="full-address-wrapper">
            <van-field rows="1" label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
                       :autosize="true" v-model="fullAddress" class="grey-background"
            />
          </div>
        </template>
      </van-area>
    </van-action-sheet>
  </div>
</template>

<script>
import {areaList} from '@vant/area-data'
import {imageUploader} from "@/mixin/image-uploader";

export default {
  name: "index",
  data() {
    return {
      fileList: [],
      // [0]创建家族 [1]编辑家族
      type: '0',
      // [0]家族地址 [1]家族祖籍
      areaType: '0',
      showAreaSheet: false,
      areaList: [],
      fullAddress: '',
      formData: {
        id: undefined,
        cover: undefined,
        title: undefined,
        surname: undefined,
        address: undefined,
        ancestryAddress: undefined,
        introduction: undefined,
        generationSong: undefined
      }
    }
  },
  mixins: [imageUploader],
  mounted() {
    this.areaList = areaList
    // [1]用户头像 [2]家族封面
    this.imgType = '2';

    // [0]创建家族 [1]编辑家族
    let type = this.$route.params.type
    if (!(type === '0' || type === '1')) {
      type = '0'
    }
    this.type = type

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
    backRoute() {
      return this.type === '0' ? '/family' : '/family/admin'
    }
  },
  methods: {
    initFormData() {
      if (this.type === '1') {
        const defaultGenealogy = this.$store.getters['genealogy/defaultGenealogy']
        if (defaultGenealogy.id) {
          const {id, cover, title, surname, address, ancestryAddress, introduction, generationSong} = defaultGenealogy
          Object.assign(this.formData, {id, cover, title, surname, address, ancestryAddress, introduction, generationSong})
          this.fileList.push({url: cover})
        } else {
          this.$toast({message: "默认家族信息不存在", position: 'bottom'})
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
      this.showAreaSheet = false;
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
        // 创建成功，切换到新创建的默认家族，移除所有家族信息
        this.$store.commit('genealogy/CLEAR_STATE')
        this.$router.replace("/family")
        this.$toast.success('创建成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    },
    editGenealogy() {
      this.$api.genealogy.updateGenealogy(this.formData).then(() => {
        // 更新成功，查询最新的家族列表信息
        this.$store.dispatch('genealogy/genealogies').then(() => {
          this.$router.replace("/family/info")
          this.$toast.success('更新成功')
        }).catch(msg => {
          this.$toast({message: msg, position: 'bottom'})
        })
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
.van-uploader {
  margin: auto auto;
}

/deep/ .van-uploader__preview {
  margin: 0;
}

/deep/ .van-uploader__upload {
  margin: 0;
}

/deep/ .flex-container .van-field__error-message {
  text-align: center;
}
</style>
