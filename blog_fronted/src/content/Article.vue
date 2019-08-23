<template>
    <div class="article-content">
      <el-row :gutter="20">
  <el-col :span="10" :offset="6"><div class="alert" style="height:40px;overflow:hidden;">
          <el-alert
        style="margin-top:5px;;"
        v-show="showLike"
    :title="showMsg"
    center
    type="success"
    show-icon>
  </el-alert></div></el-col>
</el-row>
      <el-row>
        
        
        
        <el-col :span="18">
        <div class="content">
        <h3 id="title">
            {{article.title}}
        </h3>
        <div id="info">
          <el-tag style="float:left;margin:4px;margin-right:20px;width:70px;" type="success" effect="dark">{{checkType()}}</el-tag>        
          <el-tag
          v-for="tag in article.tags"
          :key=tag
          type="warning"
          effect="light"
          style="display:inline-block;margin:4px;margin-right:20px;float:left; padding-top:1px;width:70px;"
        >{{ tag}}</el-tag>
          <span style="float:left;margin-right:30px;color:darkgrey;">
            <span style="">At Time /</span> {{article.date}}
          </span>
          <span style="float:right;margin-right:30px;color:darkgrey;"><i class="el-icon-view">{{article.view_count}} 阅读</i></span>
          <span style="float:right;margin-right:30px;color:darkgrey;cursor:pointer;" @click="likeBlog()"><i class="el-icon-star-off">{{article.like_count}} 喜欢</i></span>
        </div>
        <div id="article" v-html="article.content" v-hljs>
          {{article.content}}
        </div>
        </div>
        </el-col>
        <el-col :span="6">
          <div class="layout-right">
            <hot-read></hot-read>
          </div>
        </el-col>
        </el-row>
    </div>
</template>

    
    
<script>

import HotBlog from '../view/HotBlog';
export default {
  components:{
    "hot-read":HotBlog
  },
    data(){
        return {
            article:null,
            showLike:false,
            showMsg:""
        }
    },
    created() {
        this.getArticle(this.$route.params.blogId)
        
    },
    watch:{
     
      
    },
    mounted(){
        
        
    },
        
    
    methods:{
      //点赞或者取消点赞博客
      likeBlog(){
        var _this = this;
        var data={
            userId:_this.$store.state.user.id,
            postId:_this.article.blogId
          };
        this.$http.post(
          '/api/likeBlog',
          this.$qs.stringify(data)
        ).then(resp=>{
          //console.log(resp);
          _this.showMsg = resp.data.msg;
          _this.showLike = true;
          if(_this.showMsg=="点赞成功"){
            _this.article.like_count+=1;
          }
          else {
            _this.article.like_count-=1;
          }
          setTimeout(function(){
            _this.showLike = false;
          },1000)

        })
      },
      timestampToTime(timestamp){
      var date=new Date(timestamp);
      var Y=date.getFullYear()+"-";
      var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
      var D = date.getDate() ;
      return Y+M+D;
    },
        checkType(){
            var type = this.article.blogType;
            if(type==1){
                return "原创"
            }else if(type==2){
                return "转载"
            }else{
                return "翻译";
            }
        },
        getArticle(id){
            console.log(id);
            var _this=this;
            this.$http.post(
                `/api/article/${id}`
            ).then(resp=>{
                var article = resp.data;
                article.tags = article.tags.split(",");
                article.date = this.timestampToTime(article.date);
                _this.article=article;
                //console.log(_this.article.content);
                //document.getElementById("article").innerHTML=_this.article.content;
            })
        }
    },

}
</script>

<style scoped>

.article-content{
  background-image: '../assets/back.jpg';
  background-repeat:no-repeat;
}
.content{
  margin-top: -10px;
}
#info{
  
  overflow: hidden;
  height: 40px;
  line-height: 40px;
}
#title{
  text-align: left;
  font-family: "黑体";
  font-size: 25px;
  padding-left:5px; 
}
#article{
  
    text-align: left;
    padding: 10px;
    width:96%;
    
    /*float:left;*/
    margin-top:20px;
    overflow: auto;
    background-color:white;
}
#article >>> p{
  text-align: left;
}
/* table 样式 */
#article >>> table {
    border-top: 1px solid #ccc;
    border-left: 1px solid #ccc;
    border-collapse: collapse;
  }
  #article >>> table td,
  table th {
    border:1px solid #cad9ea;
    color: #666;  
    padding: 3px 5px;
  }
  #article >>> table th {
    background-color: #CCE8EB;
    
    text-align: center;
  }
  
  /* blockquote 样式 */
  #article >>> blockquote {
    display: block;
    border-left: 8px solid #d0e5f2;
    padding: 5px 10px;
    margin: 10px 0;
    line-height: 1.4;
    font-size: 100%;
    background-color: #f1f1f1;
  }
  
  /* code 样式 */
  #article >>>code {
    display: inline-block;
    *display: inline;
    *zoom: 1;
    background-color: #ccc;
    border-radius: 3px;
    padding: 3px 5px;
    margin: 0 3px;
    font-family: 'Times New Roman', Times, serif;
  }
  #article >>> pre code {
    display: block;
  }
  
  /* ul ol 样式 */
  #article >>> ul, ol {
    margin: 10px 0 10px 20px;
  }

  #article img{
    width: 80%;
    height: 80%;
  }
</style>
