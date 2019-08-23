package org.test.dao;

import bean.Blog;
import bean.BlogLike;
import controller.BlogController;
import dao.BlogMapper;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.test.BaseTest;
import service.BlogService;
import service.LikedService;
import service.RedisService;
import vo.BlogLikeViewResp;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

public class MapperTest extends BaseTest {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private LikedService likedService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogController blogController;
    @Autowired
    private BlogService blogService;

    @Test
    public void test1() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Blog obj = blogMapper.selectBlogById(1);
        BeanMap map= new BeanMap(obj);
        System.out.println(map);
        System.out.println("aaa");
    }
    @Test
    public void test2(){
        BlogLike bl = new BlogLike(1,1);
        blogMapper.saveBlogLike(bl);
    }

    @Test
    public void test3(){
        blogController.likeBlog(4,1);
        likedService.transLikedCountFromRedis2DB();
        likedService.transLikedFromRedis2DB();
    }
    //测试事务
    @Test
    public void test4(){
        Blog blog = new Blog("事务测试","ceshi",1,"事务",  new Date(),0,0);
        blogService.addBlog(blog,1);
    }
}
