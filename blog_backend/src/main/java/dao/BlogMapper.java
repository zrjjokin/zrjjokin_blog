package dao;

import bean.Blog;
import bean.BlogLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import vo.BlogLikeViewResp;

import java.util.List;

@Repository
public interface BlogMapper {
    public int insertBlog(Blog blog);
    public int insertAddBlog(@Param("id") int id, @Param("blogId") int blogId);
    public Blog selectBlogById(@Param("id") Integer id);
    public List<Blog> allBlogs();
    public int saveBlogLike(BlogLike blogLike);
    public BlogLike getBlogLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
    public void updateInfo(Blog blog);
    List<Blog> getHotBlogs();
    void updateBlogLike(BlogLike bl);
}
