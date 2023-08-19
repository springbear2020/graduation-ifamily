<template>
  <div>
    <van-nav-bar title="家族信息" left-arrow @click-left="$router.replace('/family')"/>

    <!-- 家族照片 -->
    <div class="flex-container">
      <van-image class="cover" width="100" height="100" :src="url" @click="previewImage"/>
    </div>

    <!-- 家族成员概况 -->
    <van-grid direction="horizontal" :border="false" :column-num="5">
      <van-grid-item>
        <desc-tag title="男" content="323" color="#007bff" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="女" content="200" color="#e83e8c" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="总" content="523" color="#fd7e14" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="生" content="423" color="#28a745" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
      <van-grid-item>
        <desc-tag title="逝" content="100" color="#6c757d" @click.native="$toast('查看家族成员')"/>
      </van-grid-item>
    </van-grid>

    <!-- 名称、姓氏 -->
    <van-cell-group>
      <van-cell title="家族名称" value="相亲相爱" :border="false"/>
      <van-cell title="家族姓氏" value="李"/>
    </van-cell-group>
    <!-- 地址、祖籍 -->
    <van-cell-group>
      <van-cell title="家族地址" value="湖北省/武汉市/洪山区" :border="false"/>
      <van-cell title="家族祖籍" value="湖北省/武汉市/洪山区"/>
    </van-cell-group>
    <!-- 简介、字辈歌 -->
    <van-collapse v-model="activeNames">
      <van-collapse-item title="家族简介" name="1" :border="false">
        众所周知，字辈是中国传承千年的重要起名形式，像老一辈很多人名字都是按家谱起的，父姓+中间的家谱，家长起好最后一个字就好了，这样的起名方式挺简单省事的
      </van-collapse-item>
      <van-collapse-item :border="false" title="字辈歌" name="2">万代荣禄传后名，<br/>万代荣禄传后名，<br/>万代荣禄传后名，<br/>万代荣禄传后名。
      </van-collapse-item>
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
      <van-cell title="创建时间" value="2023-02-12 16:02"/>
    </van-cell-group>
  </div>
</template>

<script>
import {ImagePreview} from "vant";
import PeopleTag from '@/components/tag/people-tag'
import DescTag from '@/components/tag/desc-tag'

export default {
  name: "index",
  components: {PeopleTag, DescTag},
  data() {
    return {
      activeNames: ['1', '2'],
      url: 'https://img01.yzcdn.cn/vant/cat.jpeg',
    }
  },
  methods: {
    previewImage() {
      let images = []
      images.push(this.url)
      ImagePreview({images})
    },
  }
}
</script>
