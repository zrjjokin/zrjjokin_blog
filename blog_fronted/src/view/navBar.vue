<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    @select="handleSelect"
  >
    <el-menu-item index="show">首页</el-menu-item>
    <el-menu-item index="add">添加博客</el-menu-item>
  </el-menu>
</template>
<script>
export default {
  name:"nav-bar",
  data() {
    return {
      activeIndex: "show",
    };
  },
  mounted(){
    console.log("mounted")
    this.$router.push('/'+this.activeIndex)
  },
  methods: {
    handleSelect(key, keyPath) {
      
      this.$router.push('/'+key)
    }
  }
};
</script>
