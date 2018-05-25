package com.frank.smartbutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.frank.smartbutler.fragment.ButlerFragment;
import com.frank.smartbutler.fragment.GirlFragment;
import com.frank.smartbutler.fragment.UserFragment;
import com.frank.smartbutler.fragment.WeChatFragment;
import com.frank.smartbutler.ui.SettingActivity;
import com.frank.smartbutler.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String>mTitle;
    private List<Fragment>mFragments;
    private FloatingActionButton fab_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
    }
    //初始化view
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        //默认隐藏按钮
        fab_setting.setVisibility(View.GONE);
        //设置按钮的监听
        fab_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingActivity.class));
            }
        });
        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());
        //设置viewpager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当滑动的时候会调用此方法，在滑动被停止之前，此方法一直被调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //页面跳转后调用position为位置编号
            @Override
            public void onPageSelected(int position) {
                LogUtils.i(this,""+position);
                //在服务管家界面时隐藏设置按钮
                if (position==0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }
            //状态改变时调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            //返回item个数
            @Override
            public int getCount() {
                return mFragments.size();
            }
            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.text_butler_service));
        mTitle.add(getString(R.string.text_wechat));
        mTitle.add(getString(R.string.text_girl));
        mTitle.add(getString(R.string.text_user_info));

        mFragments = new ArrayList<>();
        mFragments.add(new ButlerFragment());
        mFragments.add(new WeChatFragment());
        mFragments.add(new GirlFragment());
        mFragments.add(new UserFragment());
    }
}
