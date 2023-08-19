<template>
  <div>
    <van-nav-bar title="访问控制" left-arrow
                 @click-left="$router.replace('/family/manage')"
                 @click-right="$toast('修改成功')"
    >
      <template #right>
        <van-icon name="passed" size="20"/>
      </template>
    </van-nav-bar>

    <!-- 访问权限单选 -->
    <van-radio-group v-model="whoCanSee">
      <van-cell title="家族成员" clickable @click="whoCanSee = '0'" :border="false">
        <template #right-icon>
          <van-radio name="0"/>
        </template>
      </van-cell>
      <van-cell title="管理员" clickable @click="whoCanSee = '1'" :border="false">
        <template #right-icon>
          <van-radio name="1"/>
        </template>
      </van-cell>
      <van-cell title="创建者" clickable @click="whoCanSee = '2'" :border="false">
        <template #right-icon>
          <van-radio name="2"/>
        </template>
      </van-cell>
    </van-radio-group>

    <!-- 额外人员、 -->
    <van-collapse v-model="activeName" accordion>
      <van-collapse-item title="额外人员" name="1">
        <search-people-list/>
      </van-collapse-item>
      <!-- 不给谁看 -->
      <van-collapse-item title="不给谁看" name="2">
        <search-people-list/>
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
import SearchPeopleList from "@/components/business/search-people-list";

export default {
  name: "index",
  components: {SearchPeopleList},
  data() {
    return {
      whoCanSee: '0',
      activeName: ['1'],
    }
  }
}
</script>
