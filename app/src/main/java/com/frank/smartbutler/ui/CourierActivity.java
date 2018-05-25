package com.frank.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.frank.smartbutler.MainActivity;
import com.frank.smartbutler.R;
import com.frank.smartbutler.adapter.CourierAdapter;
import com.frank.smartbutler.entity.CourierData;
import com.frank.smartbutler.utils.LogUtils;
import com.frank.smartbutler.utils.StaticClass;
import com.frank.smartbutler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件名:CourierActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/10 17:49
 * 描述:    快递查询
 */

public class CourierActivity extends BaseActivity {
    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private ListView courierNameListView;
    private List<CourierData> mList = new ArrayList<>();
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
        initData();
    }

    private void initData() {

    }


    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        mListView = (ListView) findViewById(R.id.mListView);
        courierNameListView = (ListView) findViewById(R.id.courierNameListView);

        btn_get_courier.setOnClickListener(new MyOnClickListener());
        //初始化dialog
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_query, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //提示框以外点击无效
        dialog.setCancelable(false);
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_get_courier:
                    dialog.show();
                    mListView.setVisibility(View.VISIBLE);
                    courierNameListView.setVisibility(View.GONE);
                    //获取输入框内容
                    String name = et_name.getText().toString().trim();
                    String number = et_number.getText().toString().trim();
                    String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY+"&com="+
                            name+"&no="+number;
                    //判断输入框是否为空
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){
                        //请求数据
                        RxVolley.get(url, new HttpCallback() {
                            /**
                             * Http请求成功时回调
                             * @param t HttpRequest返回信息
                             */
                            @Override
                            public void onSuccess(String t) {
                                //Toast.makeText(getApplicationContext(),t,Toast.LENGTH_SHORT).show();
                                LogUtils.i(this,t);
                                dialog.dismiss();
                                //解析json数据
                                parsingJson(t);
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatatime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);//设置数据适配器
        } catch (JSONException e) {
            e.printStackTrace();
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
