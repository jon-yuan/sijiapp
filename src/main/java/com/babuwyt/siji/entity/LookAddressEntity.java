package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LookAddressEntity {
    private String fid;
    private String ftransportno;
    private String fromto;
    private String faddress;
    private String fcustomername;
    private String fdistrict;
    private String flinkman;
    private String fphone;
    private String fseq;
    private String fromtoloadid;
    private String pickcount;
    private String unloadcount;
    private String ssq;
    private String ownsendcarno;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFtransportno() {
        return ftransportno;
    }

    public void setFtransportno(String ftransportno) {
        this.ftransportno = ftransportno;
    }

    public String getFromto() {
        return fromto;
    }

    public void setFromto(String fromto) {
        this.fromto = fromto;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getFcustomername() {
        return fcustomername;
    }

    public void setFcustomername(String fcustomername) {
        this.fcustomername = fcustomername;
    }

    public String getFdistrict() {
        return fdistrict;
    }

    public void setFdistrict(String fdistrict) {
        this.fdistrict = fdistrict;
    }

    public String getFlinkman() {
        return flinkman;
    }

    public void setFlinkman(String flinkman) {
        this.flinkman = flinkman;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFseq() {
        return fseq;
    }

    public void setFseq(String fseq) {
        this.fseq = fseq;
    }

    public String getFromtoloadid() {
        return fromtoloadid;
    }

    public void setFromtoloadid(String fromtoloadid) {
        this.fromtoloadid = fromtoloadid;
    }

    public String getPickcount() {
        return pickcount;
    }

    public void setPickcount(String pickcount) {
        this.pickcount = pickcount;
    }

    public String getUnloadcount() {
        return unloadcount;
    }

    public void setUnloadcount(String unloadcount) {
        this.unloadcount = unloadcount;
    }

    public String getSsq() {
        return ssq;
    }

    public void setSsq(String ssq) {
        this.ssq = ssq;
    }

    public String getOwnsendcarno() {
        return ownsendcarno;
    }

    public void setOwnsendcarno(String ownsendcarno) {
        this.ownsendcarno = ownsendcarno;
    }

    /**
     *  {
     "fid": 513,
     "ftransportno": 227,
     "fromto": "FROM",
     "faddress": "开元东路15号",
     "fcustomername": "TE",
     "fdistrict": "610111",
     "flinkman": "赵彦鹏",
     "fphone": "13259511726",
     "fseq": 1,
     "fromtoloadid": "FMDD201709010000-1",
     "pickcount": 1,
     "unloadcount": 1,
     "ssq": "陕西省西安市灞桥区",
     "ownsendcarno": "DD201709010000"
     },
     */
}
