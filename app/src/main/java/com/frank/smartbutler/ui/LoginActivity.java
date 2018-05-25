package com.frank.smartbutler.ui;

/**
 * 文件名:MyUser
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.entity
 * 创建者:Frank
 * 创建时间:2018/3/8 19:08
 * 描述:      登入
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.frank.smartbutler.MainActivity;
import com.frank.smartbutler.R;
import com.frank.smartbutler.entity.MyUser;
import com.frank.smartbutler.utils.SpUtil;
import com.frank.smartbutler.utils.StaticClass;
import com.frank.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private CustomDialog dialog;
    private TextView tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered = (Button) findViewById(R.id.btn_registered);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        btn_registered.setOnClickListener(this);
        //初始化dialog
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //提示框以外点击无效
        dialog.setCancelable(false);
        //读取sp中保存密码的状态并回显
        boolean isCheck = SpUtil.getBoolean(this, StaticClass.KEEP_PASS, false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            //用户名密码回显
            et_name.setText(SpUtil.getString(this,StaticClass.USER,""));
            et_password.setText(SpUtil.getString(this,StaticClass.PASSWORD,""));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered://注册
                startActivity(new Intent(getApplicationContext(),RegisteredActivity.class));
                break;
            case R.id.btnLogin://登入
                //获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断密码是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                    //登入
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null){
                                //邮箱验证
                                if (user.getEmailVerified()) {
                                    dialog.show();
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });

                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
        }
    }
    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存checkbox的状态
        SpUtil.putBoolean(this, StaticClass.KEEP_PASS,keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()){
            SpUtil.putString(this,StaticClass.USER,et_name.getText().toString().trim());
            SpUtil.putString(this,StaticClass.PASSWORD,et_password.getText().toString().trim());
        }else{
            SpUtil.remove(this,StaticClass.USER);
            SpUtil.remove(this,StaticClass.PASSWORD);
        }
    }
}