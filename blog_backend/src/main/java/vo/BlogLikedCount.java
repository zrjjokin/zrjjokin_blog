package vo;

public class BlogLikedCount {

    private Integer blogId;
    private Integer count;

    public BlogLikedCount(Integer blogId, Integer count) {
        this.blogId = blogId;
        this.count = count;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
