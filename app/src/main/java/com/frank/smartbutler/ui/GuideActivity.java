package com.frank.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名:GuideActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/7 17:30
 * 描述:      引导页
 */
public class GuideActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    //容器
    private List<View> mViewList = new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3,iv_back;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        point1 = (ImageView) findViewById(R.id.iv_point1);
        point2 = (ImageView) findViewById(R.id.iv_point2);
        point3 = (ImageView) findViewById(R.id.iv_point3);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new MyOnClickListener());
        //设置小圆点默认图片
        setPointImg(true,false,false);
        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);
        view3.findViewById(R.id.btn_start).setOnClickListener(new MyOnClickListener());
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        //设置适配器
        mViewPager.setAdapter(new MyPagerAdapter());
        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换
            @Override
            public void onPageSelected(int position) {
                LogUtils.i(this,position+"");
                switch (position){
                    case 0:
                        setPointImg(true,false,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        iv_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置小圆点的选中效果
     */
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if(isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if(isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if(isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                case R.id.iv_back:
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    break;
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter{

        /**
         * 返回item的数量
         */
        @Override
        public int getCount() {
            return mViewList.size();
        }

        /**
         *确定一个页面视图是否与一个特定的键对象相关联，
         * 这个对象是实例化项目（ViewGroup，int）返回的。
         * 这个方法需要一个PagerAdapter才能正常工作。
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //添加item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mViewList.get(position));
            return mViewList.get(position);
        }
        //删除item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mViewList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
