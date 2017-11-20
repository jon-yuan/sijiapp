package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BindBankCarkEntity {
    private String rspCode;
    private String rspMsg;
    private String thirdLogNo;

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

    /**
     * "rspCode": "ERR138",
     "rspMsg": "短信动态码验证不相符",
     "thirdLogNo": null
     */
}
