package com.babuwyt.siji.entity;

import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/29.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BankCallBackEntity {
    private String rspCode;
    private String rspMsg;
    private String thirdLogNo;
    private String curBalance;
    private String serialNo;
    private String frontLogNo;
    private String revMobilePhone;
    private String lastBalance;
    private String custAcctId;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getThirdLogNo() {
        return thirdLogNo;
    }

    public void setThirdLogNo(String thirdLogNo) {
        this.thirdLogNo = thirdLogNo;
    }

    public String getCurBalance() {
        return curBalance;
    }

    public void setCurBalance(String curBalance) {
        this.curBalance = curBalance;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getFrontLogNo() {
        return frontLogNo;
    }

    public void setFrontLogNo(String frontLogNo) {
        this.frontLogNo = frontLogNo;
    }

    public String getRevMobilePhone() {
        return revMobilePhone;
    }

    public void setRevMobilePhone(String revMobilePhone) {
        this.revMobilePhone = revMobilePhone;
    }

    public String getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(String lastBalance) {
        this.lastBalance = lastBalance;
    }

    public String getCustAcctId() {
        return custAcctId;
    }

    public void setCustAcctId(String custAcctId) {
        this.custAcctId = custAcctId;
    }
    /**
     *  "rspCode": "000000",
     "rspMsg": "交易成功",
     "thirdLogNo": "20170929115113271060",
     "curBalance": null,
     "serialNo": "17092900407091",
     "frontLogNo": null,
     "revMobilePhone": "3619",
     "lastBalance": null,
     "custAcctId": null
     */
}
