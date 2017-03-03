package com.xx.invoker.dailynews.model;

import org.json.JSONObject;

/**
 * Created by invoker on 2017/2/28.
 *
 *
 *  新闻类的父类
 */

public abstract class NewsParent {

    protected String date;

    protected  abstract void parseJson(JSONObject object);

}
