package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/10/9.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class TranListEntity {
    private int dealType;
    private String dealTime;
    private String transportNo;
    private double dealMoney;

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public double getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(double dealMoney) {
        this.dealMoney = dealMoney;
    }
    /**
     * "dealType": 3,
     "dealTime": "2017年09月29 11:40",
     "dealMoney": 5,
     "transportNo": "T201709010006"
     */
}
