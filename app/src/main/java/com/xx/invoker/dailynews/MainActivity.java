package com.xx.invoker.dailynews;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerView();
        initListView();


    }

    //初始化ListView
    private void initListView() {


        View view = LayoutInflater.from(this).inflate(R.layout.header_list_home, null);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager_header_list_home);
        TextView title = (TextView) view.findViewById(R.id.title_pager_header_list_home);
        RadioGroup group = (RadioGroup) view.findViewById(R.id.group_pager_header_list_home);

    }

    //初始化抽屉布局及ToolBar
    private void initDrawerView() {
        //获得Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_home);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_home);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //TODO: 抽屉的单条点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}
