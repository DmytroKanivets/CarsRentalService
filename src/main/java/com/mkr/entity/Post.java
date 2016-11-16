package com.mkr.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="news")
@NamedQuery(name="Post.selectAll", query="SELECT p FROM Post p")
public class Post implements Serializable {

    public static final int TARGET_BLOG = 1 << 0;
    public static final int TARGET_EMAIL = 1 << 1;

    @Transient private int target;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
