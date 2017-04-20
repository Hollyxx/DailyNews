package com.xx.invoker.dailynews.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.xx.invoker.dailynews.ContentActivity;
import com.xx.invoker.dailynews.R;
import com.xx.invoker.dailynews.adapter.HomeListAdapter;
import com.xx.invoker.dailynews.adapter.HomeThemeAdapter;
import com.xx.invoker.dailynews.address.Address;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends Fragment {

    private Context context;
    private HomeThemeAdapter adapter;
    private List<News> data;
    private ListView list;
    private SwipeRefreshLayout swipe;
    private int id;
    private TextView summary;
    private ImageView image;

    public ThemeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        initView(view);
        initData();
        loadData();
        return view;
    }

    private void initView(View view) {
        list = (ListView) view.findViewById(R.id.list_theme);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_theme);

        View inflate = LayoutInflater.from(context).inflate(R.layout.item_head__pager_list_home, null);
        image = (ImageView) inflate.findViewById(R.id.image_header_list_home);
        summary = (TextView) inflate.findViewById(R.id.title_pager_header_list_home);

        list.addHeaderView(inflate);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News news = data.get((int)id);
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("id", news.getId());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("image", news.getImage());
                startActivity(intent);

            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new HomeThemeAdapter(context, data);
        list.setAdapter(adapter);
    }

    private void loadData() {
        StringRequest request = new StringRequest(Address.Theme_News + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        data.clear();
                        JSONObject object = new JSONObject(response);
                        summary.setText(object.getString("description"));
                        Glide.with(context).load(object.getString("background")).into(image);
                        JSONArray array = object.getJSONArray("stories");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            News news = new News();
                            if (json.has("images")) {
                                news.setImage((String) json.getJSONArray("images").get(0));
                            } else {
                                news.setImage("");
                            }
                            news.setTitle(json.getString("title"));
                            news.setId(json.getInt("id"));
                            data.add(news);
                        }
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
                Toast.makeText(context, "哎呀，好像出错了呦", Toast.LENGTH_SHORT).show();
            }
        });

        MyApp.getQueue().add(request);
    }
}
