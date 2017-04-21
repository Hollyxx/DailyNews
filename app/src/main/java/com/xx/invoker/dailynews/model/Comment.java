package com.xx.invoker.dailynews.model;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by invoker on 2017/4/21.
 */

public class Comment {

    private String author;
    private String content;
    private String url;
    private String time;
    private String like;
    private String replyContent;
    private String replyAuthor;

    public void parseJson(JSONObject object) {
        if (object != null) {
            try {
                author = object.getString("author");
                content = object.getString("content");
                url = object.getString("avatar");
                setTime(getData(object.getString("time")));
                like = object.getString("likes");
                if (object.has("reply_to")) {
                    JSONObject json = object.getJSONObject("reply_to");
                    replyAuthor = json.getString("author");
                    replyContent = json.getString("content");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getData(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = Long.parseLong(date);
        String s = format.format(new Date(l*1000));
        return s;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyAuthor() {
        return replyAuthor;
    }

    public void setReplyAuthor(String replyAuthor) {
        this.replyAuthor = replyAuthor;
    }
}
