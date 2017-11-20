package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/22.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class UserInfoEntity {
    private String fdriverid;
    private String fdrivername;
    private String fphone;
    private String carid;
    private String fplateno;
    private int fisdelete;
    private String webtoken;
    private double income;

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getFdriverid() {
        return fdriverid;
    }

    public void setFdriverid(String fdriverid) {
        this.fdriverid = fdriverid;
    }

    public String getFdrivername() {
        return fdrivername;
    }

    public void setFdrivername(String fdrivername) {
        this.fdrivername = fdrivername;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getFplateno() {
        return fplateno;
    }

    public void setFplateno(String fplateno) {
        this.fplateno = fplateno;
    }

    public int getFisdelete() {
        return fisdelete;
    }

    public void setFisdelete(int fisdelete) {
        this.fisdelete = fisdelete;
    }

    public String getWebtoken() {
        return webtoken;
    }

    public void setWebtoken(String webtoken) {
        this.webtoken = webtoken;
    }


    /**
     *  "fdriverid": 78,
     "fdrivername": "袁伟",
     "fphone": "18717372920",
     "carid": 68,
     "fplateno": "陕E02569",
     "fisdelete": 1,
     "webtoken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0RHJpdmVyIjp7ImZkcml2ZXJpZCI6NzgsImZkcml2ZXJuYW1lIjoi6KKB5LyfIiwiZnBob25lIjoiMTg3MTczNzI5MjAiLCJjYXJpZCI6NjgsImZwbGF0ZW5vIjoi6ZmVRTAyNTY5IiwiZmlzZGVsZXRlIjoxLCJ3ZWJ0b2tlbiI6bnVsbH0sImlzcyI6InRtc2FwaXVzZXIiLCJleHAiOjE1MDczNjM2NDQsIm5iZiI6MTUwNjA2NzY0NH0.96acIFZM8SDI4hCcySgLqT-ZcMkrnmRx9C05xGlBYPU"
     */

    private String fid;
    private String fcard;
    private String furgencylineman;
    private String flinemanphone;
    private String fdrivingcard;
    private String fdrivingtime;
    private String fparenttype;
    private String fparentid;
    private String fbankid;
    private String fbankcard;
    private String favatar;
    private String fcardpicture;
    private String fcardpicturerear;
    private String fhandcardpicture;
    private String fdrivingpicture;
    private int fcautionmoney;
    private String fmoneystate;
    private String fidrs;
    private String fremark;
    private String fcreateid;
    private String fcreatetime;
    private String fauditid;
    private String faudittime;
    private String fdrivercode;
    private String fdrivernum;
    private String fname;
    private String fattribution;
    private String fviolations;
    private String ffinishcount;
    private String fotherout;
    private String faccount;
    private String cartype;
    private String fbankname;
    private String ftrucktypeid;
    private String fdrivenumber;
    private String fdrivecard;
    private int fispay;//2是结算 3不结算

    public int getFispay() {
        return fispay;
    }

    public void setFispay(int fispay) {
        this.fispay = fispay;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFcard() {
        return fcard;
    }

    public void setFcard(String fcard) {
        this.fcard = fcard;
    }

    public String getFurgencylineman() {
        return furgencylineman;
    }

    public void setFurgencylineman(String furgencylineman) {
        this.furgencylineman = furgencylineman;
    }

    public String getFlinemanphone() {
        return flinemanphone;
    }

    public void setFlinemanphone(String flinemanphone) {
        this.flinemanphone = flinemanphone;
    }

    public String getFdrivingcard() {
        return fdrivingcard;
    }

    public void setFdrivingcard(String fdrivingcard) {
        this.fdrivingcard = fdrivingcard;
    }

    public String getFdrivingtime() {
        return fdrivingtime;
    }

    public void setFdrivingtime(String fdrivingtime) {
        this.fdrivingtime = fdrivingtime;
    }

    public String getFparenttype() {
        return fparenttype;
    }

    public void setFparenttype(String fparenttype) {
        this.fparenttype = fparenttype;
    }

    public String getFparentid() {
        return fparentid;
    }

    public void setFparentid(String fparentid) {
        this.fparentid = fparentid;
    }

    public String getFbankid() {
        return fbankid;
    }

    public void setFbankid(String fbankid) {
        this.fbankid = fbankid;
    }

    public String getFbankcard() {
        return fbankcard;
    }

    public void setFbankcard(String fbankcard) {
        this.fbankcard = fbankcard;
    }

    public String getFavatar() {
        return favatar;
    }

    public void setFavatar(String favatar) {
        this.favatar = favatar;
    }

    public String getFcardpicture() {
        return fcardpicture;
    }

    public void setFcardpicture(String fcardpicture) {
        this.fcardpicture = fcardpicture;
    }

    public String getFcardpicturerear() {
        return fcardpicturerear;
    }

    public void setFcardpicturerear(String fcardpicturerear) {
        this.fcardpicturerear = fcardpicturerear;
    }

    public String getFhandcardpicture() {
        return fhandcardpicture;
    }

    public void setFhandcardpicture(String fhandcardpicture) {
        this.fhandcardpicture = fhandcardpicture;
    }

    public String getFdrivingpicture() {
        return fdrivingpicture;
    }

    public void setFdrivingpicture(String fdrivingpicture) {
        this.fdrivingpicture = fdrivingpicture;
    }

    public int getFcautionmoney() {
        return fcautionmoney;
    }

    public void setFcautionmoney(int fcautionmoney) {
        this.fcautionmoney = fcautionmoney;
    }

    public String getFmoneystate() {
        return fmoneystate;
    }

    public void setFmoneystate(String fmoneystate) {
        this.fmoneystate = fmoneystate;
    }

    public String getFidrs() {
        return fidrs;
    }

    public void setFidrs(String fidrs) {
        this.fidrs = fidrs;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }

    public String getFcreateid() {
        return fcreateid;
    }

    public void setFcreateid(String fcreateid) {
        this.fcreateid = fcreateid;
    }

    public String getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(String fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getFauditid() {
        return fauditid;
    }

    public void setFauditid(String fauditid) {
        this.fauditid = fauditid;
    }

    public String getFaudittime() {
        return faudittime;
    }

    public void setFaudittime(String faudittime) {
        this.faudittime = faudittime;
    }

    public String getFdrivercode() {
        return fdrivercode;
    }

    public void setFdrivercode(String fdrivercode) {
        this.fdrivercode = fdrivercode;
    }

    public String getFdrivernum() {
        return fdrivernum;
    }

    public void setFdrivernum(String fdrivernum) {
        this.fdrivernum = fdrivernum;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFattribution() {
        return fattribution;
    }

    public void setFattribution(String fattribution) {
        this.fattribution = fattribution;
    }

    public String getFviolations() {
        return fviolations;
    }

    public void setFviolations(String fviolations) {
        this.fviolations = fviolations;
    }

    public String getFfinishcount() {
        return ffinishcount;
    }

    public void setFfinishcount(String ffinishcount) {
        this.ffinishcount = ffinishcount;
    }

    public String getFotherout() {
        return fotherout;
    }

    public void setFotherout(String fotherout) {
        this.fotherout = fotherout;
    }

    public String getFaccount() {
        return faccount;
    }

    public void setFaccount(String faccount) {
        this.faccount = faccount;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getFbankname() {
        return fbankname;
    }

    public void setFbankname(String fbankname) {
        this.fbankname = fbankname;
    }

    public String getFtrucktypeid() {
        return ftrucktypeid;
    }

    public void setFtrucktypeid(String ftrucktypeid) {
        this.ftrucktypeid = ftrucktypeid;
    }

    public String getFdrivenumber() {
        return fdrivenumber;
    }

    public void setFdrivenumber(String fdrivenumber) {
        this.fdrivenumber = fdrivenumber;
    }

    public String getFdrivecard() {
        return fdrivecard;
    }

    public void setFdrivecard(String fdrivecard) {
        this.fdrivecard = fdrivecard;
    }
    /**
     * "fid": 78,
     "fdrivername": "袁伟",
     "fcard": "610502198901081216",
     "fphone": "18717372920",
     "furgencylineman": null,
     "flinemanphone": null,
     "fdrivingcard": "610502198901081216",
     "fdrivingtime": null,
     "fparenttype": null,
     "fparentid": null,
     "fbankid": 1,
     "fbankcard": "64643494678434843",
     "favatar": null,
     "fcardpicture": "2017/07/0978/00f57fa108884560b3031c783250f1be.jpg",
     "fcardpicturerear": "2017/07/0978/b441c8d6a42f46c1a1afe935704619a1.jpg",
     "fhandcardpicture": "2017/07/0978/22b50d53220943f3a3dadd73b0f92f2f.jpg",
     "fdrivingpicture": "2017/07/0978/204d8414ebc74e7fad49b7d1b4bcde6f.jpg",
     "fcautionmoney": 1000,
     "fmoneystate": 2,
     "fidrs": null,
     "fremark": null,
     "fisdelete": 1,
     "fcreateid": null,
     "fcreatetime": null,
     "fauditid": 1,
     "faudittime": 1499565293000,
     "fdrivercode": null,
     "fdrivernum": null,
     "fname": null,
     "fattribution": "袁伟",
     "fplateno": "陕E02569",
     "fviolations": 0,
     "ffinishcount": 0,
     "fotherout": null,
     "faccount": 0,
     "cartype": "13米",
     "fbankname": "中国银行",
     "carid": null,
     "ftrucktypeid": 20,
     "fdrivenumber": "610502198901081216",
     "fdrivecard": "2017/07/0978/94638f31835e47b0a95ccc946685594f.jpg"
     */
}
