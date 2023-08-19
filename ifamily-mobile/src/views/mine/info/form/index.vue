<template>
  <div>
    <van-nav-bar left-arrow :title="title" @click-left="$router.push('/mine/info')" @click-right="handleUpdate">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-form>
      <van-field
          rows="1" autosize type="textarea" maxlength="30" show-word-limit
          @blur="error = content.length > 0 ? '' : `${title}不能为空，请重新输入`"
          v-model="content" :label="title" :placeholder="title"
      />
      <div class="van-field__error-message">{{ error }}</div>
    </van-form>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: "index",
  data() {
    return {
      content: '',
      // [0]编辑昵称 [1]编辑签名
      type: undefined,
      error: ''
    }
  },
  mounted() {
    let type = this.$route.params.type
    if (!(type === '0' || type === '1')) {
      type = '0'
    }
    this.type = type
    this.content = type === '0' ? this.user.nickname : this.user.signature
  },
  computed: {
    title() {
      return this.type === '0' ? '昵称' : '个性签名'
    },
    ...mapState({
      user: state => {
        return state.user.user ? state.user.user : {}
      }
    })
  },
  methods: {
    handleUpdate() {
      // 空内容
      if (this.content.length <= 0) {
        this.error = `${this.title}不能为空，请重新输入`
        return;
      } else {
        this.error = ''
      }

      // [1]用户昵称 [2]个性签名 [3]头像地址
      let content = ''
      let type = 0;
      if (this.type === '0') {
        type = 1
        content = this.user.nickname
      } else if (this.type === '1') {
        type = 2
        content = this.user.signature
      } else {
        return
      }

      // 内容是否发生修改
      if (this.content === content) {
        this.error = `${this.title}未修改`
        return;
      } else {
        this.error = ''
      }

      this.$api.user.updateUserProfile(this.content, type).then(() => {
        this.$toast.success('更新成功')
        // 移除用户信息，查询最新的用户信息
        this.$store.dispatch('user/getUser')
      }).catch((err) => {
        this.error = err.data || err.desc
      });
    },
  }
}
</script>

<style scoped>
.van-field__error-message {
  padding: 10px 16px;
}
</style>
