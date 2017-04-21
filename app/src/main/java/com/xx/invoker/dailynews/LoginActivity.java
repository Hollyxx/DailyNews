package com.xx.invoker.dailynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.db.MyHelper;
import com.xx.invoker.dailynews.utils.StatusBarUtil;

/**
 * 登录页面
 * by invokerxx
 * Time 2017年4月12日14:18:28
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editAccount, editPassword;
    private MyHelper helper;
    private SQLiteDatabase database;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.home_toolbar);
        helper = new MyHelper(this,null);
        preferences = MyApp.getPreferences();

        initView();
    }

    private void initView() {
        editAccount = (EditText) findViewById(R.id.user_account);
        editPassword = (EditText) findViewById(R.id.user_password);
        database = helper.getWritableDatabase();
        ((Button)findViewById(R.id.btn_user_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRightLogin(editAccount.getText().toString(),editPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    editAccount.setText("");
                    editPassword.setText("");
                    Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isRightLogin(String accountText, String passwordText){
        Cursor cursor = database.query("user", null, "username = ? and password = ?", new String[]{accountText, passwordText}, null, null, null);
        if (cursor!=null && cursor.getCount()>0){
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("username",accountText);
            edit.putString("password",passwordText);
            edit.putBoolean("loginStatus",true);
            edit.commit();
            return true;
        }
        return false;
    }

    public void bClick(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void mClick(View view) {
        finish();
    }
}
