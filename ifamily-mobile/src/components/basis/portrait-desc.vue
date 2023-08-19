<template>
  <van-cell center class="flex-cell-container" :border="false">
    <!-- 头像、标题、内容 -->
    <template #title>
      <van-image round width="50" height="50" :src="person.portrait"/>
      <div class="portrait-title-container">
        <p >{{ person.name }}</p>
        <p class="content">{{ person.content }}</p>
      </div>
    </template>

    <!-- 更多 -->
    <template #right-icon>
      <van-popover placement="bottom-end" trigger="click"
                   v-model="showPopover" :actions="actions" @select="action => $emit('moreOperation', action)">
        <template #reference>
          <van-icon name="ellipsis" size="20" v-show="more" @click.stop="showPopover = true"/>
        </template>
      </van-popover>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "portrait-desc",
  props: {
    person: {
      type: Object,
      required: true
    },
    more: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      showPopover: false,
      actions: [
        {text: '编辑', icon: 'edit'},
        {text: '删除', icon: 'delete-o'}
      ],
    }
  },
}
</script>

<style scoped>
.flex-cell-container .van-cell__title {
  display: flex;
}

.portrait-title-container {
  margin-left: 8px;
}

.portrait-title-container p {
  margin: 0 0;
}

.portrait-title-container .content {
  color: #969799;
  font-size: 12px;
}
</style>
