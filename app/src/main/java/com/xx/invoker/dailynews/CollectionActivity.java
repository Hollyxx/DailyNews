package com.xx.invoker.dailynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.invoker.dailynews.adapter.CollectionListAdapter;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.db.MyHelper;
import com.xx.invoker.dailynews.model.News;
import com.xx.invoker.dailynews.utils.StatusBarUtil;
import com.xx.invoker.dailynews.view.SlideCutListView;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity implements SlideCutListView.RemoveListener{

    private SlideCutListView list;
    private TextView empty;
    private CollectionListAdapter adapter;
    private List<News> data;
    private MyHelper helper;
    private SharedPreferences preferences;
    private SQLiteDatabase db;
    private String username;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.home_toolbar);
        Toast.makeText(this, "左滑或右滑删除收藏", Toast.LENGTH_SHORT).show();
        helper = new MyHelper(this, null);
        preferences = MyApp.getPreferences();

        initView();
        initData();

    }

    private void initView() {
        list = (SlideCutListView) findViewById(R.id.list_collection);
        empty = (TextView) findViewById(R.id.empty);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_collection);

        list.setEmptyView(empty);
        list.setRemoveListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News news = data.get(position);
                Intent intent = new Intent(CollectionActivity.this, ContentActivity.class);
                intent.putExtra("id",news.getId());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("image", news.getImage());
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query(username);
            }
        });
    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new CollectionListAdapter(this, data);
        list.setAdapter(adapter);
        db = helper.getWritableDatabase();
        username = preferences.getString("username", "abc");
        query(username);
    }

    private void query(String username) {
        Cursor cursor = db.query("collections", null, "username=?", new String[]{username}, null, null, null, null);
        if (cursor != null) {
            data.clear();
            while (cursor.moveToNext()) {
                News news = new News();
                news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                news.setId(cursor.getInt(cursor.getColumnIndex("id")));
                news.setImage(cursor.getString(cursor.getColumnIndex("url")));
                data.add(news);
            }
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
        }
    }

    private boolean delete(int id) {
        int result = db.delete("collections", "id=?", new String[]{"" + id});
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        int id = data.get(position).getId();
        if (delete(id)){
            query(username);
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void bClick(View view) {
        finish();
    }
}
