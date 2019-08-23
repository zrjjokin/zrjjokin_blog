package service;

import bean.BlogLike;

public interface LikedService {
    /**
     * 查询该用户是否点赞改文章
     * @param userId
     * @param postId
     * @return
     */

    BlogLike hasLiked(Integer userId,Integer postId);

    /**
     * 将redis的点赞数据（userId,postId）存入数据库
     */
    void transLikedFromRedis2DB();

    /**
     * 将redis中的点赞数量存入数据库blog_like
     */
    void transLikedCountFromRedis2DB();

    void save(BlogLike blogLike);
}
