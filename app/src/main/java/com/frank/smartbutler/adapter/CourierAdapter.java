package com.frank.smartbutler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 文件名:CourierAdapter
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.adapter
 * 创建者:Frank
 * 创建时间:2018/3/10 20:31
 * 描述:      快递查询list view适配器
 */

public class CourierAdapter extends BaseAdapter {

    private Context mContext;
    private List<CourierData> mList;

    //构造方法
    public CourierAdapter(Context mContext,List<CourierData> mList){
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
        //第一次加载
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.layout_courier_item,null);
            viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_zone = (TextView) convertView.findViewById(R.id.tv_zone);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {//不是第一次加载
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.tv_remark.setText(mList.get(position).getRemark());
        viewHolder.tv_zone.setText(mList.get(position).getZone());
        viewHolder.tv_datetime.setText(mList.get(position).getDatatime());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;

    }
}
