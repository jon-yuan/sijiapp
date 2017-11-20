package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;
import com.bigkoo.pickerview.model.IPickerViewData;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/9/27.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class CarTypeEntity implements Serializable,IPickerViewData {
    private String fid;
    private String fname;
    private String fstate;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    @Override
    public String getPickerViewText() {
        return this.fname;
    }
    /**
     *  {
     "fid": 19,
     "fname": "17.5米",
     "fstate": 1
     },
     */
}
