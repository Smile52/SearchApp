package com.dandy.searchapp.entity;

/**搜索引擎实体类
 * Created by Smile on 2016/9/22.
 */

public class SeetingIntent {
    private int icon;//图片
    private String name;//名字
    private boolean status;//选中状态

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SeetingIntent{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
