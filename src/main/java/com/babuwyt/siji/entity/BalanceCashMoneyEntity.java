package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/10/9.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BalanceCashMoneyEntity {
    private double ftotalTranOutAmount;//可提现
    private double ftotalBalance;//可用

    public double getFtotalTranOutAmount() {
        return ftotalTranOutAmount;
    }

    public void setFtotalTranOutAmount(double ftotalTranOutAmount) {
        this.ftotalTranOutAmount = ftotalTranOutAmount;
    }

    public double getFtotalBalance() {
        return ftotalBalance;
    }

    public void setFtotalBalance(double ftotalBalance) {
        this.ftotalBalance = ftotalBalance;
    }

    public String getUse(){
        if (ftotalBalance>0){
            return ftotalBalance+"";
        }
        return "0";
    }
    public String getT(){
        if (ftotalTranOutAmount>0){
            return ftotalTranOutAmount+"";
        }
        return "0";
    }
}
