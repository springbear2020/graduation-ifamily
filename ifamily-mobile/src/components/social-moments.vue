<template>
  <div>
    <van-cell-group v-for="moment in dataList" :key="moment.id">
      <!-- 头像、姓名、时间、更多 -->
      <portrait-desc :person="moment" @click.native="viewUserPersonal" :more="true" @more-operation="moreOperation"/>

      <!-- 文本内容、图片列表、操作图标 -->
      <van-cell>
        <template #default>
          <p class="text-content">{{ moment.desc }}</p>

          <image-list :data-list="moment.photoList"/>

          <div class="behavior-icon">
            <van-icon name="good-job" size="20" v-if="goodJob" @click="cancelThumbsUp"/>
            <van-icon name="good-job-o" size="20" v-else @click="thumbsUp"/>
            <van-icon name="chat-o" size="20" @click="giveComment = true"/>
            <van-icon name="share-o" size="20" @click="showShare = true"/>
          </div>
        </template>
      </van-cell>

      <!-- 点赞列表、评论列表、评论框 -->
      <van-cell>
        <template #default>
          <p class="like-list">
            <van-icon name="good-job" size="16" v-if="goodJob"/>
            <van-icon name="good-job-o" size="16" v-else/>&nbsp;
            <span v-for="people in moment.likeList" :key="people.id">{{ people.name }}，</span>
          </p>

          <div class="comments-list">
            <p v-for="comment in moment.commentsList" :key="comment.id">
              {{ comment.source }} 回复 {{ comment.target }}：{{ comment.content }}
            </p>
          </div>

          <van-field class="comments-box" placeholder="说点什么吧..." left-icon="smile-comment-o"
                     v-model="comment" @click-right-icon="$emit('post-comment', moment, comment)" v-show="giveComment">
            <template #right-icon>
              <van-icon name="guide-o" size="16"/>
            </template>
          </van-field>
        </template>
      </van-cell>
    </van-cell-group>

    <!-- 动态分享面板 -->
    <van-share-sheet title="立即分享给好友" v-model="showShare" :options="options" @select="onSelect"/>
  </div>
</template>

<script>

export default {
  name: "social-moments",
  data() {
    return {
      comment: '',
      goodJob: false,
      giveComment: false,
      showShare: false,
      options: [
        [
          {name: '微信', icon: 'wechat'},
          {name: '朋友圈', icon: 'wechat-moments'},
          {name: '微博', icon: 'weibo'},
          {name: 'QQ', icon: 'qq'},
        ],
        [
          {name: '复制链接', icon: 'link'},
          {name: '分享海报', icon: 'poster'},
          {name: '二维码', icon: 'qrcode'},
          {name: '小程序码', icon: 'weapp-qrcode'},
        ],
      ],
    }
  },
  props: {
    dataList: {
      type: Array,
      required: true
    }
  },
  methods: {
    viewUserPersonal() {
      this.$toast.success('查看用户信息')
    },
    moreOperation() {
      this.$toast.fail('更多操作')
    },
    thumbsUp() {
      this.$toast.success('点赞')
      this.goodJob = true
    },
    cancelThumbsUp() {
      this.$toast.fail('取消点赞')
      this.goodJob = false
    },
    onSelect(option) {
      this.$toast(option.name);
      this.showShare = false;
    },
  }
}
</script>

<style scoped>
.text-content {
  margin-top: 0;
  margin-bottom: 5px;
}

.behavior-icon {
  float: right;
}

.behavior-icon i {
  padding: 10px;
}

.like-list {
  margin: 0;
}

.comments-list p {
  margin: 0;
}

.comments-box {
  padding: 5px 5px;
  background-color: #f7f8fa;
}

/deep/ .van-share-sheet__options {
  justify-content: center
}
</style>