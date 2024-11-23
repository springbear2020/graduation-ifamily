<template>
  <div>
    <div v-for="(moment, index) in momentList" :key="moment.id" class="top">

      <!-- 头像、昵称、描述、更多 -->
      <van-cell center class="avatar-cell" :border="false" @click="viewUser(moment.publisher)">
        <template #title>
          <van-image round width="52" height="52" :src="moment.publisher.avatar || '/ifamily-mobile/img/avatar.jpg'"/>
          <div class="avatar-wrapper">
            <p class="plain-border">{{ moment.publisher.nickname }}</p>
            <p class="plain-border avatar-wrapper_desc">{{ moment.scheduled }}</p>
          </div>
        </template>

        <template #right-icon>
          <van-popover placement="bottom-end" trigger="click"
                       v-model="popoverShowArr[index]" :actions="actions"
                       @select="moreOperation($event, moment.id)">
            <template #reference>
              <van-icon name="ellipsis" size="20" @click.stop="$set(popoverShowArr, index, true)"
                        v-if="moment.publisher.id === userInfo.id"
              />
            </template>
          </van-popover>
        </template>
      </van-cell>

      <!-- 文本内容、图片列表、权限图标、行为图标 -->
      <van-cell class="moment-cell" :border="false">
        <template #default>
          <p style="margin-top: 0" class="line-wrap" v-html="moment.content"/>

          <image-list :data-list="moment.imgUrls || []" style="padding: 0"/>

          <div>
            <div class="permission-icon" v-if="showPermission">
              <div v-if="moment.whoCanSee === 0">
                <van-icon name="closed-eye"/>
                仅自己可见
              </div>
              <div v-else-if="moment.whoCanSee === 1">
                <van-icon name="eye-o"/>
                默认家族成员可见
              </div>
            </div>

            <div class="behavior-icon">
              <van-icon name="good-job" size="20" v-if="moment.currentUserLiked" @click="cancelThumbsUp(moment)"/>
              <van-icon name="good-job-o" size="20" v-else @click="thumbsUp(moment)"/>
              <van-icon name="comment-o" size="20" @click="openCommentBox(moment, index)"/>
            </div>
          </div>
        </template>
      </van-cell>

      <!-- 点赞列表、评论列表 -->
      <van-cell style="padding: 0 16px 10px 16px">
        <template #default>
          <p class="plain-border" v-if="moment.likeList && moment.likeList.length > 0">
            <van-icon name="good-job" size="16"/>&nbsp;
            <span v-for="(item, index) in moment.likeList" :key="item.id" class="bold" @click.stop="viewUser(item)">
              {{ item.nickname }}
              <span v-if="index < moment.likeList.length - 1">、</span>
            </span>
          </p>

          <div class="comment-list" v-if="moment.commentList && moment.commentList.length > 0">
            <p v-for="comment in moment.commentList" :key="comment.id" class="plain-border"
               @click="replyComment(comment, moment.id, index)">
              <span class="bold" @click.stop="viewUser(comment.sourceUser)"
              >{{ comment.sourceUser.nickname || comment.sourceUser.username }}</span>
              回复
              <span class="bold" @click.stop="viewUser(comment.targetUser)"
              >{{ comment.targetUser.nickname || comment.sourceUser.username }}</span>
              :
              <span>{{ comment.content }}</span>
            </p>
          </div>
        </template>
      </van-cell>
    </div>

    <!-- 评论输入框弹出层 -->
    <van-popup v-model="showCommentPopup" position="bottom" :safe-area-inset-bottom="true"
               :lock-scroll="false" :lazy-render="false" :close-on-popstate="true">
      <van-field type="textarea" rows="1" autofocus center size="large" class="comment-box"
                 :autosize="true" v-model="formData.content" :placeholder="commentPlaceholder" ref="commentBox">
        <template #button>
          <van-button size="mini" type="info" class="nav-button" @click="sendComment">
            发送
          </van-button>
        </template>
      </van-field>
    </van-popup>

    <van-popup v-model="showUserPopup" class="full-popup white-background">
      <van-nav-bar title="用户资料" @click-left="showUserPopup = false">
        <template #left>
          <van-icon name="cross" size="20"/>
        </template>
      </van-nav-bar>

      <van-card centered :thumb="clickedUser.avatar || '/ifamily-mobile/img/avatar.jpg'" class="white-background"
                @click-thumb="previewImage(clickedUser.avatar || '/ifamily-mobile/img/avatar.jpg')">
        <template #desc>
          <van-cell center>
            <p class="van-ellipsis plain-border">昵称：{{ clickedUser.nickname }}</p>
            <p class="van-ellipsis plain-border">UID：{{ clickedUser.username }}</p>
          </van-cell>
        </template>
      </van-card>

      <van-cell is-link class="top">设置备注及标签</van-cell>
      <van-cell is-link title="电话号码" value="15985485628"></van-cell>
      <van-cell is-link>动态</van-cell>
      <van-cell is-link>更多信息</van-cell>

      <div class="user-operation">
        <van-cell class="top ">
          <van-icon name="add-o"/>
          加好友
        </van-cell>
        <van-cell>
          <van-icon name="chat-o"/>
          发消息
        </van-cell>
      </div>
    </van-popup>
  </div>
</template>

<script>
import ImageList from '@/components/basis/image-list'
import {previewImage} from "@/mixin/common-utils";

