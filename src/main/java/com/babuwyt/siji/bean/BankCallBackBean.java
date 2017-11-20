package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.BankCallBackEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/29.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BankCallBackBean extends BaseBean{
    private BankCallBackEntity obj;

    public BankCallBackEntity getObj() {
        return obj;
    }

    public void setObj(BankCallBackEntity obj) {
        this.obj = obj;
    }
}
