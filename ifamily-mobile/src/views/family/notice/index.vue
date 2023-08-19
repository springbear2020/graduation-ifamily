<template>
  <div>
    <van-nav-bar title="家族公告" left-arrow @click-left="$router.replace('/family')"
                 @click-right="content = undefined; showActionSheet = true; type = 1">
      <template #right>
        <van-icon name="guide-o" size="20"/>
      </template>
    </van-nav-bar>

    <van-list finished-text="没有更多了" :finished="finished" v-model="loading" @load="loadNotices">
      <div v-for="notice in list" :key="notice.id" class="van-hairline--bottom">
        <portrait-desc :person="notice.announcer" @moreOperation="moreOperation($event, notice)"/>
        <van-cell class="content-top">
          {{ notice.content }}
        </van-cell>
      </div>
    </van-list>

    <!-- 家族公告发布面板 -->
    <van-action-sheet v-model="showActionSheet" :title="title">
      <van-form @submit="saveOrUpdate">
        <van-field rows="10" type="textarea" maxlength="1000" :placeholder="title" show-word-limit
                   v-model.trim="content" :autosize="true" :rules="[{ required: true, message: '请填写家族公告' }]"/>
        <van-button type="info" block>发布</van-button>
      </van-form>
    </van-action-sheet>
  </div>
</template>

<script>
import PortraitDesc from '@/components/basis/portrait-desc';
import {momentDate} from "@/utils/converter";

export default {
  name: "index",
  components: {PortraitDesc},
  data() {
    return {
      // 分页数据
      finished: false,
      loading: false,
      formData: {
        current: 1,
        size: 10,
      },
      list: [],
      // 操作类型 [1]新增 [2]编辑
      type: 1,
      showActionSheet: false,
      // 公告内容
      id: undefined,
      content: undefined,
    }
  },
  computed: {
    title() {
      return this.type === 1 ? '发布公告' : '编辑公告'
    }
  },
  methods: {
    saveOrUpdate() {
      if (this.type === 1) {
        // 新增公告
        this.handleSave()
      } else if (this.type === 2) {
        // 编辑公告
        this.handleEdit()
      }
    },
    loadNotices() {
      this.$api.notice.noticesPageData(this.formData).then(notices => {
        notices.forEach(item => {
          // 将发布时间追加到发布者对象中
          item.announcer.content = momentDate(item.created)
          this.list.push(item)
        })
        this.loading = false
        this.formData.current += 1
      }).catch(() => {
        this.finished = true
      })
    },
    handleSave() {
      // FIXME 当 tomcat 版本大于 9 时，key=val&key=val 的内容中不能包含 [] 的特殊字符的问题
      this.$api.notice.save({content: this.content}).then(() => {
        this.showActionSheet = false
        this.$toast.success('发布成功\n审核后展示')
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    handleEdit() {
      this.$api.notice.edit({id: this.id, content: this.content}).then(() => {
        this.showActionSheet = false
        this.$toast.success('编辑成功\n审核后展示')
        this.noticeListReduce()
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    handleRemove(notice) {
      this.$dialog.confirm({
        title: '删除公告',
        message: `您确定要删除《${notice.announcer.name}》于${notice.announcer.content} 发布的家族公告吗？`,
      }).then(() => {
        this.$api.notice.remove({id: this.id}).then(() => {
          this.$toast.success('删除成功')
          this.noticeListReduce()
        }).catch(err => {
          this.$toast({message: err.data || err.desc, position: 'bottom'})
        })
      }).catch(() => {
      });
    },
    moreOperation(action, notice) {
      this.id = notice.id
      if ('编辑' === action.text) {
        this.type = 2
        this.content = notice.content
        this.showActionSheet = true
      } else if ('删除' === action.text) {
        this.handleRemove(notice)
      }
    },
    noticeListReduce() {
      this.list = this.list.filter(notice => notice.id !== this.id)
    }
  }
}
</script>

<style scoped>
.content-top {
  padding-top: 0;
}
</style>
