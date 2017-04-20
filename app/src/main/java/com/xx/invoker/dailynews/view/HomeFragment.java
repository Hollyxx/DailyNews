package com.xx.invoker.dailynews.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xx.invoker.dailynews.ContentActivity;
import com.xx.invoker.dailynews.R;
import com.xx.invoker.dailynews.adapter.HomeListAdapter;
import com.xx.invoker.dailynews.adapter.MyPagerAdapter;
import com.xx.invoker.dailynews.address.Address;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.model.BeforeNews;
import com.xx.invoker.dailynews.model.HeadNews;
import com.xx.invoker.dailynews.model.LatestNews;
import com.xx.invoker.dailynews.model.News;

import org.json.JSONArray;
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
    private String date = "";
    private boolean isBottom = false;
    private List<HeadNews> headList;
    private MyPagerAdapter pagerAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 12138) {
                int current = pager.getCurrentItem();
                if (current == pagerAdapter.getCount() - 1) {
                    pager.setCurrentItem(0);
                } else {
                    pager.setCurrentItem(current + 1);
                }

                handler.sendEmptyMessageDelayed(12138, 3000);
            }
        }
    };
    private ViewPager pager;

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
        adapter = new HomeListAdapter(context, data);
        initView(view);
        initHeadView();
        initData();
        list.setAdapter(adapter);
        setListener();

        Message message = new Message();
        message.what = 12138;
        handler.sendMessageDelayed(message, 3000);
        return view;
    }

    //初始化ViewPager的视图和数据
    private void initHeadView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.header_list_home, null);
        pager = (ViewPager) inflate.findViewById(R.id.pager_header_list_home);

        headList = new ArrayList<>();
        pagerAdapter = new MyPagerAdapter(context, headList);
        pager.setAdapter(pagerAdapter);
        list.addHeaderView(inflate);
    }

    private void setListener() {

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isBottom && scrollState == SCROLL_STATE_IDLE && date != null) {
                    initMore();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount) == totalItemCount;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = data.get((int) id);
                if (o instanceof News) {
                    News news = (News) o;
                    Intent intent = new Intent(context, ContentActivity.class);
                    intent.putExtra("id", news.getId());
                    intent.putExtra("title", news.getTitle());
                    intent.putExtra("image", news.getImage());
                    startActivity(intent);
                }
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


    }

    //进行网络加载数据
    private void initData() {

        StringRequest request = new StringRequest(Address.Latest_News, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        //获取今日热闻的数据
                        data.clear();
                        headList.clear();
                        LatestNews news = new LatestNews();
                        news.parseJson(new JSONObject(response));
                        data.add(news.getDate());
                        List<News> items = news.getItems();
                        data.addAll(items);
                        date = news.getTime();

                        //获得首页ViewPager的数据
                        JSONArray array = new JSONObject(response).getJSONArray("top_stories");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            HeadNews head = new HeadNews();
                            head.parseJson(object);
                            headList.add(head);
                        }
                        pagerAdapter.notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络好像不太好", Toast.LENGTH_SHORT).show();
            }
        });

        MyApp.getQueue().add(request);

    }

    //实现分页加载的数据
    private void initMore() {
        int page = Integer.valueOf(date);
        page--;

        StringRequest request = new StringRequest(Address.Before_News + (page + 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        if (response != null) {
                            BeforeNews news = new BeforeNews();
                            news.parseJson(new JSONObject(response));
                            data.add(news.getDate());
                            data.addAll(news.getItems());
                            date = news.getTime();
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "哎呀，好像出错了", Toast.LENGTH_SHORT).show();
            }
        });
        MyApp.getQueue().add(request);
    }


    private void initView(View view) {
        list = (ListView) view.findViewById(R.id.list_home);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_home);
    }

}
