package org.test;

import bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml","classpath:application-redis.xml","classpath:application-dao.xml","classpath:application-service.xml"})
public class BaseTest {
    @Autowired
    public RedisTemplate<String,String> redisTemplate;

    @Test
    public void testRedis(){
        Map<String,Object> map=new HashMap<>();
        map.put("name","zrj");
        map.put("job","it");

       redisTemplate.opsForHash().putAll("key1",map);
       Map<Object,Object> hash=redisTemplate.opsForHash().entries("key1");
        System.out.println("ans:"+hash);
}
    @Test
    public void testObject2Map(){


    }
}
