package com.xx.invoker.dailynews;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xx.invoker.dailynews.adapter.CommentListAdapter;
import com.xx.invoker.dailynews.address.Address;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.model.Comment;
import com.xx.invoker.dailynews.utils.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private int id;
    private ListView list;
    private SwipeRefreshLayout swipe;
    private CommentListAdapter adapter;
    private List<Comment> data;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        StatusBarUtil.setWindowStatusBarColor(this,R.color.home_toolbar);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 123);
        initView();
        initData();
        loadData();
    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new CommentListAdapter(this,data);
        list.setAdapter(adapter);
    }

    private void initView() {
        list = (ListView) findViewById(R.id.list_comment);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_comment);
        empty = (TextView) findViewById(R.id.empty_comment);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        list.setEmptyView(empty);

    }

    private void loadData() {

        StringRequest request = new StringRequest(Address.getComments(1, id), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    data.clear();
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray comments = object.getJSONArray("comments");
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject json = comments.getJSONObject(i);
                            Comment comment = new Comment();
                            comment.parseJson(json);
                            data.add(comment);
                        }
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApp.getQueue().add(request);
    }

    public void bClick(View view) {
        finish();
    }
}
