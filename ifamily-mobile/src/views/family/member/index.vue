<template>
  <div>
    <van-nav-bar title="家族成员" left-arrow @click-left="$router.replace('/family')" @click-right="showPopover = true">
      <template #right>
        <van-popover v-if="generations" placement="bottom-end" trigger="click"
                     v-model="showPopover" :actions="actions" @select="onSelect">
          <template #reference>
            <van-icon name="filter-o" size="20"/>
          </template>
        </van-popover>
      </template>
    </van-nav-bar>

    <!-- 搜索框与搜索条件面包屑 -->
    <form v-if="generations">
      <van-search shape="round" placeholder="请输入家族人员姓名" clearable
                  @cancel="formData.name = undefined" v-model.trim="formData.name"
      />
      <div class="tags-container">
        <van-tag closeable size="large" round color="#007bff" v-if="formData.gender === 0"
                 @close="formData.gender = undefined">男
        </van-tag>
        <van-tag closeable size="large" round color="#e83e8c" v-if="formData.gender === 1"
                 @close="formData.gender = undefined">女
        </van-tag>
        <van-tag closeable size="large" round color="#28a745" v-if="formData.alive === true"
                 @close="formData.alive = undefined">生
        </van-tag>
        <van-tag closeable size="large" round color="#6c757d" v-if="formData.alive === false"
                 @close="formData.alive = undefined">逝
        </van-tag>
      </div>
    </form>

    <van-index-bar :index-list="generations">
      <div v-for="generation in generations" :key="generation">
        <!-- 世代 -->
        <van-index-anchor :index="generation">{{ chineseGeneration(generation) }}</van-index-anchor>
        <!-- 世代成员列表 -->
        <van-cell is-link center v-for="people in memberMap[generation]" :key="people.id"
                  @click="$router.push(`/family/member/info/1?pid=${people.id}`)"
        >
          <template #title>
            <van-image round width="52" height="52" :src="people.portrait || defaultPortrait(people.gender)"/>
            <p>
              <span>{{ people.name }}</span>
              <sex-tag :sex="people.gender"/>
            </p>
          </template>
          <template #default>
            <van-tag color="#6c757d" v-if="people.deathDate">已逝</van-tag>
            <van-tag color="#28a745" v-else>健在</van-tag>
          </template>
        </van-cell>
      </div>
    </van-index-bar>

    <van-empty class="empty" v-if="!generations" description="空空如也~"/>
  </div>
</template>

<script>
import SexTag from '@/components/tag/sex-tag'
import {numToChinese} from "@/utils/converter";

export default {
  name: "index",
  components: {SexTag},
  data() {
    return {
      // 右上角气泡弹出框
      showPopover: false,
      actions: [
        {text: '全部成员',},
        {text: '男性成员',},
        {text: '女性成员',},
        {text: '健在成员',},
        {text: '已逝成员',},
      ],
      formData: {
        name: undefined,
        gender: undefined,
        alive: undefined
      }
    }
  },
  computed: {
    generations() {
      return this.$store.state.genealogy.memberMap.generations
    },
    memberMap() {
      return this.$store.state.genealogy.memberMap.members
    }
  },
  watch: {
    formData: {
      immediate: true,
      deep: true,
      handler() {
        this.getGenerationsMember()
      }
    }
  },
  methods: {
    getGenerationsMember() {
      this.$store.dispatch('genealogy/generationMembers', this.formData).then(() => {
      }).catch(err => {
        this.$toast({message: err.data || err.desc, position: 'bottom'})
      })
    },
    defaultPortrait(gender) {
      // 根据性别决定展示的默认肖像
      return gender === 0 ? 'img/male.jpg' : 'img/female.jpg'
    },
    chineseGeneration(generation) {
      return "第" + numToChinese(generation) + "世";
    },
    onSelect(action, index) {
      switch (index) {
        case 0:
          // [0]全部成员
          this.formData.name = undefined
          this.formData.gender = undefined
          this.formData.alive = undefined
          break;
        case 1:
          // [1]男性成员
          this.formData.gender = 0
          break;
        case 2:
          // [2]女性成员
          this.formData.gender = 1
          break;
        case 3:
          // [3]健在成员
          this.formData.alive = true
          break;
        case 4:
          // [4]已逝成员
          this.formData.alive = false
          break;
        default:
      }
    },
  }
}
</script>

<style scoped>
/deep/ .van-cell__title {
  display: flex;
}

.van-cell__title p {
  margin-left: 8px;
}

.tags-container {
  text-align: center;
  background-color: #ffffff;
  padding: 0 16px 8px 16px;
}

.tags-container .van-tag {
  margin-right: 8px;
}

.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
</style>
