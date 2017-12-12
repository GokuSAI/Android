package com.example.saikrishna.gsignin;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by saikrishna on 11/18/17.
 */

public class Post implements Serializable {

    String name,content;
Date time;
    String id,userid;

    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return time != null ? time.equals(post.time) : post.time == null;

    }

    @Override
    public int hashCode() {
        return time != null ? time.hashCode() : 0;
    }
}
