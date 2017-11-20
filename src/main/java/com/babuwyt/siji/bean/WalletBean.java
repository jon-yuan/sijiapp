package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.WalletEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/10/9.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class WalletBean  extends BaseBean{
    private WalletEntity obj;

    public WalletEntity getObj() {
        return obj;
    }

    public void setObj(WalletEntity obj) {
        this.obj = obj;
    }
}
