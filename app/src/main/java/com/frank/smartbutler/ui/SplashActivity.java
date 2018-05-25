package com.frank.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.utils.SpUtil;
import com.frank.smartbutler.utils.StaticClass;
import com.frank.smartbutler.utils.UtilTools;

/**
 * 文件名:SplashActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/7 17:00
 * 描述:      闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()){
                        startActivity(new Intent(getApplicationContext(),GuideActivity.class));
                    }else {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };
    private TextView tv_splash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private boolean isFirst() {
        boolean isFirst = SpUtil.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst){
            SpUtil.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    private void initView() {
        //延时
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,1500);

        tv_splash = (TextView) findViewById(R.id.tv_splash);
        //设置字体
        //设置字体
        UtilTools.setFont(this,tv_splash);

    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
