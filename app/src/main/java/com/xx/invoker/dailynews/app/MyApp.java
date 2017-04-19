package com.xx.invoker.dailynews.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by invoker on 2017/3/4.
 */

public class MyApp extends Application {

    private static RequestQueue queue;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
        preferences = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.commit();
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static RequestQueue getQueue() {
        return queue;
    }
}
