<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow
                 @click-left="$router.replace('/family')"
                 @click-right="$router.push('/family/manage/form/0')"
    >
      <template #right>
        <van-icon name="add-o" size="20"/>
      </template>
    </van-nav-bar>

    <family-info-card v-for="i in 10" :key="i" :me-created="i === 3 || i ===1 " :default-family="i === 1"
                      @click.native="setDefaultFamily"
    />
  </div>
</template>

<script>
import FamilyInfoCard from "@/components/business/family-info-card";

export default {
  name: "index",
  components: {FamilyInfoCard},
  methods: {
    setDefaultFamily() {
      this.$toast('设为默认家庭')
      this.$router.replace('/family')
    }
  }
}
</script>
