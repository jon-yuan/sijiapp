package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.AcountEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class AcountBean extends BaseBean {
    private AcountEntity obj;

    public AcountEntity getObj() {
        return obj;
    }

    public void setObj(AcountEntity obj) {
        this.obj = obj;
    }
}
