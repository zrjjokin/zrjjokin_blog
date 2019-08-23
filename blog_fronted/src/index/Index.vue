<template>
  <div class="main-wrapper">
    <router-view name="header"></router-view>
    <router-view name="content"></router-view>
    <router-view name="footer"></router-view>
  </div>
</template>

<script>
</script>

<style scoped>
  .main-wrapper{
      width: 1200px;      
      margin:0 auto;
  }
</style>
