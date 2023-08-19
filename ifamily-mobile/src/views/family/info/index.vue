<template>
  <div>
    <van-nav-bar title="家族信息" left-arrow @click-left="$router.replace('/family')"/>

    <!-- 家族封面 -->
    <div class="flex-container">
      <van-image class="cover" width="100" height="100" :src="genealogy.cover" @click="previewImage(genealogy.cover)"/>
    </div>

    <!-- 家族成员概况 -->
    <van-grid direction="horizontal" :border="false" :column-num="5">
      <van-grid-item>
        <desc-tag title="男" :content="genealogy.male" color="#007bff" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="女" :content="genealogy.female" color="#e83e8c" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="总" :content="genealogy.total" color="#fd7e14" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="生" :content="genealogy.alive" color="#28a745" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="逝" :content="genealogy.death" color="#6c757d" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
    </van-grid>

    <!-- 名称、姓氏 -->
    <van-cell-group>
      <van-cell title="家族名称" :value="genealogy.title" :border="false"/>
      <van-cell title="家族姓氏" :value="genealogy.surname"/>
    </van-cell-group>
    <!-- 地址、祖籍 -->
    <van-cell-group>
      <van-cell title="家族地址" :value="genealogy.address" :border="false"/>
      <van-cell title="家族祖籍" :value="genealogy.ancestryAddress"/>
    </van-cell-group>

    <!-- 简介、字辈歌 -->
    <van-collapse v-model="activeNames">
      <van-collapse-item title="家族简介" name="1" :border="false">{{ genealogy.introduction }}</van-collapse-item>
      <van-collapse-item :border="false" title="字辈歌" name="2">{{ genealogy.generationSong }}</van-collapse-item>
    </van-collapse>

    <!-- 管理员、创建者 -->
    <van-cell-group>
      <van-cell title="管理员" name="3" :border="false">
        <template #label>
          <people-tag name="光头勇" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" :sex="1" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" :sex="1" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" @click.native="$toast('查看管理员')"/>
          <people-tag name="光头勇" :sex="1" @click.native="$toast('查看管理员')"/>
        </template>
      </van-cell>
      <van-cell title="创建者" :border="false">
        <people-tag name="光头勇" @click.native="$toast('查看管理员')" :right="false" :bottom="false"/>
      </van-cell>
    </van-cell-group>

    <!-- 创建时间 -->
    <van-cell-group>
      <van-cell title="创建时间" :value="genealogy.created"/>
    </van-cell-group>
  </div>
</template>

<script>
import {ImagePreview} from "vant";
import PeopleTag from '@/components/tag/people-tag'
import DescTag from '@/components/tag/desc-tag'
import {genealogyList} from "@/mixin/genealogy-list";

export default {
  name: "index",
  components: {PeopleTag, DescTag},
  mixins: [genealogyList],
  computed: {
    genealogy() {
      return this.$store.getters["genealogy/defaultGenealogy"]
    }
  },
  data() {
    return {
      activeNames: ['1', '2']
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
</style>
