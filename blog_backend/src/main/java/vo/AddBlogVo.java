package vo;

import bean.Blog;

public class AddBlogVo {
    private Blog blog;
    private String username;

    public AddBlogVo(Blog blog, String username) {
        this.blog = blog;
        this.username = username;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
