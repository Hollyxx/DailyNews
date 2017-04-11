package com.xx.invoker.dailynews;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xx.invoker.dailynews.view.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerView();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_fragment_home, new HomeFragment()).commit();

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

        switch (item.getItemId()) {
            case R.id.list_company:

                break;

            case R.id.list_design:

                break;
            case R.id.list_finance:

                break;
            case R.id.list_game:

                break;
            case R.id.list_home:

                break;
            case R.id.list_internet:

                break;
            case R.id.list_manga:

                break;
            case R.id.list_movie:

                break;
            case R.id.list_music:

                break;
            case R.id.list_no_bored:

                break;
            case R.id.list_psychology:

                break;
            case R.id.list_sports:

                break;

        }

        return false;
    }


}
