package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class PicBean extends BaseBean {
    private ArrayList<PicEntity> obj;

    public ArrayList<PicEntity> getObj() {
        return obj;
    }

    public void setObj(ArrayList<PicEntity> obj) {
        this.obj = obj;
    }
}
