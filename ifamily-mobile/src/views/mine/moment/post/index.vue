<template>
  <div>
    <van-nav-bar left-arrow :title="type === '1' ? '发表动态' : '编辑动态'"
                 @click-left="$router.replace('/mine/moment')" @click-right="handlePost">
      <template #right>
        <van-button type="info" size="mini" class="nav-button">发表</van-button>
      </template>
    </van-nav-bar>

    <van-form class="white-background">
      <van-field rows="5" type="textarea" maxlength="1000" show-word-limit placeholder="分享新鲜事..."
                 autofocus :autosize="true" v-model="formData.content"
      />

      <van-uploader multiple v-model="fileList"
                    :before-read="beforeRead" :after-read="afterRead" :max-size="5 * 1024 * 1024"
                    @oversize="$toast({message: '文件大小不能超过 5MB', position: 'bottom'})"
      />
    </van-form>

    <van-cell title="权限设置" icon="eye-o" is-link :value="actions[formData.whoCanSee].name"
              @click="showPermissionSheet = true" class="top"
    />
    <van-cell title="定时发表" icon="clock-o" is-link :value="formData.scheduled" @click="showDatetimePicker = true"/>

    <!-- 权限设置动作面板 -->
    <van-action-sheet v-model="showPermissionSheet" :actions="actions" cancel-text="取消" close-on-click-action
                      @select="(actions, index) => {this.formData.whoCanSee = index}"
    />

    <!-- 定时发表动作面板 -->
    <van-action-sheet v-model="showDatetimePicker">
      <van-datetime-picker type="datetime" title="选择年月日小时分钟"
                           :min-date="minDate" :max-date="maxDate" @cancel="showDatetimePicker = false"
                           @confirm="confirmDate" :formatter="formatter"
      />
    </van-action-sheet>
  </div>
</template>

<script>
import dayjs from "dayjs";
import {imageUploader} from "@/mixin/image-uploader"

export default {
  name: "index",
  mixins: [imageUploader],
  data() {
    return {
      fileList: [],
      // 权限设置：[0]仅自己可见 [1]默认家族成员可见
      showPermissionSheet: false,
      actions: [{name: '仅自己可见'}, {name: '默认家族成员可见'}],
      // 定时发表
      showDatetimePicker: false,
      minDate: new Date(),
      maxDate: dayjs(new Date()).add(7, 'day').$d,
      // [1]发布动态 [2]编辑动态
      type: '1',
      formData: {
        id: undefined,
        content: undefined,
        whoCanSee: 1,
        scheduled: '立即发表',
        imgUrls: undefined
      }
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '1' || type === '2')) {
      type = '1'
    }
    this.type = type

    // [1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]大事配图 [6]动态图片
    this.imgType = '6';

    this.initDefaultGenealogyName()
    this.initMoment()
  },
  watch: {
    imgUrl(url) {
      // 图片上传成功，用图片链接替换文件 File 对象
      this.fileList[this.fileList.length - 1] = {url}
    }
  },
  methods: {
    initDefaultGenealogyName() {
      const defaultGenealogyName = this.$store.getters["genealogy/defaultGenealogy"].title
      if (defaultGenealogyName) {
        this.actions[1].name = `《${defaultGenealogyName}》家族成员可见`;
      }
    },
    confirmDate(chooseDate) {
      this.formData.scheduled = dayjs(chooseDate).format('YYYY-MM-DD HH:mm')
      this.showDatetimePicker = false
    },
    formatter(type, val) {
      if (type === 'year') {
        return `${val}年`
      } else if (type === 'month') {
        return `${val}月`;
      } else if (type === 'day') {
        return `${val}日`;
      } else if (type === 'hour') {
        return `${val}时`
      } else if (type === 'minute') {
        return `${val}分`
      }
      return val;
    },
    handlePost() {
      const content = this.formData.content
      if (!content || content.length === 0) {
        this.$toast({message: '请填写动态内容', position: 'bottom'})
        return
      }

      // 动态图片列表
      let imgUrls = [];
      this.fileList.forEach(item => {
        if (item.url) {
          imgUrls.push(item.url)
        }
      })
      if (imgUrls.length > 0) {
        this.formData.imgUrls = imgUrls
      }

      // 定时发表
      if ('立即发表' === this.formData.scheduled) {
        this.formData.scheduled = dayjs().format('YYYY-MM-DD HH:mm')
      }

      if (this.type === '1') {
        this.handleCreate()
      } else if (this.type === '2') {
        this.handleEdit()
      }
    },
    handleCreate() {
      // 请求服务器保存用户动态
      this.$api.social.saveMoment(this.formData).then(() => {
        this.$router.replace('/mine/moment')
        this.$toast.success('发表成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    },
    handleEdit() {
      // 请求服务器更新用户动态
      this.$api.social.editMoment(this.formData).then(() => {
        this.$router.replace('/mine/moment')
        this.$toast.success('更新成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    },
    initMoment() {
      // 读取路由路径参数，当编辑动态时初始化动态信息
      const momentId = this.$route.query.mid
      if (momentId && this.type === '2') {
        this.$api.social.getMoment(momentId).then((moment) => {
          Object.assign(this.formData, moment)

          // 动态图片列表预览
          const {imgUrls} = moment
          if (imgUrls && imgUrls.length > 0) {
            imgUrls.forEach(item => this.fileList.push(item))
          }
        }).catch(msg => {
          this.$toast({message: msg, position: 'bottom'})
        })
      }
    }
  }
}
</script>

<style scoped>
/deep/ .van-cell {
  padding: 20px 16px;
}

/deep/ .van-uploader__preview {
  margin: 8px 0 8px 8px;
}

/deep/ .van-uploader__upload {
  margin: 8px 0 8px 8px;
}
</style>
