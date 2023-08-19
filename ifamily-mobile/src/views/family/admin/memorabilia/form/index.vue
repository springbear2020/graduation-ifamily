<template>
  <div>
    <van-nav-bar title="编纂大事" left-arrow @click-left="$router.replace('/family/admin')"/>

    <van-form @submit="handleSave">
      <van-field required size="large" label="标题" placeholder="家族大事标题" v-model.trim="formData.title"
                 :rules="[{ required: true, pattern: /^.{1,100}$/, message: '请填写家族大事标题, 长度不大于 100' }]"
      />

      <van-field required size="large" label="时间" placeholder="点击选择年份" readonly clickable
                 v-model="formData.occurredYear" @click="showYearPicker = true"
                 :rules="[{ required: true, message: '请选择家族大事发生时间' }]"
      />

      <van-field required size="large" label="配图" :rules="[{ required: true, message: '请选择家族大事配图' }]">
        <template #input>
          <van-uploader max-count="1" v-model="fileList"
                        :before-read="beforeRead" :after-read="afterRead" :max-size="5 * 1024 * 1024"
                        @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
          />
        </template>
      </van-field>

      <van-field required size="large" label="内容" placeholder="家族大事内容" rows="10" type="textarea" maxlength="1000"
                 show-word-limit v-model.trim="formData.content" :autosize="true"
                 :rules="[{ required: true,  message: '请填写家族大事内容, 长度不大于 100' }]"
      />

      <div class="block-button-container">
        <van-button type="info" block>保存</van-button>
      </div>
    </van-form>

    <!-- 年份选择动作面板 -->
    <van-action-sheet v-model="showYearPicker">
      <van-picker title="选择家族大事发生年份" show-toolbar
                  :columns="columns" @cancel="showYearPicker = false"
                  @confirm="(val) => {this.formData.occurredYear = val; this.showYearPicker = false}"
      />
    </van-action-sheet>
  </div>
</template>

<script>
import {imageUploader} from "@/mixin/image-uploader";
import dayjs from "dayjs";

export default {
  name: "index",
  mixins: [imageUploader],
  data() {
    return {
      formData: {
        occurredYear: undefined,
        title: undefined,
        cover: undefined,
        content: undefined
      },
      fileList: [],
      showYearPicker: false
    }
  },
  mounted() {
    // 图片类型：[1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]大事配图
    this.imgType = 5
  },
  computed: {
    columns() {
      let curYear = parseInt(dayjs().format('YYYY'))
      let arr = []
      while (curYear > 0) {
        arr.push(curYear--)
      }
      return arr
    }
  },
  watch: {
    imgUrl(imgUrl) {
      this.formData.cover = imgUrl
    }
  },
  methods: {
    handleSave() {
      this.$api.genealogy.saveMemorabilia(this.formData).then(() => {
        this.$router.replace('/family/memorabilia')
        this.$toast.success('发布成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>

<style scoped>
/deep/ .van-uploader__preview {
  margin: 0;
}

/deep/ .van-uploader__upload {
  margin: 0;
}
</style>
