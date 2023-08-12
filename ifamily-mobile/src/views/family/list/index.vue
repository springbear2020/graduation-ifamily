<template>
  <div>
    <van-nav-bar title="我的家族" left-arrow @click-left="backFamily"/>

    <family-brief-info :defaultFamily="i === 1" v-for="i in 10" :key="i" @click.native="setDefaultFamily"/>
  </div>
</template>

<script>
import FamilyBriefInfo from "@/views/family/list/family-brief-info";

export default {
  name: "index",
  components: {FamilyBriefInfo},
  methods: {
    backFamily() {
      this.$router.replace('/family')
    },
    setDefaultFamily() {
      this.$router.replace('/family')
    }
  }
}
</script>