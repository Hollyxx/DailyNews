package com.xx.invoker.dailynews.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xx.invoker.dailynews.R;
import com.xx.invoker.dailynews.adapter.HomeListAdapter;
import com.xx.invoker.dailynews.address.Address;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.model.LatestNews;
import com.xx.invoker.dailynews.model.News;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView list;
    private List<Object> data;
    private SwipeRefreshLayout swipe;
    private Context context;
    private HomeListAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        data = new ArrayList<>();
        initView(view);
        initData();


        adapter = new HomeListAdapter(context, data);
        list.setAdapter(adapter);

        return view;
    }

    //进行网络加载数据
    private void initData() {

        StringRequest request = new StringRequest(Address.Latest_News, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response != null) {
                        LatestNews news = new LatestNews();
                        news.parseJson(new JSONObject(response));
                        data.add(news.getDate());
                        List<News> items = news.getItems();
                        data.addAll(items);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyApp.getQueue().add(request);

    }

    private void initView(View view) {
        list = (ListView) view.findViewById(R.id.list_home);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_home);
    }

}
