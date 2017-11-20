package com.babuwyt.siji.bean;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class XieyiBean extends BaseBean{
    private String obj;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
