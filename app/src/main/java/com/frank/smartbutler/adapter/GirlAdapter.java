package com.frank.smartbutler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.frank.smartbutler.R;
import com.frank.smartbutler.entity.GirlData;
import com.frank.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 文件名:GirlAdapter
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.adapter
 * 创建者:Frank
 * 创建时间:2018/3/14 9:08
 * 描述:      相册适配器
 */

public class GirlAdapter extends BaseAdapter {

    private Context mContext;
    private List<GirlData> mList;
    private WindowManager wm;
    //屏幕宽
    private int width;
    public GirlAdapter(Context mContext,List<GirlData> mList){
        this.mContext = mContext;
        this.mList = mList;
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
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
            convertView = View.inflate(mContext,R.layout.girl_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //解析图片
        String url = mList.get(position).getImgUrl();
        PicassoUtils.loadImageViewSize(mContext,url,width/2,500,viewHolder.imageView);

        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
