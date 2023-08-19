<template>
  <div>
    <van-nav-bar title="发布动态" left-arrow @click-left="$router.replace('/mine/moments')" @click-right="$toast('发布成功')">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-field placeholder="分享新鲜事..." type="textarea" maxlength="1000" rows="5" autofocus show-word-limit
               v-model="content"/>

    <van-uploader v-model="fileList" :multiple="true"/>

    <van-cell-group>
      <van-cell title="权限设置" icon="eye-o" is-link :value="actions[selectedIndex].name"
                @click="showPermissionSheet = true"
      />
      <van-cell title="定时发表" icon="clock-o" is-link @click="showDatetimePicker = true" :value="datetimeStr"/>
    </van-cell-group>

    <!-- 权限设置动作面板 -->
    <van-action-sheet v-model="showPermissionSheet" :actions="actions" cancel-text="取消" close-on-click-action
                      @select="(actions, index) => {this.selectedIndex = index}"/>
    <!-- 定时发表动作面板 -->
    <van-action-sheet v-model="showDatetimePicker">
      <van-datetime-picker type="datetime" title="选择年月日小时分钟"
                           :min-date="minDate" :max-date="maxDate" @cancel="showDatetimePicker = false"
                           @confirm="confirmDate"/>
    </van-action-sheet>
  </div>
</template>

<script>
import dayjs from "dayjs";

export default {
  name: "index",
  data() {
    return {
      content: '',
      fileList: [],
      showPermissionSheet: false,
      actions: [{name: '家族成员可见'}, {name: '家庭成员可见'}, {name: '仅自己可见'}],
      selectedIndex: 0,
      minDate: new Date(),
      maxDate: dayjs(this.minDate).add(7, 'day').$d,
      showDatetimePicker: false,
      datetimeStr: ''
    }
  },
  methods: {
    confirmDate(newDate) {
      this.datetimeStr = dayjs(newDate).format('YYYY-MM-DD HH:mm').toString()
      this.showDatetimePicker = false
    },
  }
}
</script>

<style scoped>
.van-uploader {
  padding: 10px;
}
</style>