package service;

import bean.Blog;
import bean.BlogLike;
import dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.BlogLikedCount;

import java.util.List;

@Service
public class LikedServiceImpl implements LikedService{


    @Autowired
    private RedisService redisService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public BlogLike hasLiked(Integer userId, Integer postId) {
        return blogMapper.getBlogLike(userId,postId);
    }

    @Override
    public void transLikedFromRedis2DB() {
        System.out.println("**************************************************");
        List<BlogLike> list = redisService.getLikedDataFromRedis();
        for(BlogLike b : list){
            BlogLike bl = hasLiked(b.getUserId(),b.getPostId());
            if(bl==null){
                blogMapper.saveBlogLike(b);
            }else{
                bl.setStatus(b.getStatus());
                blogMapper.updateBlogLike(bl);
            }

        }
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        List<BlogLikedCount> list = redisService.getLikedCountFromRedis();
        for(BlogLikedCount b:list){
            Blog blog = blogMapper.selectBlogById(b.getBlogId());
            if(blog!=null){
                Integer like_count = b.getCount()+blog.getLike_count();
                blog.setLike_count(like_count);
                blogMapper.updateInfo(blog);
            }

        }
    }

    @Override
    public void save(BlogLike blogLike) {
        blogMapper.saveBlogLike(blogLike);
    }
}
