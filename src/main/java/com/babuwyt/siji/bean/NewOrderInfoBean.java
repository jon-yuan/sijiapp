package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.NewOrderInfoEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/22.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class NewOrderInfoBean extends BaseBean {
    private ArrayList<NewOrderInfoEntity> obj;

    public ArrayList<NewOrderInfoEntity> getObj() {
        return obj;
    }

    public void setObj(ArrayList<NewOrderInfoEntity> obj) {
        this.obj = obj;
    }
}
