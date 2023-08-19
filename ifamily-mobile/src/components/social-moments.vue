<template>
  <div>
    <div v-for="moment in dataList" :key="moment.id" class="van-hairline--bottom">
      <!-- 头像、标题、内容、更多 -->
      <portrait-desc :person="moment" :more="true" @click.native="$toast('查看用户信息')"/>

      <!-- 文本内容、图片列表、行为图标 -->
      <van-cell class="text-content-top">
        <template #default>
          <!-- 文本内容 -->
          <p class="text-content">{{ moment.description }}</p>
          <!-- 图片列表 -->
          <image-list :data-list="moment.imgList"/>
          <!-- 行为图标 -->
          <div class="behavior-icon">
            <van-icon name="good-job" size="20" v-if="goodJob" @click="cancelThumbsUp"/>
            <van-icon name="good-job-o" size="20" v-else @click="thumbsUp"/>
            <van-icon name="comment-o" size="20" @click="giveComment = true"/>
            <van-icon name="share-o" size="20" @click="showShareSheet = true"/>
          </div>
        </template>
      </van-cell>

      <!-- 点赞列表、评论列表、评论输入框 -->
      <van-cell>
        <template #default>
          <!-- 文本内容 -->
          <p class="like-list">
            <van-icon name="good-job" size="16"/>
            <span v-for="people in moment.likeList" :key="people.id">{{ people.name }}，</span>
          </p>
          <!-- 评论列表 -->
          <div class="comment-list">
            <p v-for="comment in moment.commentList" :key="comment.id">
              {{ comment.source }} 回复 {{ comment.target }}：{{ comment.content }}
            </p>
          </div>
          <!-- 评论输入框 -->
          <van-field class="comment-box" placeholder="说点什么吧..." left-icon="smile-comment-o"
                     v-model="comment" @click-right-icon="$emit('post-comment', moment, comment)"
                     v-show="giveComment">
            <template #right-icon>
              <van-icon name="guide-o" size="16"/>
            </template>
          </van-field>
        </template>
      </van-cell>
    </div>

    <!-- 动态分享面板 -->
    <van-share-sheet title="立即分享给好友" v-model="showShareSheet" :options="options" @select="onSelect"/>
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
      showShareSheet: false,
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
    thumbsUp() {
      this.$toast('点赞')
      this.goodJob = true
    },
    cancelThumbsUp() {
      this.$toast('取消点赞')
      this.goodJob = false
    },
    onSelect(option) {
      this.$toast(option.name);
      this.showShareSheet = false;
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
  padding: 15px;
}

.like-list {
  margin: 0;
}

.comment-list p {
  margin: 0;
}

.comment-box {
  padding: 5px 5px;
  background-color: #f7f8fa;
}

/deep/ .van-share-sheet__options {
  justify-content: center
}

.text-content-top {
  padding-top: 0;
  padding-bottom: 0;
}
</style>