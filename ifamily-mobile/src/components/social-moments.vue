<template>
  <div>
    <van-cell-group v-for="moment in dataArray" :key="moment.id">
      <!-- 头像、备注、时间、更多 -->
      <van-cell :border="false">
        <template #title>
          <van-image round width="50" height="50" :src="moment.portrait"/>
          <div class="desc">
            <p class="name">{{ moment.name }}</p>
            <p>{{ moment.datetime }}</p>
          </div>
        </template>

        <template #right-icon>
          <van-icon name="ellipsis"/>
        </template>
      </van-cell>

      <!-- 文本内容、图片列表、操作 -->
      <van-cell>
        <template #default>
          <p class="text-content">{{ moment.content }}</p>

          <div>
            <van-image v-for="(img, index) in moment.photoList" :src="img.url" :key="img.id"
                       @click="viewImage(moment.photoList, index)" width="100" height="100"/>
          </div>

          <div class="behavior-icon">
            <van-icon name="good-job-o" size="20px"/>
            <van-icon name="chat-o" size="20px"/>
            <van-icon name="share-o" size="20px"/>
          </div>
        </template>
      </van-cell>

      <!-- 点赞列表、评论内容列表、评论框 -->
      <van-cell>
        <template #default>
          <p class="like-list">
            <van-icon name="good-job-o"/>&nbsp;
            <span v-for="people in moment.likeList" :key="people.id">{{ people.name }}，</span>
          </p>

          <div class="comments-list">
            <p v-for="comment in moment.commentsList" :key="comment.id">
              {{ comment.source }} 回复 {{ comment.target }}：{{ comment.content }}
            </p>
          </div>

          <div>
            <van-field class="comments-box" placeholder="说点什么吧..." right-icon="guide-o"
                       v-model="commentContent" @click-right-icon="$emit('post-comment', moment, commentContent)"/>
          </div>
        </template>
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
import {ImagePreview} from 'vant';

export default {
  name: "social-moments",
  data() {
    return {
      commentContent: '',
      dataArray: [],
    }
  },
  props: ['dataList'],
  mounted() {
    this.dataArray = this.dataList && this.dataList.length > 0 ? this.dataList : []
  },
  methods: {
    viewImage(imgList, index) {
      let images = []
      imgList.forEach(item => {
        images.push(item.url)
      })
      ImagePreview({images: images, startPosition: index, closeable: true, showIndicators: true})
    },
  }
}
</script>

<style scoped>
.van-cell__title {
  display: flex;
}

.desc {
  margin-left: 5px;
}

.desc p {
  margin: 0 0;
}

.desc .name {
  font-size: large;
  font-weight: bold;
}

.text-content {
  margin-top: 0;
  margin-bottom: 5px;
}

.van-image {
  /*TODO bottom padding ?*/
  margin-right: 8px;
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
  padding: 5px 0;
  background-color: #f7f8fa;
}
</style>