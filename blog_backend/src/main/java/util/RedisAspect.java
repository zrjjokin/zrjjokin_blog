package util;


import bean.Blog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vo.BlogLikeViewResp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Aspect
public class RedisAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisAspect.class);
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 环绕通知
     * 先从redis获取缓存，获取不到就查询数据库，然后保存到缓存
     * @param joinPoint
     * @return
     */
    public Object around(ProceedingJoinPoint joinPoint) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ObjectMapper om = new ObjectMapper();
        Integer blogId=null;
        //获取查询的id
        Object[] args = joinPoint.getArgs();
        Object obj = new Blog();
        if(args!=null && args.length>0){
            blogId = (Integer) args[0];
        }
        //前置
        Map<String, Object> map =redisUtil.hmget("blogId"+blogId);
        MyBeanUtils.transMap2Bean(map,obj);
        if(map.size()>0){
            //浏览数自增
            //redisUtil.hincr("blogId"+blogId,"view_count",1);
            int view_count = (int) redisUtil.hget("blogId"+blogId,"view_count");
            redisUtil.hset("blogId"+blogId,"view_count",1+view_count);
            System.out.println(redisUtil.hget("blogId1","view_count"));
            logger.info("**********从Redis中查到了数据**********");
            logger.info("Redis的KEY值:"+blogId);
            logger.info("Redis的value值:"+obj.toString());
            return obj;
        }

        logger.info("**********没有从Redis查到数据**********");
        try{
            obj =  joinPoint.proceed(args);
        }catch(Throwable e){
            e.printStackTrace();
        }
        logger.info("**********开始从MySQL查询数据**********");

        //后置

        //BeanMap m = new BeanMap(obj);
        Map<String,Object> newmap = MyBeanUtils.transBean2Map(obj);
        redisUtil.hmset("blogId"+blogId, newmap);
        logger.info("**********数据成功保存到Redis缓存!!!**********");
        return obj;
    }

}
