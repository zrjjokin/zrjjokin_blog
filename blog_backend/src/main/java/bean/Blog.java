package bean;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class Blog implements Serializable {
    private Integer blogId;
    private String title;
    private String content;
    private int blogType;
    private String tags;
    private Date date;
    private Integer like_count;
    private Integer view_count;

    public Blog(String title, String content, int blogType, String tags, Date date, Integer like_count, Integer view_count) {
        this.title = title;
        this.content = content;
        this.blogType = blogType;
        this.tags = tags;
        this.date = date;
        this.like_count = like_count;
        this.view_count = view_count;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Blog(){

    }
    public Blog( Integer blogId, String title, String content, int blogType, String tags, Date date ,Integer view_count,Integer like_count) {
        this.blogId = blogId;

        this.title = title;
        this.content = content;
        this.blogType = blogType;
        this.tags = tags;
        this.date = date;
        this.view_count = view_count;
        this.like_count = like_count;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBlogType() {
        return blogType;
    }

    public void setBlogType(int blogType) {
        this.blogType = blogType;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "title:"+this.title+"content:"+this.content+"tags"+this.tags+"blogType:"+this.blogType;
    }
}
