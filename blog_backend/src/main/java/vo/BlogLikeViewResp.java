package vo;

import bean.Blog;

import java.io.Serializable;

public class BlogLikeViewResp implements Serializable {
    private Blog blog;
    private int like_count;
    private int view_count;

    public BlogLikeViewResp(){

    }
    public BlogLikeViewResp(Blog blog, int like_count, int view_count) {
        this.blog = blog;
        this.like_count = like_count;
        this.view_count = view_count;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }
}
