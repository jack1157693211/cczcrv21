package com.cczcrv.jack.cczcrv.Model;

/**
 * Created by jack on 2018/1/18.
 */

public class review_item_model {
    public String head_pic;
    public String name;
    public Float score;
    public String time;
    public String content;
    public String[] pics;
    public Integer allPicCount;

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public Integer getAllPicCount() {
        return allPicCount;
    }

    public void setAllPicCount(Integer allPicCount) {
        this.allPicCount = allPicCount;
    }
}
