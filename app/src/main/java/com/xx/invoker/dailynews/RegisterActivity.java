package com.xx.invoker.dailynews;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xx.invoker.dailynews.app.MyApp;
import com.xx.invoker.dailynews.db.MyHelper;

public class RegisterActivity extends AppCompatActivity {

    private MyHelper helper;
    private EditText editAccount, editPassword, editConfirmPsw;
    private Button register;
    private SQLiteDatabase db;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        helper = new MyHelper(this, null);
        preferences = MyApp.getPreferences();
        db = helper.getWritableDatabase();
        initView();

    }

    private void initView() {
        editAccount = (EditText) findViewById(R.id.user_account);
        editPassword = (EditText) findViewById(R.id.user_password);
        editConfirmPsw = (EditText) findViewById(R.id.confirm_password);
        register = (Button) findViewById(R.id.btn_user_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editAccount.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPsw = editConfirmPsw.getText().toString();

                if (isExists(account)) {
                    Toast.makeText(RegisterActivity.this, "账号已存在，请重新输入", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPsw)) {
                    Toast.makeText(RegisterActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPsw)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不相同，请重新输入", Toast.LENGTH_SHORT).show();
                } else if(isInsert(account,password)){
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putBoolean("loginStatus", true);
                    edit.putString("username", account);
                    edit.putString("password", password);
                    edit.commit();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean isExists(String accountText) {
        Cursor cursor = db.query("user", null, "username = ?", new String[]{accountText}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    private boolean isInsert(String accountText, String passwordText) {
        ContentValues values = new ContentValues();
        values.put("username", accountText);
        values.put("password", passwordText);
        long num = db.insert("user", null, values);
        if (num > 0) {
            return true;
        }
        return false;
    }
}
