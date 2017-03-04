package com.xx.invoker.dailynews.model;

import org.json.JSONObject;

/**
 * Created by invoker on 2017/2/28.
 *
 *
 *  新闻类的父类
 */

public abstract class NewsParent {

    public String date;

    public String getDate() {
        return date;
    }

    public  abstract void parseJson(JSONObject object);

}
