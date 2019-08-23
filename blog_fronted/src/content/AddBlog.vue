<template>
  <div class="add-container">
    
    <form id="Blog" action="#" >
        <div id="button" style="margin-top:10px;overflow:hidden;">
        <el-button type="primary" style="width:100px;float:left" @click="submitBlog">发布文章</el-button>
        <el-button type="success" style="width:100px;float:left;" @click="previewBlog">
          <i class="el-icon-view">预览</i>
        </el-button>
      </div>
      <div id="title">
        
        <el-select v-model="blog.blogType" placeholder="类型" style="display:inline-block;float:left;">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <div style="overflow:hidden;">
          <el-input placeholder="标题：" v-model="blog.title"></el-input>
        </div>
      </div>
      <div id="tags" style="overflow:hidden;">
        <span
          style="width:100px;float:left;line-height:45px;margin-left:0;border-right:1px solid darkgray;font-family:SimSun;font-size:20px;color: aquamarine"
        >
          <strong>标签</strong>
        </span>
        <el-tag
          :key="tag"
          v-for="tag in tags"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
        >{{tag}}</el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        ></el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新标签</el-button>
      </div>
  
      <div id="editor">
        <div id="div1" class="toolbar" ></div>
        <div style="padding: 5px 0; color: #ccc"></div>
       
       <div id="div2" class="text" >
          <p>请在此输入内容</p>             
      </div>
      <div id="previewContent" ></div>
      </div>
      
      
      
    </form>
    
  </div>
</template>


<script>
import E from "wangeditor";
import {mapGetters} from "vuex";

export default {
  data() {
    return {
      options: [
        {
          value: 1,
          label: "原创"
        },
        {
          value: 2,
          label: "转载"
        },
        {
          value: 3,
          label: "翻译"
        }
      ],
      
        
    dialogContentVisible:false,
      inputVisible: false,
      inputValue: "",
      blog: {
        title: "",
        tags: '',
        blogType: "",
        content: "",
        //person:window.localStorage.getItem('user').username
      },
      tags:[],
     
    };
  },
  computed:{
    ...mapGetters(['user']),
    
  },
  mounted() {
    //console.log('mounted')

    var _this = this;
    var editor = new E("#div1", "#div2");
    editor.customConfig.debug = true;
    editor.customConfig.zIndex = 100;
    editor.customConfig.pasteFilterStyle = false;
    // 配置服务器端地址(springmvc解决跨域)
    editor.customConfig.uploadImgServer = '/api/upload'
    editor.customConfig.uploadFileName = "myFile"; //设置文件上传的参数名称
    // 将图片大小限制为 3M
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
    // 限制一次最多上传 5 张图片
    editor.customConfig.uploadImgMaxLength = 5;
    editor.customConfig.onchange = function(html) {
      // html 即变化之后的内容
      _this.blog.content = html;
      
    };
    //自定义上传图片事件
	editor.customConfig.uploadImgHooks = {
		before : function(xhr, editor, files) {
			
		},
		success : function(xhr, editor, result) {
			console.log("上传成功");
		},
		fail : function(xhr, editor, result) {
			console.log("上传失败,原因是"+result);
		},
		error : function(xhr, editor) {
			console.log("上传出错");
		},
		timeout : function(xhr, editor) {
			console.log("上传超时");
		}
	}

    editor.create();
  },
  methods: {
    handleClose(tag) {
      this.tags.splice(this.tags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.tags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    previewBlog() {
        if(this.blog.content){
            console.log(this.blog.content);
            this.dialogContentVisible = true;
            document.getElementById("previewContent").innerHTML=this.blog.content;
            //document.scrollingElement.scrollTop = document.scrollingElement.scrollHeight;
        }
    },
    submitBlog(){
      var _this=this;
    
      //先校验表单数据
      if(this.blog.title===""||this.blog.content===""||this.tags.length==0){
        alert("请完善您的博客内容！！！");
      }else{
        //this.blog.date=new Date();
        var nTags = this.tags.map(function(a){
          return a.trim();
        });
        this.blog.tags=nTags.join(',');
        this.$http.post("/api/addBlog",
        
           this.blog,
             {withCredentials: true},    
        ).then(resp=>{
          var data = resp.data;
          var blogId=resp.data.object.blogId
        _this.$router.push({
          path:`/article/${blogId}`,

        });
        });
      }
    }
  }
};
</script>

<style>
.toolbar {
  border: 1px solid #ccc;
  width: 100%;
}

.text {
  border: 1px solid #ccc;
  height: 400px;
  width: 50%;
  float: left;
  
}
#title {
  
  margin-top: 10px;
  margin-bottom: 20px;
  overflow: hidden;
}
#tags {

  margin-bottom: 50px;
  width: 100%;
  height: 45px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  line-height: 45px;
}
#editor {
  overflow: auto;
  top: 0px;
  left: 0px;
  margin-bottom: 20px;

 
}

.text {
  text-align: left;
}
#previewContent{
  margin-left: 5px;
    text-align: left;
    border: 1px solid darkgray;   
    width:49.35%;
    height: 400px;
    float:left;
    margin-bottom: 20px;
    overflow: auto;
    background-color: gainsboro
}
#previewContent p{
  text-align: left;
}
/* table 样式 */
#previewContent table {
    border-top: 1px solid #ccc;
    border-left: 1px solid #ccc;
  }
  #previewContenttable td,
  table th {
    border-bottom: 1px solid #ccc;
    border-right: 1px solid #ccc;
    padding: 3px 5px;
  }
  #previewContent table th {
    
    border-bottom: 2px solid #ccc;
    text-align: center;
  }
  
  /* blockquote 样式 */
  #previewContent blockquote {
    display: block;
    border-left: 8px solid #d0e5f2;
    padding: 5px 10px;
    margin: 10px 0;
    line-height: 1.4;
    font-size: 100%;
    background-color: #f1f1f1;
  }
  
  /* code 样式 */
  #previewContent code {
    display: inline-block;
    *display: inline;
    *zoom: 1;
    background-color: #d0e5f2;
    border-radius: 3px;
    padding: 3px 5px;
    margin: 0 3px;
    font-family: 'Times New Roman', Times, serif;
  }
  #previewContent pre code {
    display: block;
  }
  
  /* ul ol 样式 */
  #previewContent ul, ol {
    margin: 10px 0 10px 20px;
  }
</style>
