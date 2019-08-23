package bean;

import enums.LikedStatusEnum;

public class BlogLike {

    private Integer id;
    private Integer userId;
    private Integer postId;
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public BlogLike(Integer id, Integer userId, Integer postId, Integer status) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.status = status;
    }

    public BlogLike(Integer userId, Integer postId){
        this.userId = userId;
        this.postId = postId;
    }
    public BlogLike( Integer userId, Integer postId , Integer status) {

        this.userId = userId;
        this.postId = postId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
