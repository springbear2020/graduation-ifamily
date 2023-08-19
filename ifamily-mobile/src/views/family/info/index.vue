<template>
  <div>
    <van-nav-bar title="家族信息" left-arrow @click-left="$router.replace('/family')"
                 @click-right="$router.push('/family/info/form/1')">
      <template #right>
        <van-icon v-if="defaultGenealogy.id" name="edit" size="20"/>
      </template>
    </van-nav-bar>

    <div v-if="defaultGenealogy.id">
      <!-- 家族封面 -->
      <div class="flex-container">
        <van-image class="cover" width="100" height="100" :src="defaultGenealogy.cover"
                   @click="previewImage(defaultGenealogy.cover)"/>
      </div>

      <!-- 家族成员概况 -->
      <van-grid direction="horizontal" :border="false" :column-num="5">
        <van-grid-item>
          <desc-tag title="男" :content="defaultGenealogy.male" color="#007bff"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="女" :content="defaultGenealogy.female" color="#e83e8c"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="总" :content="defaultGenealogy.total" color="#fd7e14"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="生" :content="defaultGenealogy.alive" color="#28a745"/>
        </van-grid-item>
        <van-grid-item>
          <desc-tag title="逝" :content="defaultGenealogy.death" color="#6c757d"/>
        </van-grid-item>
      </van-grid>

      <!-- 名称、姓氏 -->
      <van-cell title="家族名称" :value="defaultGenealogy.title"/>
      <van-cell title="家族姓氏" :value="defaultGenealogy.surname"/>
      <!-- 地址、祖籍 -->
      <van-cell title="家族地址">
        <template #label>
          {{ defaultGenealogy.address }}
        </template>
      </van-cell>
      <van-cell title="家族祖籍">
        <template #label>
          {{ defaultGenealogy.ancestryAddress }}
        </template>
      </van-cell>

      <!-- 简介、字辈歌 -->
      <van-cell title="家族简介" name="1">
        <template #label>
          {{ defaultGenealogy.introduction }}
        </template>
      </van-cell>
      <van-cell title="字辈歌" name="2">
        <template #label>
          {{ defaultGenealogy.generationSong }}
        </template>
      </van-cell>

      <!-- 管理员、创建者 -->
      <van-cell title="管理员" name="3" v-if="defaultGenealogy.admins">
        <template #label>
          <people-tag v-for="admin in defaultGenealogy.admins" :key="admin.id"
                      :name="admin.name" :sex="admin.gender"/>
        </template>
      </van-cell>
      <van-cell title="创建者" v-if="defaultGenealogy.creator.id">
        <people-tag :name="defaultGenealogy.creator.name" :sex="defaultGenealogy.creator.gender"/>
      </van-cell>

      <!-- 创建时间 -->
      <van-cell title="创建时间" :value="defaultGenealogy.created"/>
    </div>

    <van-empty class="empty" v-else description="空空如也~"/>
  </div>
</template>

<script>
import {ImagePreview} from "vant";
import PeopleTag from '@/components/tag/people-tag'
import DescTag from '@/components/tag/desc-tag'

export default {
  name: "index",
  components: {PeopleTag, DescTag},
  computed: {
    defaultGenealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"]
    }
  },
  methods: {
    previewImage(url) {
      let images = []
      images.push(url)
      ImagePreview({images})
    },
  }
}
</script>

<style scoped>
.flex-container {
  display: flex;
}

.flex-container .cover {
  margin: auto auto;
  padding: 24px;
}

.empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}

/deep/ .van-tag {
  margin-right: 2px;
}
</style>
