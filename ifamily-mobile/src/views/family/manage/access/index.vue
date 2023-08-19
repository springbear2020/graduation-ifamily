<template>
  <div>
    <van-nav-bar title="访问控制" left-arrow
                 @click-left="$router.replace('/family/manage')"
                 @click-right="$toast.success('修改成功')"
    >
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 访问权限单选 -->
    <van-radio-group v-model="whoCanSee">
      <van-cell title="家族成员" clickable @click="whoCanSee = '0'">
        <template #right-icon>
          <van-radio name="0"/>
        </template>
      </van-cell>
      <van-cell title="管理员" clickable @click="whoCanSee = '1'">
        <template #right-icon>
          <van-radio name="1"/>
        </template>
      </van-cell>
      <van-cell title="创建者" clickable @click="whoCanSee = '2'">
        <template #right-icon>
          <van-radio name="2"/>
        </template>
      </van-cell>
    </van-radio-group>

    <!-- 额外人员、不给谁看 -->
    <van-collapse v-model="activeName" accordion>
      <van-collapse-item title="额外人员" name="1">
        <van-search show-action v-model="uid" placeholder="UID" @search="$toast.success('额外人员')">
          <template #action>
            <div @click="$toast.success('额外人员')">搜索</div>
          </template>
        </van-search>
        <van-swipe-cell v-for="i in 3">
          <portrait-desc :person="person" class="van-hairline--top-bottom"/>
          <!-- 左滑删除 -->
          <template #right>
            <van-button square type="danger" text="删除" @click="$toast.fail('移除')"/>
          </template>
        </van-swipe-cell>
      </van-collapse-item>

      <van-collapse-item title="不给谁看" name="2">
        <van-search show-action v-model="uid" placeholder="UID" @search="$toast.success('不给谁看')">
          <template #action>
            <div @click="$toast.success('不给谁看')">搜索</div>
          </template>
        </van-search>
        <van-swipe-cell v-for="i in 5">
          <portrait-desc :person="person" class="van-hairline--top-bottom"/>
          <!-- 左滑删除 -->
          <template #right>
            <van-button square type="danger" text="删除" @click="$toast.fail('移除')"/>
          </template>
        </van-swipe-cell>
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      whoCanSee: '0',
      activeName: ['1'],
      uid: '',
      person: {
        portrait: 'https://img01.yzcdn.cn/vant/cat.jpeg',
        name: '光头勇',
        content: 'UID:05482352'
      },
    }
  }
}
</script>