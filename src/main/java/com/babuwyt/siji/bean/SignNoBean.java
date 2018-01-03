package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.SignNoEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/1/3.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class SignNoBean extends BaseBean {
    public ArrayList <SignNoEntity> obj;

    public ArrayList<SignNoEntity> getObj() {
        return obj;
    }

    public void setObj(ArrayList<SignNoEntity> obj) {
        this.obj = obj;
    }
}
