package com.xx.invoker.dailynews;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xx.invoker.dailynews.address.Address;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.db.MyHelper;
import com.xx.invoker.dailynews.model.Content;
import com.xx.invoker.dailynews.model.ExtraContent;
import com.xx.invoker.dailynews.model.News;
import com.xx.invoker.dailynews.utils.StatusBarUtil;

import org.json.JSONObject;


public class ContentActivity extends AppCompatActivity {

    private TextView comment, praise;
    private Intent intent;
    private int id;
    private WebView web;
    private ImageView image, praiseImg, collectImg;
    private TextView title;
    private Content content;
    private Toolbar bar;
    private MyHelper DBHelper;
    private News news;
    private SQLiteDatabase db;
    private SharedPreferences preferences;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
        preferences = MyApp.getPreferences();
        //获取上个页面传来的信息
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        news.setId(id);
        news.setTitle(intent.getStringExtra("title"));
        news.setImage(intent.getStringExtra("image"));
        loadDate();

    }

    //初始化控件
    private void initView() {
        bar = (Toolbar) findViewById(R.id.toolbar_content);
        comment = (TextView) bar.findViewById(R.id.action_comment_number);
        praise = (TextView) bar.findViewById(R.id.action_praise_number);
        web = (WebView) findViewById(R.id.web_content);
        image = (ImageView) findViewById(R.id.image_header_list_home);
        title = (TextView) findViewById(R.id.title_pager_header_list_home);
        praiseImg = (ImageView) findViewById(R.id.action_praise_toolbar_content);
        collectImg = (ImageView) findViewById(R.id.action_collect_toolbar_content);
        linearLayout = (LinearLayout) findViewById(R.id.head_view);
        DBHelper = new MyHelper(this, null);
        db = DBHelper.getWritableDatabase();
        news = new News();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.home_toolbar);

    }

    //加载网络数据
    private void loadDate() {
        StringRequest request_content = new StringRequest(Address.Content_News + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Gson gson = new Gson();
                        content = gson.fromJson(response, Content.class);
                        if (TextUtils.isEmpty(content.getImage())) {
                            linearLayout.setVisibility(View.GONE);
                        } else {
                            Glide.with(ContentActivity.this).load(content.getImage()).into(image);
                            title.setText(content.getTitle());
                        }
                        setWebViewDisplay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        StringRequest request_extra = new StringRequest(Address.Extra_Content + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject object = new JSONObject(response);
                        ExtraContent content = new ExtraContent();
                        content.parseJson(object);
                        int popularity = content.getPopularity();
                        int comments = content.getComments();
                        if (popularity >= 1000) {
                            praise.setText(popularity / 1000 + ".0K");
                        } else
                            praise.setText(popularity + "");

                        comment.setText(comments + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApp.getQueue().add(request_content);
        MyApp.getQueue().add(request_extra);
    }

    //设置WebView
    private void setWebViewDisplay() {
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        web.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    //menu的点击事件
    public void menuClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.action_collect_toolbar_content:
                if (!preferences.getBoolean("loginStatus", false)) {
                    Intent intent = new Intent(ContentActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (!isCollect()) {
                        insert(db, news);
                        collectImg.setImageResource(R.mipmap.collected);
                    } else
                        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_comment_toolbar_content:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("id", this.id);
                startActivity(intent);
                break;

            case R.id.action_praise_toolbar_content:
                praiseImg.setImageResource(R.mipmap.praised);
                Toast.makeText(this, "点赞成功 +1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.back_content_activity:
                finish();
                break;
        }
    }

    //判断是否已经收藏过
    private boolean isCollect() {
        Cursor cursor = db.query("collections", null, "id=?", new String[]{"" + news.getId()}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else
            return false;
    }

    //向数据库中添加数据
    private void insert(SQLiteDatabase db, News news) {
        ContentValues values = new ContentValues();
        values.put("id", news.getId());
        values.put("title", news.getTitle());
        values.put("url", news.getImage());
        values.put("username", preferences.getString("username", "abc"));
        long count = db.insert("collections", null, values);
        if (count > 0) {
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

}
