package service;

import bean.Blog;
import bean.BlogLike;
import vo.BlogLikedCount;


import java.util.List;
import java.util.Map;


public interface RedisService {


    void saveLiked2Redis(Integer userId,Integer postId);
    void unlikeFromRedis(Integer userId,Integer postId);
    BlogLike ifLiked(Integer userId, Integer postId);
    void incrLikedCount(Integer postId);
    void decrLikedCount(Integer postId);
    void incrViewCount(Integer postId);
    void setNewBlog2Redis(Blog blog);
    List<Blog> getNewBlogFromRedis();
    List<BlogLike> getLikedDataFromRedis();
    List<BlogLikedCount> getLikedCountFromRedis();
    List<Blog> getHotBlogFromRedis();
    void setHotBlog2Redis();
    Map<String, Object> getAllTags();
    void setTags2Redis(String tags);
}
