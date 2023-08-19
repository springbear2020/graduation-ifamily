<template>
  <div>
    <van-nav-bar title="发布公告" left-arrow @click-left="$router.replace('/family/admin')" @click-right="handleSave">
      <template #right>
        <van-button type="info" size="mini" class="nav-button">发布</van-button>
      </template>
    </van-nav-bar>

    <van-field rows="10" type="textarea" maxlength="1000" show-word-limit placeholder="发布家族公告"
               v-model.trim="content" :autosize="true"
    />
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

      this.$api.genealogy.saveNotice({content: this.content}).then(() => {
        this.$router.replace('/family/notice')
        this.$toast.success('发布成功')
      }).catch(msg => {
        this.$toast({message: msg, position: 'bottom'})
      })
    }
  }
}
</script>
