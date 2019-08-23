package service;

import bean.Blog;
import com.fasterxml.jackson.core.JsonProcessingException;
import dao.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RedisService redisService;

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    public int addBlog(Blog blog, Integer id){
        //blog.setBlogId(blogMapper.insertBlog(blog));
        blogMapper.insertBlog(blog);
        redisService.setNewBlog2Redis(blog);
        //int i = 1/0;
        redisService.setTags2Redis(blog.getTags());
        System.out.println(blog.getBlogId());
        if(blog.getBlogId()>0){
            if(blogMapper.insertAddBlog(id,blog.getBlogId())>0)
                return 1;
        }
        return 0;
    }

    /**
     * 获取博客
     * @param id
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public Blog getBlogById(Integer id)  {
        //System.out.println("service");

        Blog blog = blogMapper.selectBlogById(id);
        //更新该博客的浏览数
        blog.setView_count(blog.getView_count()+1);
        blogMapper.updateInfo(blog);
        return blog;

        }

    @Override
    public List<Blog> getAllBlogs() {
        return blogMapper.allBlogs();
    }

    @Override
    public boolean blogLike(Integer id) throws IOException {


        return false;
    }


}
