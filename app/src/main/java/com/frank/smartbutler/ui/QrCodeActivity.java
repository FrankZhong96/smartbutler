package com.frank.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.frank.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * 文件名:QrCodeActivity
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.ui
 * 创建者:Frank
 * 创建时间:2018/3/15 17:03
 * 描述:      我的二维码
 */
public class QrCodeActivity  extends BaseActivity{
    //我的二维码
    private ImageView iv_qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
    }

    private void initView() {

        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;
        //生成二维码
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我要扼住命运的咽喉", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }

    @Override
    public void showNextPage() {

    }

    @Override
    public void showPrePage() {

    }
}
