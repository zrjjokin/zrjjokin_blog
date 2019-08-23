package service;

import bean.Blog;
import vo.BlogLikeViewResp;

import java.io.IOException;
import java.util.List;


public interface BlogService {
    public int addBlog(Blog blog, Integer id);
    public Blog getBlogById(Integer id) throws IOException;
    public List<Blog> getAllBlogs();
    public boolean blogLike(Integer id) throws IOException;
}
