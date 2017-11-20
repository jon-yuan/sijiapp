package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.BankInfoEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BankInfoBean extends BaseBean {
    private BankInfoEntity obj;

    public BankInfoEntity getObj() {
        return obj;
    }

    public void setObj(BankInfoEntity obj) {
        this.obj = obj;
    }
}
