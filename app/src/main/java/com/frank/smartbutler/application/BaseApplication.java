package com.frank.smartbutler.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.frank.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 文件名:BaseApplication
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.application
 * 创建者:Frank
 * 创建时间:2018/3/6 20:27
 * 描述:  Application 基类中我们可以做一些Application层次的工作，例如我们可以在其中退出所有的Activity
 * 这个工作相信是很多APP都具备的功能，另外我们也可以做一些第三方SDK的初始化操作
 *
 */

public class BaseApplication extends Application {
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //腾讯bugly初始化（异常反馈采集）
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //Bmob第一：默认初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        //科大讯飞语音初始化
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.VOICE_KEY);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
