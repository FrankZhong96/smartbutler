package com.frank.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 文件名:MyUser
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.entity
 * 创建者:Frank
 * 创建时间:2018/3/8 18:42
 * 描述:      用户属性
 */

public class MyUser extends BmobUser {
    private int age;
    private boolean sex;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
