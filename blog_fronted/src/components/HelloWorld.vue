<template>
  <div class='panel'>
    <div class='title'>
      <slot name="title"></slot>
    </div>
    <div class='content'>
      <slot name="content"></slot>
    </div>
    <div class='footer'>
      <slot name="footer">更多信息</slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.panel{
  border: 1px solid;
  margin-bottom: 15px;
  max-width: 300px;
  
}
.panel .title{
  border-bottom: 1px solid;
  height: 100px;
}
.panel .content{
  border-bottom: 1px solid;
  height:100px;
}
</style>
