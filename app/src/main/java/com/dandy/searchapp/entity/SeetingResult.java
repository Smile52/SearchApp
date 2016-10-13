package com.dandy.searchapp.entity;

/**设置 显示结果实体类
 * Created by Smile on 2016/9/22.
 */

public class SeetingResult {
    private String name;//名字
    private boolean status;//选中状态

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
}
