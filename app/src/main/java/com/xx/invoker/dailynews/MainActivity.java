package com.xx.invoker.dailynews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.model.Content;
import com.xx.invoker.dailynews.utils.StatusBarUtil;
import com.xx.invoker.dailynews.view.HomeFragment;
import com.xx.invoker.dailynews.view.ThemeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager manager;
    private SharedPreferences preferences;
    private TextView username;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = MyApp.getPreferences();
        initDrawerView();

        //将状态栏改成标题栏的颜色
        StatusBarUtil.setWindowStatusBarColor(this, R.color.home_toolbar);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_fragment_home, new HomeFragment()).commit();
    }

    //初始化抽屉布局的头布局
    private void initHeadView(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.user_name);
        if (preferences.getBoolean("loginStatus", false)) {
            username.setText(preferences.getString("username", "abc"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.getBoolean("loginStatus", false)) {
            username.setText(preferences.getString("username", "abc"));
        }
    }

    //初始化抽屉布局及ToolBar
    private void initDrawerView() {
        //获得Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_home);

        drawer = (DrawerLayout) findViewById(R.id.drawer_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        navigationView.setNavigationItemSelectedListener(this);
        initHeadView(navigationView);
    }

    //抽屉布局的菜单点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = 0;
        drawer.closeDrawer(Gravity.LEFT);
        ThemeFragment fragment = new ThemeFragment();
        Bundle bundle = new Bundle();
        toolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.list_recommend:
                id = 12;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_company:
                id = 5;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_design:
                id = 4;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_finance:
                id = 6;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_game:
                id = 2;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_internet:
                id = 10;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_manga:
                id = 9;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_movie:
                id = 3;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_music:
                id = 7;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_no_bored:
                id = 11;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_psychology:
                id = 13;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
            case R.id.list_sports:
                id = 8;
                bundle.putInt("id", id);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frame_fragment_home, fragment).commit();
                break;
        }

        return false;
    }


    public void mClick(View view) {
        int id = view.getId();
        switch (id) {

            //返回首页的Fragment
            case R.id.home:
                toolbar.setTitle("首页");
                drawer.closeDrawer(Gravity.LEFT);
                manager.beginTransaction().replace(R.id.frame_fragment_home, new HomeFragment()).commit();
                break;

            //进入我的收藏
            case R.id.collect_nav_home:
                drawer.closeDrawer(Gravity.LEFT);
                if (preferences.getBoolean("loginStatus", false)) {
                    Intent intent = new Intent(this, CollectionActivity.class);
                    startActivity(intent);
                } else {
                    //跳转到登陆页面
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.user_name:
                //TODO  判断是否登录，登陆跳个人中心，未登录跳登陆界面
                drawer.closeDrawer(Gravity.LEFT);
                if (preferences.getBoolean("loginStatus", false)) {
                    Intent intent = new Intent(this, MineActivity.class);
                    startActivity(intent);
                } else {
                    //跳转到登陆页面
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }

    }

    /**
     * 按两次退出程序
     */
    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次，将退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
