<template>
  <div class="content-container">
    <el-row>

    <el-col :span="18">
    <div class="blog-cell" v-for="blog in currentBlogLists" :key="blog.blogId" v-if="currentBlogLists.length>0">
      <a :href="'/#/article/'+blog.blogId"><h3 style="text-align:left;margin-bottom:4px;margin-left:10px;">{{blog.title}}</h3></a>
      <!-- 博客标签 -->
      <div class="tag-group">
        <el-tag
          v-for="tag in blog.tags"
          :key=tag
          type="warning"
          effect="light"
          style="display:inline-block;margin:10px;float:left; height:28px;"
        >{{ tag}}</el-tag>
      </div>
      <!--只显示一行文本
            博客正文
      -->
      <a :href="'/#/article/'+blog.blogId"><p class="blog-content">{{blog.content}}</p></a>
      <!--博客信息-->
      <div class="blog-info" style="text-align:left;padding-left:5px;color:#707070;">
        <span style="padding-right:15px;color:#00CCFF">At time /</span>
        <span style="padding-right:15px;">{{blog.date}}</span>
        <span style="padding-right:15px;" class="el-icon-view">{{blog.view_count}} 浏览</span>
        <span style="padding-right:15px;" class="el-icon-star-off">{{blog.like_count}} 喜欢</span>
      </div>
    </div>
    <el-alert
    title="抱歉没有找到相关数据"
    center
    type="error"
    effect="dark"
    :closable="false"
    v-if="currentBlogLists.length==0">
  </el-alert>
    </el-col>
    <el-col :span="6">
      <div class="layout-right">
        <new-blog></new-blog>
        <hot-read></hot-read>
        <tag-wall></tag-wall>
      </div>
    </el-col>
    </el-row>
    <br>
    <el-pagination background layout="prev, pager, next" :current-page="currentPage" :total="total" :page-size="pageSize" @current-change="changeCurrentPage"></el-pagination>
  </div>
</template>
<script>
import NewBlog from '../view/NewBlog';
import HotBlog from '../view/HotBlog';
import TagWall from '../view/TagWall';
import {mapGetters} from 'vuex';
export default {
  
  components:{
    'new-blog':NewBlog,
    'hot-read':HotBlog,
    'tag-wall':TagWall
  },
  created() {
    this.getBlogList();
  },
  data() {
    return {
      pageSize:6,
      
      
      blogLists: [],
      copyBlogLists:[]
    };
  },
  computed:{
    ...mapGetters(['keywords','currentPage','blogIndex','pageSize']),
    total(){
      return this.blogLists.length;
    },
    currentBlogLists(){
      
      return this.blogLists.slice(this.blogIndex,this.blogIndex+this.pageSize);
    },
   
  },
  watch:{
    keywords(newV,oldV){
      //用户退格时候重置
      if(newV.length<oldV.length){
        this.blogLists = this.copyBlogLists;
      }
      this.blogLists = this.blogLists.filter(function(blog){
          console.log(blog.tags.join(' ').indexOf(newV)>-1)
          return blog.title.indexOf(newV)>-1 || blog.tags.join(' ').indexOf(newV)>-1;
        });
    }
  },
  methods:{
    changeCurrentPage(currentPage){
      this.$store.commit("updateCurrentPage",currentPage);
      this.$store.commit('updateShowHeader',true);

    },
    timestampToTime(timestamp){
      var date=new Date(timestamp);
      var Y=date.getFullYear()+"-";
      var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
      var D = date.getDate() ;
      return Y+M+D;
    },
    //获取后端博客列表数据
    getBlogList(){
      var _this=this;
      this.$http.get(
        "/api/blogList"
      ).then(
        resp=>{
          console.log(resp);
          var blogLists= resp.data;
           blogLists.sort(function(a,b){
              return b.date-a.date;
            })
          //数据预处理
          for( var i=0; i<blogLists.length;i++){
            
            blogLists[i].tags = blogLists[i].tags.split(",");
            blogLists[i].date = this.timestampToTime(blogLists[i].date);
            blogLists[i].content = blogLists[i].content.replace(/<[^>]+>/g,"");
            _this.blogLists.push(blogLists[i]);
           
            _this.copyBlogLists.push(blogLists[i]);
            
          }
        }
      )
    }
  }
};
</script>
<style scoped>
.content-container {
  width: 100%;

  margin: 20px auto;
}
.blog-cell {
  
  box-shadow: 0 0 25px #cac6c6;
  border-radius: 10px;
}
.blog-content {
  overflow: hidden;
  display: -webkit-box;
  text-overflow: ellipsis;
  -webkit-line-clamp: 1; /*要显示的行数*/
  -webkit-box-orient: vertical;
  color: darkgray;
  font-family: "Times New Roman", Times, serif;
  margin-top: 10px;
  padding-left:10px; 
  text-align: left;
}
.tag-group {
  overflow: hidden;
}
.blog-info {
}
a{
  text-decoration: none;
}
a:link {background:#B2FF99;color:black;}    /* 未被访问的链接 */
a:visited {background:#FFFF85;color:gray;} /* 已被访问的链接 */
a:hover {background:#FF704D;}   /* 鼠标指针移动到链接上 */
a:active {background:#FF704D;}  /* 正在被点击的链接 */

</style>
