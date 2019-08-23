package controller;

import bean.Blog;
import bean.BlogLike;
import bean.Person;
import bean.RespBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BlogService;
import service.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisService redisService;
    @RequestMapping(value = "/addBlog")


    public @ResponseBody RespBean addBlog( @RequestBody Blog blog, HttpServletRequest request) {

        Person p = new Person(1,22,"zrj","Java web");

        Date date = new Date();
        blog.setDate(date);
        RespBean resp=null;
        if (blogService.addBlog(blog,p.getId()) > 0) {
            resp = RespBean.ok("添加成功", blog);

        } else {
            resp = RespBean.error("添加失败", blog);
        }
        return resp;
    }


    @RequestMapping("/article/{id}")
    @ResponseBody
    public Blog showBlog(@PathVariable Integer id) throws IOException {

        return blogService.getBlogById(id);
    }


    @RequestMapping("/blogList")
    @ResponseBody
    public List<Blog> blogList(){

        return blogService.getAllBlogs();


    }
    @RequestMapping("/newBlog")
    @ResponseBody
    public List<Blog> newBlogList(){
        return redisService.getNewBlogFromRedis();
    }

    @RequestMapping("/hotBlog")
    @ResponseBody
    public List<Blog> hotBlogList(){
        return redisService.getHotBlogFromRedis();
    }
    //标签墙
    @RequestMapping("/tags")
    @ResponseBody
    public Map<String, Object> allTags(){
       return redisService.getAllTags();
    }

    //点赞/取消点赞博客
    @RequestMapping("/likeBlog")
    @ResponseBody
    public RespBean likeBlog(@RequestParam("postId")Integer blogId,@RequestParam("userId") Integer userId){
        BlogLike bl = redisService.ifLiked(userId,blogId);
        //先判断是否点赞过
        if(bl==null){
            //1.数据库中没有
            redisService.incrLikedCount(blogId);
            redisService.saveLiked2Redis(userId,blogId);
            return RespBean.ok("点赞成功");

        }else if(bl.getStatus()==1){
            redisService.unlikeFromRedis(userId,blogId);
            redisService.decrLikedCount(blogId);
            return RespBean.ok("取消成功");
        }else if(bl.getStatus()==0){
            redisService.incrLikedCount(blogId);
            redisService.saveLiked2Redis(userId,blogId);
            return RespBean.ok("点赞成功");
        }
        return null;

    }
}
