package com.xx.invoker.dailynews.model;

import org.json.JSONObject;

/**
 * Created by invoker on 2017/2/28.
 * <p>
 * <p>
 * 新闻类
 */

public class News {

    private int id;
    private String image;
    private String title;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void parseJson(JSONObject object) {
        if (object != null) {
            try {
                image = (String) object.getJSONArray("images").get(0);
                id = object.getInt("id");
                title = object.getString("title");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
