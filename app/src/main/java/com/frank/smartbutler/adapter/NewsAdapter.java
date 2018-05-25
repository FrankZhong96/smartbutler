package com.frank.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.entity.NewsData;
import com.frank.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 文件名:NewsAdapter
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.adapter
 * 创建者:Frank
 * 创建时间:2018/3/12 14:25
 * 描述:      新闻适配器
 */

public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsData> mList;

    public NewsAdapter(Context mContext,List<NewsData> mList){
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.news_item,null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_title.setText(mList.get(position).getTitle());
        viewHolder.tv_time.setText(mList.get(position).getTime());
        viewHolder.tv_source.setText(mList.get(position).getSrc());
        if(!TextUtils.isEmpty(mList.get(position).getPic())){
            //加载图片
            PicassoUtils.loadImageViewSize(mContext, mList.get(position).getPic(), 300, 250, viewHolder.iv_img);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_source;
    }

}
