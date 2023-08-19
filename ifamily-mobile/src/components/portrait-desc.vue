<template>
  <!-- 头像、标题、内容、更多 -->
  <van-cell :border="false" center class="flex-cell-title">
    <template #title>
      <!-- 头像 -->
      <van-image round width="50" height="50" :src="person.portrait"/>
      <!-- 标题、内容 -->
      <div class="portrait-title-container">
        <p class="title">{{ person.name }}</p>
        <p class="van-ellipsis">{{ person.content }}</p>
      </div>
    </template>
    <!-- 更多 -->
    <template #right-icon>
      <van-popover placement="bottom-end" trigger="click"
                   v-model="showPopover" :actions="actions" @select="(action)=>$toast(action.text)">
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
        {text: '删除', icon: 'delete-o'},
        {text: '举报', icon: 'warn-o'},
      ],
    }
  },
}
</script>

