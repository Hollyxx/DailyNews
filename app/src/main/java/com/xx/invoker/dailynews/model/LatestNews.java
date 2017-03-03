package com.xx.invoker.dailynews.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invoker on 2017/2/28.
 * <p>
 * 最新消息的实体类
 */

public class LatestNews extends NewsParent {

    private List<News> items;

    public List<News> getItems() {
        return items;
    }

    @Override
    protected void parseJson(JSONObject object) {
        if (object != null) {
            items = new ArrayList<>();
            try {
                date = "今日热闻";
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
