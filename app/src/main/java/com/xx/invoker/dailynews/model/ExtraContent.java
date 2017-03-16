package com.xx.invoker.dailynews.model;

import org.json.JSONObject;

/**
 * Created by invoker on 2017/3/16.
 */

public class ExtraContent {

    private int long_comments;
    private int popularity;
    private int short_comments;
    private int comments;

    public void parseJson(JSONObject object) {
        if (object != null) {
            try {
                long_comments = object.getInt("long_comments");
                popularity = object.getInt("popularity");
                short_comments = object.getInt("short_comments");
                comments = object.getInt("comments");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getLong_comments() {
        return long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public int getComments() {
        return comments;
    }
}
