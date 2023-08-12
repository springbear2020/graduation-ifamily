<template>
  <div class="footer-span"></div>
</template>

<script>
export default {
  name: "footer-tab-span"
}
</script>

<style scoped>
.footer-span {
  padding-bottom: calc(50px + env(safe-area-inset-bottom));
}
</style>