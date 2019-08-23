<template>
<el-row>
  <el-col :span="24" >

  
  <div class="simple-header">
    <transition name="fade" style="position:fixed">
      <div id="header" v-show="showHeader">
        <router-link id="logo" to="/index">
          <img
            src="../assets/log1.png"
            style="display:inline-block;width:40px;height:100%;border-radius:20px;"
          />
          <span class="title">ZrjJokin's Blog</span>
          &nbsp;&nbsp;&nbsp;
          <span class="motto">Take a sad song and make it better</span>
        </router-link>
        <ul id="nav" style="display:block;list-style-type:none;line-height:60px;">
          <li>
            <form action="#" id="search-form">
              <el-input
                class="search-input"
                v-model="keywords"
                suffix-icon="el-icon-search"
                @keyup.enter="submit"
              ></el-input>
            </form>
          </li>

          <li class="list">
            <a href="/#/index" style="color:darkgray;font-size:20px;">文章</a>
          </li>
          <li class="list">
            <a href="/#/timeline" style="color:darkgray;font-size:20px;">时间轴</a>
          </li>
          <li class="list">
            <a :href="addBlog" style="color:darkgray;font-size:20px;">写博客</a>
          </li>
          <li class="list">
            <a href="https://github.com/zrjjokin" style="color:darkgray;font-size:20px;">关于<span style="font-size:15px;">{{user.username}}</span></a>
          </li>
        </ul>
      </div>
    </transition>
  </div>
  </el-col>
  </el-row>
</template>

<script type="text/ecmascript-6">
import {mapGetters} from "vuex"
export default {
  data() {
    return {
      addBlog:'#'
     
    };
  },
  created() {
    //this.keywords = this.$route.query.keywords;
    //如果已经登录
    if(this.user.id!=''){
      this.addBlog = '/#/addBlog';
    }
  },
  computed:{
    ...mapGetters(['showHeader','user']),
    keywords:{
      get(){
        return  this.$store.state.keywords;
      },
      set(value){
        this.$store.commit('setKeyWords',value);
      }
    },
    
  },
  mounted: function() {
    // 给页面绑定滑轮滚动事件
    if (document.addEventListener) {
      // firefox
      document.addEventListener("DOMMouseScroll", this.watchScroll, false);
    }
    // 滚动滑轮触发scrollFunc方法  //ie 谷歌
    window.onmousewheel = document.onmousewheel = this.watchScroll;
  },
  methods: {
    watchScroll(e) {
      //console.log(e);
      console.log(this.$store.state.showHeader);
      e = e || window.event;
      if (e.wheelDelta) {
        if (e.wheelDelta > 0 && this.showHeader === false) {
          // 当滑轮向上滚动
          this.$store.commit('updateShowHeader');
        }
        if (e.wheelDelta < 0 && this.showHeader === true) {
          // 当滑轮向下滚动
          this.$store.commit('updateShowHeader');
        }
      } else if (e.detail) {
        if (e.detail < 0 && this.showHeader === false) {
          // 当滑轮向上滚动
          this.$store.commit('updateShowHeader');
        }
        if (e.detail > 0 && this.showHeader === true) {
          // 当滑轮向下滚动
          this.$store.commit('updateShowHeader');
        }
      }
    }
  }
};
</script>

<style scoped>
*{
    margin:0;
    padding: 0;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

.simple-header{    
    border-bottom: 1px solid darkgray;
    overflow: hidden;
    height:70px;
}
#search-form{
    height: 45px;
}
.search-input{
    height: 100%;
    line-height: 100%;
    display: inline-block;
    vertical-align: middle;  
    border-radius: 25px;
}
li{
    float:left;
    
}
#header{
    position:fixed;
    height: 60px;
    width: 100%;
    background-color: aliceblue;
    top:0;
    z-index: 102;
}
a{
    text-decoration: none;
}
.title {
    font-family: 'Times New Roman', Times, serif;
    font-size: 25px;
    color:black;
}
.motto{
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    color:skyblue;
}
#logo{
    float:left;
    
    padding: 2px;
    vertical-align: center;
    margin-right: 100px;
}
.list{
    width:100px;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color:darkgray
}
</style>

