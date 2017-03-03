package com.xx.invoker.dailynews.address;

/**
 * Created by invoker on 2017/2/27.
 * <p>
 * 地址类，用来存储开发过程中的API
 */

public class Address {

    // 最新消息
    public static final String Latest_News = "http://news-at.zhihu.com/api/4/news/latest";

    // 消息内容获取与离线下载    需要拼接属性Id
    public static final String Content_News = "http://news-at.zhihu.com/api/4/news/";

    // 过往消息，需要拼接属性 日期   格式 20140212
    public static final String Before_News = "http://news-at.zhihu.com/api/4/news/before/";
}
