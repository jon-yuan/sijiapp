package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.CarTypeEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/27.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class CarTypeBean extends BaseBean {
    private ArrayList<CarTypeEntity> obj;

    public ArrayList<CarTypeEntity> getObj() {
        return obj;
    }

    public void setObj(ArrayList<CarTypeEntity> obj) {
        this.obj = obj;
    }
}
