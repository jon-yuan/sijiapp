package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.OtherCostEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/29.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class OtherCostBean extends BaseBean{
    private ArrayList<OtherCostEntity> obj;

    public ArrayList<OtherCostEntity> getObj() {
        return obj;
    }

    public void setObj(ArrayList<OtherCostEntity> obj) {
        this.obj = obj;
    }
}
