<template>
  <div>
    <van-nav-bar fixed title="家族树谱" left-arrow @click-left="backFamily" @click-right="foldAll = !foldAll">
      <template #right>
        <van-icon name="expand-o" v-if="foldAll"/>
        <van-icon name="shrink" v-else/>
      </template>
    </van-nav-bar>

    <FamilyTree :jsonData="jsonData" @click-node="clickNode"/>
  </div>
</template>

<script>
import FamilyTree from '@/views/family/tree/family-tree';
import stone from '@/assets/ADreamInRedMansions.json'

export default {
  name: "index",
  components: {FamilyTree},
  data() {
    return {
      jsonData: stone,
      foldAll: true
    }
  },
  watch: {
    foldAll: {
      immediate: true,
      handler(newVal) {
        let extendKey = function (jsonData) {
          jsonData.extend = !newVal;
          if (Array.isArray(jsonData.children)) {
            jsonData.children.forEach(c => {
              extendKey(c)
            })
          }
          return jsonData;
        }
        extendKey(this.jsonData)
      }
    }
  },
  methods: {
    clickNode(node) {
      console.log(node)
    },
    backFamily() {
      this.$router.replace('/family')
    },
  }
}
</script>