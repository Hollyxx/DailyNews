package com.xx.invoker.dailynews.utils;

import java.util.Date;

/**
 * Created by invoker on 2017/2/28.
 */

public class DateUtils {

    //将 20170212 格式的数据转换成 2月12日的方法
    public static String getDate(String str) {
        int year = Integer.valueOf(str.substring(0, 4));
        int month = Integer.valueOf(str.substring(4, 6));
        int day = Integer.valueOf(str.substring(6));
        String time = month + "月" + day + "日";
        return time;
    }

}
