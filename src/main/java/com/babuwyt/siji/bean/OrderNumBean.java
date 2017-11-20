package com.babuwyt.siji.bean;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/22.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class OrderNumBean extends BaseBean {
    private int obj;

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }
}
