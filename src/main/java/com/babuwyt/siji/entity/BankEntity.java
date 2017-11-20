package com.babuwyt.siji.entity;

import com.babuwyt.siji.utils.request.JsonResponseParser;
import com.bigkoo.pickerview.model.IPickerViewData;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BankEntity implements Serializable,IPickerViewData {

    private String bankName;
    private String bankNo;
    private String zbankSname;
    private String zbankNo;
    private String fbankCard;
    private String zid;
    private String bId;

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getZbankSname() {
        return zbankSname;
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

    public String getFbankCard() {
        return fbankCard;
    }

    public void setFbankCard(String fbankCard) {
        this.fbankCard = fbankCard;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    @Override
    public String getPickerViewText() {
        return this.bankName;
    }
    /**
     *  "bankName": "中国工商银行",
     "bankNo": "102",
     "zbankSname": null,
     "zbankNo": null,
     "fbankCard": null,
     "zid": null
     */


    private String bankno;  //大小额联行号
    private String bankname;  // 支行名称

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    /**
     *  {
     "bankno": "104100005459",
     "status": null,
     "bankclscode": null,
     "citycode": null,
     "bankname": "中国银行股份有限公司北京科技会展中心支行"
     },
     */
}
