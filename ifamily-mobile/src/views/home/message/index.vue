<template>
  <div>
    <van-nav-bar title="消息" left-arrow @click-left="$router.replace('/home')" @click-right="$toast('清除未读消息')">
      <template #right>
        <van-icon name="sweep" color="#1989fa" class="iconfont" class-prefix="icon" size="20"/>
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="isLoading" success-text="刷新成功" @refresh="onRefresh">
      <div v-for="msg in messageList" :key="msg.id">
        <van-cell-group>
          <van-swipe-cell>
            <!-- 徽标、头像、标题、内容、时间 -->
            <van-cell center @click="$emit('view-msg')" class="flex-cell-container">
              <template #title>
                <!-- 徽标和头像 -->
                <van-badge v-if="msg.unread" :content="msg.unread">
                  <van-image round width="50" height="50" :src="msg.portrait"/>
                </van-badge>
                <van-image round width="50" height="50" :src="msg.portrait" v-else/>
                <!-- 标题和内容 -->
                <div class="portrait-title-container">
                  <p class="title">{{ msg.name }}</p>
                  <!-- FIXME too long message content -->
                  <p class="van-ellipsis">{{ msg.content }}</p>
                </div>
              </template>
              <!-- 时间 -->
              <template #default>
                <span>{{ msg.datetime }}</span>
              </template>
            </van-cell>

            <!-- 左滑置顶、已读、删除 -->
            <template #right>
              <van-button square type="primary" text="置顶" @click="$toast('消息置顶')"/>
              <van-button square type="warning" text="已读" @click="$toast('已读')"/>
              <van-button square type="danger" text="删除" @click="$toast('删除会话')"/>
            </template>
          </van-swipe-cell>
        </van-cell-group>
      </div>
    </van-pull-refresh>
  </div>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      isLoading: false,
      messageList: [
        {
          "id": 1,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "unread": 10,
          "name": "光头勇",
          "content": "自动省略多余的文本...",
          "datetime": "2023-02-23 18:55"
        },
        {
          "id": 2,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "unread": 0,
          "name": "光头勇",
          "content": "自动省略多余的文本...",
          "datetime": "2023-02-23 18:55"
        },
        {
          "id": 3,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "unread": 35,
          "name": "光头勇",
          "content": "自动省略多余的文本...",
          "datetime": "2023-02-23 18:55"
        },
        {
          "id": 4,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "unread": 76,
          "name": "光头勇",
          "content": "自动省略多余的文本...",
          "datetime": "2023-02-23 18:55"
        },
        {
          "id": 5,
          "portrait": "https://img01.yzcdn.cn/vant/cat.jpeg",
          "unread": 76,
          "name": "光头勇",
          "content": "自动省略多余的文本...",
          "datetime": "2023-02-23 18:55"
        }
      ]
    }
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    }
  }
}
</script>
