package com.xx.invoker.dailynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xx.invoker.dailynews.utils.StatusBarUtil;

/**
 * 个人中心
 *
 */
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        StatusBarUtil.setWindowStatusBarColor(this,R.color.home_toolbar);
    }
}
