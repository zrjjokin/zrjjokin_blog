<template>
    <div class="new-blog">
        <div class="title">最新文章</div>
        <ul>
            <li v-for="blog in newBlogList" :key="blog.blogId">
                <a :href="'/#/article/'+blog.blogId">
                    <p class="blogtitle">{{blog.title}}</p>
                    <p class="info">
                        <span class="time">{{blog.date}}</span>
                        <span class="likes el-icon-star-off">{{blog.like_count}}</span>
                        <span class="views el-icon-view">{{blog.view_count}}</span>
                    </p>
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data(){
        return {
            newBlogList:[]
        }
    },
    created(){
        this.initData();
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
            var _this = this;
            this.$http.get(
                '/api/newBlog'
            ).then(resp=>{
                var newBlogList = resp.data;
                newBlogList.sort(function(a,b){
                    return b.date-a.date;
                });
                for(var i=0;i<newBlogList.length;i++){
                    newBlogList[i].date = _this.timestampToTime(newBlogList[i].date);
                    _this.newBlogList.push(newBlogList[i]);
                }
                
            })
        }
    }
}
</script>

<style scoped>
    .title{
        font-size: 20px;
        font-weight: bold;
        font-family:  "黑体";
        font-style: normal;
    }
    ul{
        
        list-style-type: none;
    }
    a{
        text-decoration: none;
    }
    a:link {background:#B2FF99;color:gray;}    /* 未被访问的链接 */
a:visited {background:#FFFF85;color:gray;} /* 已被访问的链接 */
a:hover {background:#FF704D;}   /* 鼠标指针移动到链接上 */
a:active {background:#FF704D;}  /* 正在被点击的链接 */

    li{
        height: 50px;
        display: block;
        text-align: left;
       
        
    }
    .blogtitle{

    }
    .info{
        overflow: hidden;
        border-bottom: 0.7px solid darkgrey;
        
    }
    .time{
        font-size: 13px;
        float: left;
        padding-bottom: 5px;
    }
    .views{
        padding-right:10px; 
       padding-bottom: 5px;
        font-size: 13px;
        float: right;
    }
    .likes{
        padding-bottom: 5px;
        padding-right:10px; 
        font-size: 13px;
        float: right;
    }
</style>