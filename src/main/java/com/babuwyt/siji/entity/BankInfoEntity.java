package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BankInfoEntity implements Serializable {
    private String zbankSname;
    private String zbankNo;
    private String zid;
    private String fbankCard;
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFbankCard() {
        return fbankCard;
    }

    public void setFbankCard(String fbankCard) {
        this.fbankCard = fbankCard;
    }

    public String getZbankSname() {
        return zbankSname==null? bankName : zbankSname;
    }

    public void setZbankSname(String zbankSname) {
        this.zbankSname = zbankSname;
    }

    public String getZbankNo() {
        return zbankNo;
    }

    public void setZbankNo(String zbankNo) {
        this.zbankNo = zbankNo;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }
    /**
     *  "zbankSname": "黄河农村商业银行",
     "zbankNo": "402871099996",
     "zid": null
     */
}
