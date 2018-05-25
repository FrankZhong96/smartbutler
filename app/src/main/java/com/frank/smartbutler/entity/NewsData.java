package com.frank.smartbutler.entity;

/**
 * 文件名:NewsData
 * 项目名:smartbutler
 * 包名:com.frank.smartbutler.entity
 * 创建者:Frank
 * 创建时间:2018/3/12 14:25
 * 描述:      新闻实体类
 */

public class NewsData {
    private String title;
    private String time;
    //新闻来源
    private String src;
    //新闻地址
    private String url;
    //图片地址
    private String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
