package com.frank.smartbutler.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.smartbutler.MainActivity;
import com.frank.smartbutler.R;
import com.frank.smartbutler.service.SmsService;
import com.frank.smartbutler.utils.LogUtils;
import com.frank.smartbutler.utils.SpUtil;
import com.frank.smartbutler.utils.UtilTools;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文件名:SettingActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/7 15:31
 * 描述:      设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    //语音播报开关
    private Switch sw_speak;
    //短信提示开关
    private Switch sw_sms;
    //检查版本更新
    private LinearLayout ll_update;
    private TextView tv_version;
    //扫一扫
    private LinearLayout ll_scan;
    //扫描结果
    private TextView tv_scan_result;
    //我的二维码
    private LinearLayout ll_qr_code;
    //我的位置
    private LinearLayout ll_my_location;
    //关于软件
    private LinearLayout ll_about;
    private String versionName;
    private int mversionCode;
    private String mDownloadUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_sms = (Switch) findViewById(R.id.sw_sms);
        ll_update = (LinearLayout) findViewById(R.id.ll_update);
        tv_version = (TextView) findViewById(R.id.tv_version);
        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
        ll_qr_code.setOnClickListener(this);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
        ll_update.setOnClickListener(this);
        sw_speak.setOnClickListener(this);
        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);
        ll_my_location.setOnClickListener(this);


        //读取sw_speak的状态  回显
        boolean isSpeak = SpUtil.getBoolean(getApplicationContext(), "isSpeak", false);
        sw_speak.setChecked(isSpeak);
        try {
            getVersionNameCode();
            tv_version.setText(getString(R.string.text_test_version) + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tv_version.setText(getString(R.string.text_test_version) );
        }
        sw_sms.setOnClickListener(this);
        ll_scan.setOnClickListener(this);
        //读取sw_sms的状态  回显
        boolean isSms = SpUtil.getBoolean(getApplicationContext(), "isSms", false);
        sw_sms.setChecked(isSms);
        //读取SmsService服务的状态（开启/关闭），回显sw_sms按钮的状态
        boolean running = UtilTools.isServiceRunning(this, "com.frank.smartbutler.service.SmsService");
        sw_sms.setChecked(running);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_speak://语音播报
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                SpUtil.putBoolean(getApplicationContext(),"isSpeak",sw_speak.isChecked());
                break;
            case R.id.sw_sms://短信提示
                //切换相反
                sw_sms.setSelected(!sw_sms.isSelected());
                //保存状态
                SpUtil.putBoolean(getApplicationContext(),"isSms",sw_sms.isChecked());
                if (sw_sms.isChecked()){
                    startService(new Intent(this, SmsService.class));
                }else {
                    stopService(new Intent(this, SmsService.class));
                }
                break;
            case R.id.ll_update://版本更新
                RxVolley.get("http://192.168.1.105/api/update.json", new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        parsingJson(t);
                    }
                });
                break;
            case R.id.ll_scan://扫一扫
                LogUtils.i(this,"ll_scan");
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.ll_qr_code://我的二维码
                startActivity(new Intent(this, QrCodeActivity.class));
                break;
            case R.id.ll_my_location://我的位置
                startActivity(new Intent(this,LocationActivity.class));
                break;
            case R.id.ll_about://关于软件
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }

    //解析Json
    private void parsingJson(String t) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            mDownloadUrl = jsonObject.getString("downloadUrl");
            String versionCode = jsonObject.getString("versionCode");
            String versionDes = jsonObject.getString("versionDes");
            if (Integer.parseInt(versionCode)>mversionCode){
                showUpdateDialog(versionDes);
            }else {
                Toast.makeText(this,"已是最新版本",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //弹出升级提示
    private void showUpdateDialog(String versionDes) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本了")
                .setMessage(versionDes)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this, UpdatActivity.class);
                        intent.putExtra("url", mDownloadUrl);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不需要作什么，自动会执行dismiss方法
            }
        }).show();
    }

    //获取版本号
    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
        versionName = packageInfo.versionName;
        mversionCode = packageInfo.versionCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            LogUtils.i(this,"二维码数据："+scanResult);
            tv_scan_result.setText(scanResult);
        }
    }

    @Override
    public void showNextPage() {

    }

    @Override
    public void showPrePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        // 平移动画
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

}
