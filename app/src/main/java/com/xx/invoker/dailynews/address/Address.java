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

    //新闻的额外属性  需要拼接的属性Id  长评论，点赞数
    public static final String Extra_Content = "http://news-at.zhihu.com/api/4/story-extra/";

    //主题新闻的详情页面 需要拼接的属性   id
    public static final String Theme_News="http://news-at.zhihu.com/api/4/theme/";

    //新闻长评论查看  http://news-at.zhihu.com/api/4/story/8997528/long-comments
    public static final String Comments_News="http://news-at.zhihu.com/api/4/story/";

    public static String getComments(int type, int id){
        if (type == 1)
            return Comments_News + id +"/short-comments";
        else
            return Comments_News + id +"/long-comments";
    }
}
