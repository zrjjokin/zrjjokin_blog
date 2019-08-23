package service;

import bean.Blog;
import bean.BlogLike;
import dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Service;
import util.MyBeanUtils;
import util.RedisUtil;
import vo.BlogLikedCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public void saveLiked2Redis(Integer userId, Integer postId) {
        String key = userId + "::" + postId;
        redisUtil.hset("user_post_like",key,1);
    }

    @Override
    public void unlikeFromRedis(Integer userId, Integer postId) {
        String key = userId + "::" + postId;
        redisUtil.hset("user_post_like",key,0);
    }

    /**
     * 查询该用户是否点赞
     * 先查询缓存中是否有该条数据（防止用户短时间多次点赞）
     * 缓存没有结果就查询数据库
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public BlogLike ifLiked(Integer userId, Integer postId) {
        Object res = redisUtil.hget("user_post_like",userId + "::" + postId);
        if(res!=null)
        {
            Integer status = (Integer) res;
            return new BlogLike(userId,postId, status);
        }


        return blogMapper.getBlogLike(userId,postId);

    }

    @Override
    public void incrLikedCount(Integer postId) {
        redisUtil.hincr("blog_like_count",String.valueOf(postId),1);
    }

    @Override
    public void decrLikedCount(Integer postId) {
        redisUtil.hincr("blog_like_count",String.valueOf(postId),-1);
    }

    /**
     * 浏览次数自增
     * @param postId
     */
    @Override
    public void incrViewCount(Integer postId) {
        redisUtil.hincr("blog_view_count",String.valueOf(postId),1);
    }

    @Override
    public void setNewBlog2Redis(Blog blog) {
        List<Blog> blogs = getNewBlogFromRedis();
        Blog oldBlog = null;
        Long oldTime = 1622476800000L;
        if(blogs.size()<5){
            redisUtil.hmset("newBlog::"+blog.getBlogId(),MyBeanUtils.transBean2Map(blog));
            return ;
        }
        for(Blog b:blogs){
            long t = b.getDate().getTime();
            if(oldTime>=t){
                oldTime = b.getDate().getTime();
                oldBlog = b;
            }
        }

        redisUtil.del("newBlog::"+oldBlog.getBlogId());
        redisUtil.hmset("newBlog::"+blog.getBlogId(),MyBeanUtils.transBean2Map(blog));
    }

    @Override
    public List<Blog> getNewBlogFromRedis() {
        Set<String> set = redisUtil.getAllByPattern("newBlog::*");
        List<Blog> list = new ArrayList<>();
        for( String s: set){
            Blog obj = new Blog();
            MyBeanUtils.transMap2Bean(redisUtil.hmget(s),obj);
            list.add(obj);
        }
        return list;

    }

    @Override
    public List<BlogLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisUtil.scan("user_post_like");
        List<BlogLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出userId,postId
            String[] split = key.split("::");
            Integer userId = Integer.parseInt(split[0]);
            Integer postId = Integer.parseInt(split[1]);
            Integer value = (Integer) entry.getValue();
            BlogLike blogLike = new BlogLike(userId,postId,value);
            list.add(blogLike);
            //从redis删除
            redisUtil.hdel("user_post_like",key);
        }
        return list;
    }

    @Override
    public List<BlogLikedCount> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisUtil.scan("blog_like_count");
        List<BlogLikedCount> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String)entry.getKey();
            Integer value = (Integer) entry.getValue();
            BlogLikedCount blogLikedCount = new BlogLikedCount(Integer.parseInt(key),value);
            list.add(blogLikedCount);
            //从redis删除
            redisUtil.hdel("blog_like_count",key);
        }
        return list;
    }

    @Override
    public List<Blog> getHotBlogFromRedis() {

        Set<String> set = redisUtil.getAllByPattern("hotBlog::*");
        List<Blog> list = new ArrayList<>();
        for( String s: set){
            Blog obj = new Blog();
            MyBeanUtils.transMap2Bean(redisUtil.hmget(s),obj);
            list.add(obj);
        }
        return list;
    }
    //定时任务
    @Override
    public void setHotBlog2Redis() {
        //先删除之前的缓存
        Set<String> set = redisUtil.getAllByPattern("hotBlog::*");
        for(String s : set){
            redisUtil.del(s);
        }
        //查询数据库获得最新最热门数据
        List<Blog> blogs = blogMapper.getHotBlogs();
        for(Blog b:blogs){
            redisUtil.hmset("hotBlog::"+b.getBlogId(),MyBeanUtils.transBean2Map(b));
        }
    }

    @Override
    public Map<String, Object> getAllTags() {
        return redisUtil.hmget("tags");
    }

    @Override
    public void setTags2Redis(String tags) {
        String[] tag = tags.split(",");
        for(String s : tag){
            redisUtil.hincr("tags",s,1);
        }


    }
}
