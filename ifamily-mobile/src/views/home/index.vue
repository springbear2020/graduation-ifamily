<template>
  <div>
    <van-nav-bar title="主页" @click-right="$router.push('/home/message')">
      <template #right>
        <van-badge dot>
          <van-icon name="envelop-o" size="20"/>
        </van-badge>
      </template>
    </van-nav-bar>

    <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
    </van-swipe>

    <van-tabs>
      <van-tab title="成员动态">
        <van-pull-refresh success-text="刷新成功" v-model="isRefreshing" @refresh="onRefresh">
          <van-list isFinished-text="没有更多了" error-text="请求失败，点击重新加载"
                    v-model="isLoading" :isFinished="isFinished" @load="onLoad" :error.sync="error">
            <social-moments :data-list="momentList" @post-comment="handlePostComment"/>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import socialMoments from '@/components/business/social-moments'

export default {
  name: "index",
  components: {
    socialMoments,
  },
  data() {
    return {
      momentList: [
        {
          "id": 1,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "name": "光头勇",
          "content": "2023-02-23 17:23",
          "description": "所以认真才显得格格不入，好像你只想跟我风花雪月过眼云烟，我却想跟你朝朝暮暮岁岁年年。",
          "permission": "0",
          "imgList": [
            {
              "id": 1,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 2,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 3,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 4,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 5,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 6,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            }
          ],
          "likeList": [
            {
              "id": 1,
              "name": "光头勇"
            },
            {
              "id": 2,
              "name": "光头勇"
            },
            {
              "id": 3,
              "name": "光头勇"
            },
            {
              "id": 4,
              "name": "光头勇"
            },
            {
              "id": 5,
              "name": "光头勇"
            },
            {
              "id": 6,
              "name": "光头勇"
            }
          ],
          "commentList": [
            {
              "id": 1,
              "source": "强哥",
              "target": "伟哥",
              "content": "所以认真才显得格格不入，好像你只想跟我风花雪月过眼云烟，我却想跟你朝朝暮暮岁岁年年。"
            },
            {
              "id": 2,
              "source": "强哥",
              "target": "伟哥",
              "content": "所以认真才显得格格不入"
            },
            {
              "id": 3,
              "source": "强哥",
              "target": "伟哥",
              "content": "所以认真才显得格格不入"
            },
            {
              "id": 4,
              "source": "强哥",
              "target": "伟哥",
              "content": "所以认真才显得格格不入"
            }
          ]
        },
        {
          "id": 2,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "name": "光头勇",
          "content": "2023-02-23 17:23",
          "description": "所以认真才显得格格不入，好像你只想跟我风花雪月过眼云烟，我却想跟你朝朝暮暮岁岁年年。",
          "permission": "1",
          "imgList": [
            {
              "id": 1,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 2,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 3,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 4,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 5,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 6,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            }
          ],
          "likeList": [
            {
              "id": 1,
              "name": "光头勇"
            },
            {
              "id": 2,
              "name": "光头勇"
            },
            {
              "id": 3,
              "name": "光头勇"
            },
            {
              "id": 4,
              "name": "光头勇"
            },
            {
              "id": 5,
              "name": "光头勇"
            },
            {
              "id": 6,
              "name": "光头勇"
            }
          ],
          "commentList": [
            {
              "id": 1,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 2,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 3,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 4,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            }
          ]
        },
        {
          "id": 3,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "name": "光头勇",
          "content": "2023-02-23 17:23",
          "description": "所以认真才显得格格不入，好像你只想跟我风花雪月过眼云烟，我却想跟你朝朝暮暮岁岁年年。",
          "permission": "2",
          "imgList": [
            {
              "id": 1,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 2,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 3,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 4,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 5,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            },
            {
              "id": 6,
              "url": "https://img01.yzcdn.cn/vant/cat.jpeg"
            }
          ],
          "likeList": [
            {
              "id": 1,
              "name": "光头勇"
            },
            {
              "id": 2,
              "name": "光头勇"
            },
            {
              "id": 3,
              "name": "光头勇"
            },
            {
              "id": 4,
              "name": "光头勇"
            },
            {
              "id": 5,
              "name": "光头勇"
            },
            {
              "id": 6,
              "name": "光头勇"
            }
          ],
          "commentList": [
            {
              "id": 1,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 2,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 3,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            },
            {
              "id": 4,
              "source": "强哥",
              "target": "伟哥",
              "content": "阿伟，不要啦"
            }
          ]
        }
      ],
      isRefreshing: false,
      isLoading: false,
      isFinished: false,
      error: false
    };
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isRefreshing = false;
      }, 1000);
    },
    onLoad() {
      setTimeout(() => {
        this.isLoading = false;
        this.error = true;
      }, 1000);
    },
    handlePostComment(content, moment) {
      this.$toast('评论成功')
      console.log(content, moment)
    }
  }
}
</script>

<style scoped>
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>
