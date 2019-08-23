<template>
<div>

    <el-row>
        <el-col :span="18">
    <div class="block">
        <h3 class="year" style="text-align:left">2019
            <el-divider direction="vertical" style="width:2px;background-color:dark"></el-divider>
        共{{sumBlogs}}篇
        </h3>
        <el-timeline >
            <el-timeline-item  size="large"  v-for="(monthvalue,monthkey) in blogLists" v-if="monthvalue.length>0" :key="monthkey"  placement="top" style="text-align:left;">
                <div style="font-size:20px;"><i class="el-icon-date"></i>{{monthkey+1}}月
                <el-divider direction="vertical" style="width:2px;background-color:dark"></el-divider>
                    共{{monthvalue.length}}篇
                </div>
                <el-card v-for="(dayvalue,daykey) in monthvalue" :key="daykey" style="width:80%;color:#000;">
                    <a :href="'/article/'+dayvalue.blogId">
                        <div style="padding:2px;margin:0">
                        <el-tag type="info" style="height:28px;">{{dayvalue.date}}</el-tag>
                        <span>{{dayvalue.title}}</span>
                    
                        </div>
                    </a>
                    
                    
                    
                </el-card>
            </el-timeline-item>
        </el-timeline>
        
    </div>
</el-col>
<el-col :span="6">
  
<hot-blog></hot-blog>
<tag-wall></tag-wall>
</el-col>
</el-row>

</div>
</template>

<script>
import HotBlog from '../view/HotBlog'
import TagWall from '../view/TagWall'
export default {
    components:{
        "hot-blog":HotBlog,
        "tag-wall":TagWall
    },
    data(){
        return {
            blogLists:[[],[],[],[],[],[],[],[],[],[],[],[]],
            sumBlogs:0,
            value:new Date()
        }
    },
    created(){
        this.initData();
    },
    mounted () {
  window.addEventListener('scroll', this.scrollToTop)
},
destroyed () {
  window.removeEventListener('scroll', this.scrollToTop)
},

    computed:{

    },
    methods:{
        timestampToTime(timestamp){
      var date=new Date(timestamp);
      var Y=date.getFullYear()+"-";
      var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
      var D = date.getDate() ;
      return Y+M+D;
    },
        initData(){
            var _this=this;
            this.$http.get(
                '/api/blogList'
            ).then(
        resp=>{
          //console.log(resp);
          var blogLists= resp.data;
            _this.sumBlogs = blogLists.length;
          //数据预处理
          for( var i=0; i<blogLists.length;i++){
            
            
            blogLists[i].date = this.timestampToTime(blogLists[i].date);
            var date = new Date(blogLists[i].date);
            var month = date.getMonth();
            //console.log(month);
            _this.blogLists[month].push(blogLists[i]);
            
            
            //_this.blogLists[].push(blogLists[i]);  
          }
          console.log(_this.blogLists);
        }
      )
        },
        backTop () {
      const that = this
      let timer = setInterval(() => {
        let ispeed = Math.floor(-that.scrollTop / 5)
        document.documentElement.scrollTop = document.body.scrollTop = that.scrollTop + ispeed
        if (that.scrollTop === 0) {
          clearInterval(timer)
        }
      }, 16)
  },

  // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
  scrollToTop () {
    const that = this
    let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
    that.scrollTop = scrollTop
    if (that.scrollTop > 0) {
      that.btnFlag = true
    } else {
      that.btnFlag = false
    }
  }


    }
}
</script>

<style scoped>
a{
    text-decoration: none;
}
a:link{
    color: black;
}
a:hover{
    color: deepskyblue;
}
a:visited{
    color:darkgray;
}
</style>