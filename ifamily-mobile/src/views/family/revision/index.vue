<template>
  <div>
    <van-nav-bar title="修谱记录" left-arrow @click-left="$router.replace('/family')"/>

    <van-tabs v-model="active">
      <!-- 修谱日志 -->
      <van-tab title="修谱日志">
        <van-list finished-text="没有更多了" :finished="revision.finished" v-model="revision.loading" @load="loadRevision">
          <van-steps direction="vertical" active-color="#1989fa">
            <van-step v-for="(item, index) in revision.list" :key="index">
              <h3>{{ formatDate(item.operationDate) }}</h3>
              <p v-for="(log, logIndex) in item.dateLogs" :key="logIndex">
                <span class="operator">{{ log.operator.name }}</span>&nbsp;
                <span :class="`operation-type-${log.operationType}`">{{ operationType(log.operationType) }}</span>&nbsp;
                {{ `${log.operatedPeopleList.join("、")}` }}
              </p>
            </van-step>
          </van-steps>
        </van-list>
      </van-tab>

      <!-- 访问记录 -->
      <van-tab title="访问记录">
        <van-list finished-text="没有更多了" :finished="visitor.finished" v-model="visitor.loading" @load="loadVisitor">
          <van-steps direction="vertical">
            <van-step v-for="(item, index) in visitor.list" :key="index">
              <h3>{{ formatDate(item.visitedDate) }}</h3>
              <div v-for="people in item.visitors" class="visitor-container">
                <van-image round height="50" width="50" :src="people.portrait || defaultPortrait(people.gender)"/>
                <p>{{ people.name }}</p>
              </div>
            </van-step>
          </van-steps>
        </van-list>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import {weekDate} from "@/utils/converter";

export default {
  name: "index",
  components: {SexTag},
  data() {
    return {
      active: 0,
      revision: {
        finished: false,
        loading: false,
        formData: {
          current: 1,
          size: 5,
        },
        list: []
      },
      visitor: {
        finished: false,
        loading: false,
        formData: {
          current: 1,
          size: 10,
        },
        list: []
      }
    }
  },
  methods: {
    loadRevision() {
      this.$api.record.listRevisionLog(this.revision.formData).then(revisionList => {
        revisionList.forEach(item => {
          this.revision.list.push(item)
        })
        this.revision.loading = false
        this.revision.formData.current += 1
      }).catch(() => {
        this.revision.finished = true
      })
    },
    loadVisitor() {
      this.$api.record.listVisitorLog(this.visitor.formData).then(visitorList => {
        visitorList.forEach(item => {
          this.visitor.list.push(item)
        })
        this.visitor.loading = false
        this.visitor.formData.current += 1
      }).catch(() => {
        this.visitor.finished = true
      })
    },
    defaultPortrait(gender) {
      // 根据性别决定展示的默认肖像
      return gender === 0 ? 'img/male.jpg' : 'img/female.jpg'
    },
    operationType(type) {
      switch (type) {
        case 1:
          return '新增';
        case 2:
          return '删除';
        case 3:
          return '编辑';
        case 4:
          return '查看';
        default:
          return ''
      }
    },
    formatDate(dateStr) {
      return weekDate(dateStr)
    }
  }
}
</script>

<style scoped>
.van-step__title h3 {
  margin: 0 0 8px 0;
}

.van-step__title p {
  margin: 8px 0;
}

.visitor-container {
  display: inline-block;
  text-align: center;
}

.visitor-container .van-image {
  padding: 2px;
}

.operator {
  font-weight: bold;
}

.operation-type-1 {
  color: #07c160;
}

.operation-type-2 {
  color: #ee0a24;
}

.operation-type-3 {
  color: #ff976a;
}

.operation-type-4 {
  color: #323233;
}
</style>
