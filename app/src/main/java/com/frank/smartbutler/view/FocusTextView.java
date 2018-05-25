package com.frank.smartbutler.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 文件名:FocusTextView
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.view
 * 创建者:Frank
 * 创建时间:2018/3/11 9:48
 * 描述:      能够获取焦点的自定义TextView
 */

public class FocusTextView extends TextView {
    //使用在通过java代码创建控件
    public FocusTextView(Context context) {
        super(context);
    }
    //由系统调用(带属性+上下文环境构造方法)
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //由系统调用(带属性+上下文环境构造方法+布局文件中定义样式文件构造方法)
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写获取焦点的方法,由系统调用,调用的时候默认就能获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
