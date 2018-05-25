package com.frank.smartbutler.entity;

/**
 * 文件名:ChatListData
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.entity
 * 创建者:Frank
 * 创建时间:2018/3/11 18:40
 * 描述:      对话列表实体类
 */

public class ChatListData {
    //文本
    private String text;
    //type
    private int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
