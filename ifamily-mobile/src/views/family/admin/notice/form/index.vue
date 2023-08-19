<template>
  <div>
    <van-nav-bar title="发布公告" left-arrow @click-left="$router.replace('/family/admin')" @click-right="handleSave">
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <van-field rows="10" type="textarea" maxlength="1000" show-word-limit placeholder="发布家族公告"
               v-model.trim="content" :autosize="true"/>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      content: undefined
    }
  },
  methods: {
    handleSave() {
      if (!this.content || this.content.length === 0) {
        this.$toast({message: '请填写家族公告', position: 'bottom'});
        return
      }

      this.$api.notice.saveNotice({content: this.content}).then(() => {
        this.$router.replace('/family/notice')
        this.$toast.success('发布成功')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    }
  }
}
</script>
