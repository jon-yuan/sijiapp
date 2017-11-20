package com.babuwyt.siji.finals;

/**
 * Created by lenovo on 2017/9/12.
 */

public class DataSynEvent {
    public static final int DRAWER_LAYOUT_OPEN=0x00001;//打开侧滑
    public static final int DATA_SYNEVENT_CODE1=0X00001;
    public static final int DATA_SYNEVENT_CODE2=0X00002;
    private int type;
    private String msg;
    private Object object;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
