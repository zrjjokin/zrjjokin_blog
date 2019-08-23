import Vue from 'vue'
import Router from 'vue-router'
import Index from '../index/Index'
import BlogContent from '../content/BlogContent'
import BlogHeader from '../header/BlogHeader'
import CommonFooter from '../footer/CommonFooter'
import AddBlog from '../content/AddBlog'
import login from '../components/login'
import Article from '../content/Article'
import Line from '../content/TimeLine.vue'

Vue.use(Router)

export default new Router({
  
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login,        
    },
    {
      path:'/',
      name:"Index",
      component:Index,
      children:[
        {
  
          path:'/index',
          name:'index',
          components:{
            header:BlogHeader,
            content: BlogContent,
            footer:CommonFooter
          }
        },       
          {
            path:'/addBlog',
            name:'AddBlog',
            components:{
              header:BlogHeader,
              content: AddBlog,
              footer:CommonFooter
            }
          },
          {
            path:'/timeline',
            name:'TimeLine',
            components:{
              header:BlogHeader,
              content:Line,
              footer:CommonFooter
            }
          },
          {
            path:'/article/:blogId',
            name:'Article',
            components:{
              header:BlogHeader,
              content:Article,
              footer:CommonFooter
            }
          }
           
      ]
      
    }
  ]
})
