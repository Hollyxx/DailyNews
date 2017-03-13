package com.xx.invoker.dailynews.model;

import com.xx.invoker.dailynews.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by invoker on 2017/2/28.
 * <p>
 * <p>
 * 以前新闻的实体类
 */

public class BeforeNews extends NewsParent {

    private List<News> items;
    private String time;

    public String getTime() {
        return time;
    }

    public List<News> getItems() {
        return items;
    }

    @Override
    public void parseJson(JSONObject object) {
        if (object != null) {
            items = new ArrayList<>();
            try {
                time = object.getString("date");
                String str = object.getString("date");
                date = DateUtils.getDate(str);

                JSONArray array = object.getJSONArray("stories");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    News news = new News();
                    news.parseJson(json);
                    items.add(news);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