export default {
  name: "social-moments",
  components: {ImageList},
  mixins: [previewImage],
  props: {
    dataList: {
      type: Array,
      required: true
    },
    showPermission: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      momentList: [],
      popoverShowArr: [],
      actions: [
        {text: '编辑', icon: 'edit'},
        {text: '删除', icon: 'delete-o'}
      ],
      commentPlaceholder: '说点什么吧...',
      showCommentPopup: false,
      // 选择的动态数组索引
      chosenMomentIndex: undefined,
      // 选择的评论发布者用户
      chosenCommentUser: undefined,
      // 新评论内容
      formData: {
        content: undefined,
        momentId: undefined,
        parentId: undefined,
        targetUserId: undefined
      },
      showUserPopup: false,
      clickedUser: {},
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.user.user || {}
    }
  },
  watch: {
    dataList: {
      immediate: true,
      handler(list) {
        this.momentList = list
        if (list) {
          // 动态操作气泡框响应式数组
          let arr = []
          list.forEach(() => arr.push(false))
          this.popoverShowArr.splice(0, this.popoverShowArr.length, ...arr)
        }
      }
    }
  },
  methods: {
    thumbsUp(moment) {
      this.$api.social.likeMoment(moment.id).then((momentLikeId) => {
        if (!moment.likeList) {
          moment.likeList = []
        }

        // 将当前用户信息添加到当前动态的点赞列表中
        const {id, nickname, username, avatar} = this.userInfo
        const curLike = {id: momentLikeId, nickname: nickname, userId: id, momentId: moment.id, username, avatar}
        this.$set(moment.likeList, moment.likeList.length, curLike)

        moment.currentUserLiked = true
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    },
    cancelThumbsUp(moment) {
      this.$api.social.unLikeMoment(moment.id).then(() => {
        // 从当前动态的点赞列表中移除当前用户点赞
        moment.likeList = moment.likeList.filter(item => item.userId !== this.userInfo.id)
        moment.currentUserLiked = false
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    },
    openCommentBox(moment, index) {
      if (moment.id !== this.formData.momentId) {
        this.formData.content = undefined
      }
      this.commentPlaceholder = '说点什么吧...'
      this.formData.momentId = moment.id;
      this.formData.targetUserId = moment.publisher.id
      this.chosenCommentUser = moment.publisher
      this.chosenMomentIndex = index
      this.showCommentPopup = true
      this.$nextTick(() => {
        this.$refs.commentBox.focus()
      })
    },
    sendComment() {
      if (!this.formData.content || this.formData.content.length === 0) {
        this.$toast({message: '请填写动态内容', position: 'bottom'})
        return
      }

      // 如果不存在父级评论，则将当前评论的父级评论 ID 设置为当前动态 ID
      if (!this.formData.parentId) {
        this.formData.parentId = this.formData.momentId
      }
      // 如果不存在被回复者用户 ID，则设置为当前登录用户 ID
      if (!this.formData.targetUserId) {
        this.formData.targetUserId = this.userInfo.id
      }

      this.$api.social.commentMoment(this.formData).then((commentId) => {
        this.showCommentPopup = false
        // 将当前用户评论添加到当前动态的评论列表中
        this.pushComment(commentId)
        this.formData = {content: undefined, momentId: undefined, parentId: undefined, targetUserId: undefined}
        this.$toast.success('评论成功');
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      });
    },
    pushComment(commentId) {
      let curComment = {
        id: commentId,
        content: this.formData.content,
        sourceUser: this.userInfo,
        targetUser: this.chosenCommentUser
      }
      if (!curComment.targetUser) {
        curComment.targetUser = this.userInfo
      }

      const idx = this.chosenMomentIndex;
      if (!this.momentList[idx].commentList) {
        this.momentList[idx].commentList = []
      }
      this.$set(this.momentList[idx].commentList, this.momentList[idx].commentList.length, curComment)
    },
    replyComment(comment, momentId, index) {
      this.chosenMomentIndex = index
      this.chosenCommentUser = comment.sourceUser
      this.commentPlaceholder = `回复${comment.sourceUser.nickname}`
      this.formData.momentId = momentId
      this.formData.parentId = comment.id
      this.formData.targetUserId = comment.sourceUser.id
      this.showCommentPopup = true
      this.$nextTick(() => {
        this.$refs.commentBox.focus()
      })
    },
    moreOperation(action, momentId) {
      if ('删除' === action.text) {
        this.handleDeleteMoment(momentId)
      } else if ('编辑' === action.text) {
        this.$router.push(`/mine/moment/post/2?mid=${momentId}`)
      }
    },
    handleDeleteMoment(momentId) {
      this.$dialog.confirm({
        title: '删除动态',
        message: '您确定要删除该条动态？'
      }).then(() => {
        this.$api.social.removeMoment(momentId).then(() => {
          this.momentList = this.momentList.filter(item => item.id !== momentId)
          this.$toast.success('删除成功')
        }).catch(msg => {
          this.$toast({message: msg, position: 'bottom'})
        })
      }).catch(() => {
      })
    },
    viewUser(clickedUser) {
      // 特殊处理点击点赞列表用户 userId
      if (clickedUser.userId) {
        clickedUser.id = clickedUser.userId
      }
      Object.assign(this.clickedUser, clickedUser)
      this.showUserPopup = true
    }
  }
}
</script>

<style scoped>
.moment-cell {
  padding-top: 0;
  padding-bottom: 0;
}

.permission-icon {
  float: left;
  padding: 10px 0;
}

.permission-icon div {
  color: #969799;
  font-size: 12px;
  height: 20px;
}

.behavior-icon {
  float: right;
}

.behavior-icon i {
  padding: 10px;
}

.comment-box {
  padding: 9px;
  background-color: #f7f8fa;
  border: 1px solid #ebedf0;
}

/deep/ .comment-box .van-field__control {
  background-color: white;
  font-size: 16px;
}

/deep/ .van-overlay {
  background-color: transparent;
}

.full-popup .van-cell {
  padding: 20px 16px;
}

.user-operation .van-cell__value {
  text-align: center;
  color: #1989fa;
}
</style>
