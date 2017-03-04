package com.xx.invoker.dailynews.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by invoker on 2017/3/4.
 */

public class MyApp extends Application {

    private static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getQueue() {
        return queue;
    }
}
