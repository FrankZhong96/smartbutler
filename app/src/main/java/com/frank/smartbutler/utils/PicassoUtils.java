package com.frank.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 文件名:PicassoUtils
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.utils
 * 创建者:Frank
 * 创建时间:2018/3/13 18:55
 * 描述:      Picasso封装
 */

public class PicassoUtils {

    /**
     * 默认加载图片
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadImaheView(Context mContext, String url, ImageView imageView) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    /**
     * 默认加载图片(指定大小)
     * @param mContext
     * @param url
     * @param width
     * @param height
     * @param imageView
     */
    public static void loadImageViewSize(Context mContext, String url, int width, int height, ImageView imageView) {
        Picasso.with(mContext).load(url).config(Bitmap.Config.RGB_565).resize(width, height).centerCrop().into(imageView);
    }

    /**
     * 加载图片有默认图片
     */
    public static void loadImageViewHolder(Context mContext, String url, int loadImg,
                                           int errorImg, ImageView imageView) {
        Picasso.with(mContext).load(url).placeholder(loadImg).error(errorImg)
                .into(imageView);
    }

    /**
     * 裁剪图片
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadImageViewCrop(Context mContext, String url, ImageView imageView){
        Picasso.with(mContext).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    /**
     * 按比例裁剪 矩形
     */
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "Frank";
        }
    }
}
