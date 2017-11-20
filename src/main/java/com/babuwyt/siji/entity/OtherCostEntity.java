package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/29.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class OtherCostEntity {
    private double fmoney;
    private String fname;

    public double getFmoney() {
        return fmoney;
    }

    public void setFmoney(double fmoney) {
        this.fmoney = fmoney;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
