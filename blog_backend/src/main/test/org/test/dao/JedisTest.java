package org.test.dao;

import bean.Blog;
import bean.Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.test.BaseTest;
import service.BlogService;
import service.RedisService;
import util.RedisUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JedisTest  extends BaseTest {
    @Autowired
    private RedisUtil redisUtil ;
    @Autowired
     private RedisService redisService;

    @Autowired
    private BlogService blogService;

    @Test
    public void setObject() throws IOException {
        ObjectMapper om=new ObjectMapper();

        Person p= new Person(1,20,"zrj","it_work");
        String s = om.writeValueAsString(p);
        //System.out.println(s);
        redisUtil.set("person",s);
        Person np = om.readValue(String.valueOf(redisUtil.get("person")),Person.class);
        System.out.println(np);
    }

    @Test
    public void redisAspectTest() throws IOException {
        blogService.getBlogById(2);
    }

    @Test
    public void test1(){
        Blog blog = new Blog(15,"测试sada","ceshi",1,"java", new Date(),0,0);
        blogService.addBlog(blog,1);
        List<Blog> l = redisService.getNewBlogFromRedis();
        System.out.println("ceshi");
    }

    @Test
    public void test2(){
        redisService.setHotBlog2Redis();
    }

    @Test
    public void test3(){
        Blog blog = new Blog(15,"测试2","ceshi",1,"java,nginx",  new Date(),0,0);
        blogService.addBlog(blog,1);
    }

    @Test
    public void test4(){
        Map<String,Object> map = redisService.getAllTags();
        System.out.println("aaa");
    }
}
