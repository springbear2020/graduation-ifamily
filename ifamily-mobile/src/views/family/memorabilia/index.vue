<template>
  <div>
    <van-nav-bar title="家族大事" left-arrow @click-left="$router.replace('/family')"/>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadMemorabiliaList">
      <van-steps direction="vertical" active-icon="calendar-o" inactive-icon="calendar-o" active-color="#969799" center>
        <van-step v-for="item in list" :key="item.id">
          <h1 class="year">· {{ item.occurredYear }} ·</h1>
          <h2 class="title">{{ item.title }}</h2>
          <van-image :src="item.cover"/>
          <div class="top line-wrap" v-html="item.content"/>
        </van-step>
      </van-steps>
    </van-list>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      finished: false,
      loading: false,
      formData: {
        current: 1,
        size: 10,
      },
      list: []
    }
  },
  methods: {
    loadMemorabiliaList() {
      this.$api.genealogy.memorabiliaPageData(this.formData).then(memorabiliaList => {
        memorabiliaList.forEach(item => {
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1

        if (!memorabiliaList || memorabiliaList.length < this.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.finished = true
      })
    }
  }
}
</script>

<style scoped>
h1:after {
  background-color: #ebedf0;
  content: '';
  display: block;
  height: 1px;
  margin: 1.5rem auto 0.75rem;
  width: 80px;
}

.year {
  text-align: center;
  color: #969799;
}

.title {
  text-align: center;
  font-style: italic;
  line-height: 1.5;
}
</style>
