package com.frank.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.adapter.NewsAdapter;
import com.frank.smartbutler.entity.NewsData;
import com.frank.smartbutler.ui.WebViewActivity;
import com.frank.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名:WeChatFragment
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.fragment
 * 创建者:Frank
 * 创建时间:2018/3/7 13:33
 * 描述:      微信精选
 */

public class WeChatFragment extends Fragment {
    private ImageView iv_img;
    private TextView tv_title, tv_time, tv_source;
    private ListView mListView;
    private List<NewsData> mlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        //条目点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",mlist.get(position).getTitle());
                intent.putExtra("url",mlist.get(position).getUrl());
                startActivity(intent);
            }
        });
        //解析接口
        String url = "https://way.jd.com/jisuapi/get?channel=新闻&num=40&start=0&appkey="+ StaticClass.JINGDONG_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            // {}: JSONObject对象 []: JSONArray
            JSONObject root_json = new JSONObject(t);// 将一个字符串转换一个json对象
            // 获取root_json中的“result”作为JSONArray对象
            JSONObject result_json = root_json.getJSONObject("result")
                    .getJSONObject("result");
            JSONArray jsonArray = result_json.getJSONArray("list");
            NewsData newsData = null;
            for (int i = 0; i < jsonArray.length(); i++) {// 循环遍历JSONArray
                JSONObject news_json = jsonArray.getJSONObject(i);// 获取一条新闻json
                newsData = new NewsData();
                newsData.setTitle(news_json.getString("title"));//标题
                newsData.setSrc(news_json.getString("src"));//类型
                newsData.setUrl(news_json.getString("url"));//新闻地址
                newsData.setPic(news_json.getString("pic"));//图片地址
                newsData.setTime(news_json.getString("time"));//地址
                mlist.add(newsData);
            }
            NewsAdapter adapter = new NewsAdapter(getActivity(),mlist);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
