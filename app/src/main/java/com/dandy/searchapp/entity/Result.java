package com.dandy.searchapp.entity;

import android.graphics.drawable.Drawable;

/**搜索返回结果实体类
 * Created by Smile on 2016/10/8.
 */

public class Result {
    private String name;//名字
    private String event;//触发事件条件
    private Drawable icon;//图标
    private String detail;//描述

    public Result() {
    }

    public Result(String name, String event, Drawable icon) {
        this.name = name;
        this.event = event;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", event='" + event + '\'' +
                ", icon=" + icon +
                ", detail='" + detail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o==null){
            return false;
        }
        if (this==o){
            return true;
        }
        Result result= (Result) o;
        if (this.getEvent().equals(result.getEvent())&&this.getName().equals(result.getName())){
            return true;
        }

        return false;
    }
}
