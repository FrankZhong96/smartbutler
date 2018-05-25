package com.frank.smartbutler.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名:AboutActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/15 19:49
 * 描述:      关于软件
 */
public class AboutActivity extends BaseActivity{
    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String>mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    //初始化View
    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);

        mList.add(getString(R.string.text_app_name) + getString(R.string.app_name));
        mList.add(getString(R.string.text_version) + UtilTools.getVersion(this));
        mList.add(getString(R.string.text_website_address));

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        //设置适配器
        mListView.setAdapter(mAdapter);
    }
    @Override
    public void showNextPage() {

    }

    @Override
    public void showPrePage() {

    }
}
