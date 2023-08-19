<template>
  <div>
    <van-nav-bar title="修谱记录" left-arrow @click-left="$router.replace('/family')"/>

    <van-tabs v-model="active">
      <van-tab title="修谱日志">
        <van-list finished-text="没有更多了" :finished="revision.finished" v-model="revision.loading" @load="loadRevision">
          <van-steps direction="vertical" active-color="#1989fa">
            <van-step v-for="(item, index) in revision.list" :key="index">
              <h3>{{ item.operationDate }}</h3>
              <p v-for="(log, logIndex) in item.dateLogs" :key="logIndex">
                <span>{{ log.operator.name }}</span>&nbsp;
                <span :class="`operation-type-${log.operationType}`">{{ operationType(log.operationType) }}</span>&nbsp;
                {{ `${log.operatedPeopleList.join("、")}` }}
              </p>
            </van-step>
          </van-steps>
        </van-list>
      </van-tab>

      <van-tab title="访问记录">
        <van-list finished-text="没有更多了" :finished="visitor.finished" v-model="visitor.loading" @load="loadVisitor">
          <van-steps direction="vertical">
            <van-step v-for="(item, index) in visitor.list" :key="index">
              <h3>{{ item.visitedDate }}</h3>
              <div v-for="people in item.visitors" class="visitor-container right">
                <van-image round height="52" width="52" :src="people.portrait || defaultPortrait(people.gender)"/>
                <p class="plain-border">{{ people.name }}</p>
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
import {diffDate} from "@/utils/converter"
import {defaultPortrait} from "@/mixin/common-utils"

export default {
  name: "index",
  components: {SexTag},
  mixins: [defaultPortrait],
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
      this.$api.genealogy.revisionLogPageData(this.revision.formData).then(revisionList => {
        revisionList.forEach(item => {
          item.operationDate = diffDate(item.operationDate)
          this.revision.list.push(item)
        })
        this.revision.loading = false
        this.revision.formData.current += 1

        if (!revisionList || revisionList.length < this.revision.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.revision.finished = true
      })
    },
    loadVisitor() {
      this.$api.genealogy.visitorLogPageData(this.visitor.formData).then(visitorList => {
        visitorList.forEach(item => {
          item.visitedDate = diffDate(item.visitedDate)
          this.visitor.list.push(item)
        })
        this.visitor.loading = false
        this.visitor.formData.current += 1

        if (!visitorList || visitorList.length < this.visitor.formData.size) {
          this.finished = true
        }
      }).catch(() => {
        this.visitor.finished = true
      })
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
  color: #969799;
}

.visitor-container {
  display: inline-block;
  text-align: center;
}
</style>
