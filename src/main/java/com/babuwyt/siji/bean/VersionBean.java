package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.VersionEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/8/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class VersionBean extends BaseBean {
    private VersionEntity obj;

    public VersionEntity getObj() {
        return obj;
    }

    public void setObj(VersionEntity obj) {
        this.obj = obj;
    }
}
