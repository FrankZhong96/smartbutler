package com.frank.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.frank.smartbutler.MainActivity;
import com.frank.smartbutler.R;
import com.frank.smartbutler.utils.LogUtils;
import com.frank.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONObject;

/**
 * 文件名:PhoneActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/11 11:06
 * 描述:      归属地查询
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener{

    //输入框
    private EditText et_phone;
    //公司logo
    private ImageView iv_company;
    //结果
    private TextView tv_result;

    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_del, btn_query;

    //标记位
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        iv_company = (ImageView) findViewById(R.id.iv_company);
        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        //长按del键
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_phone.setText("");
                return false;
            }
        });
    }


    
    @Override
    public void onClick(View v) {
        String str = et_phone.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag){
                    flag = false;
                    str = "";
                    et_phone.setText(str);
                }
                //每次结尾加1
                et_phone.setText(str+((Button)v).getText());
                //移动光标
                et_phone.setSelection(str.length()+1);
                break;
            case R.id.btn_del://删除键
                if (!TextUtils.isEmpty(str) && str.length()>0){
                    //每次结尾减1
                    et_phone.setText(str.substring(0,str.length()-1));
                    //移动光标
                    et_phone.setSelection(str.length()-1);
                }
                break;
            case R.id.btn_query://查询
                getPhone(str);
                break;
        }
    }

    //获取归属地
    private void getPhone(String str) {
        String url = "https://way.jd.com/jisuapi/query4?shouji="+str+"&appkey="+ StaticClass.JINGDONG_KEY;
        RxVolley.get(url, new HttpCallback() {
            /**
             * Http请求成功时回调
             *
             * @param t HttpRequest返回信息
             */
            @Override
            public void onSuccess(String t) {
                LogUtils.i(this,t);
                parsingJson(t);
            }

        });
    }
    //解析json
    /*
    "result": {
        "status": "0",
        "msg": "ok",
        "result": {
            "shouji": "13456755448",
            "province": "浙江",
            "city": "杭州",
            "company": "中国移动",
            "cardtype": "GSM",
            "areacode": "0571"
        }
    }
     */
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject resultJson = jsonObject.getJSONObject("result");
            JSONObject result = resultJson.getJSONObject("result");
            String phone = result.getString("shouji");
            String province = result.getString("province");
            String city = result.getString("city");
            String company = result.getString("company");
            String cardtype = result.getString("cardtype");
            String areacode = result.getString("areacode");

            tv_result.append("电话号码:"+phone+"\n");
            tv_result.append("地址:"+province+city+"\n");
            tv_result.append("运营商:"+company+"\n");
            tv_result.append("类型:"+cardtype+"\n");
            tv_result.append("区号:"+areacode);
            //设置图片运营商logo
            switch (company){
                case "中国移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "中国联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "中国电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        }catch (Exception e){
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
