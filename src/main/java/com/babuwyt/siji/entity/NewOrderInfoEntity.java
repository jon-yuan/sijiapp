package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/22.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class NewOrderInfoEntity {
    private String fid;
    private String ownsendcarid;
    private String pickcount;
    private String unloadcount;
    private String fdriverid;
    private double cash;
    private String ftaskstate;
    private String fsettlestate;
    private double fshouldpay;
    private String othermoney;
    private double fshouldpayOilcard;
    private double fshouldreturnmoney;
    private String fpickupno;
    private String funloadno;
    private String start;
    private String end;
    private String truepicktime;
    private String trueunloadtime;
    private String prepicktime;
    private String preunloadtime;
    private String fsendcarno;
    private double fotherin;
    private double fotherout;
    private String fvicecard;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getOwnsendcarid() {
        return ownsendcarid;
    }

    public void setOwnsendcarid(String ownsendcarid) {
        this.ownsendcarid = ownsendcarid;
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

    public String getFdriverid() {
        return fdriverid;
    }

    public void setFdriverid(String fdriverid) {
        this.fdriverid = fdriverid;
    }



    public String getFtaskstate() {
        return ftaskstate;
    }

    public void setFtaskstate(String ftaskstate) {
        this.ftaskstate = ftaskstate;
    }

    public String getFsettlestate() {
        return fsettlestate;
    }

    public void setFsettlestate(String fsettlestate) {
        this.fsettlestate = fsettlestate;
    }



    public String getOthermoney() {
        return othermoney;
    }

    public void setOthermoney(String othermoney) {
        this.othermoney = othermoney;
    }






    public String getFpickupno() {
        return fpickupno;
    }

    public void setFpickupno(String fpickupno) {
        this.fpickupno = fpickupno;
    }

    public String getFunloadno() {
        return funloadno;
    }

    public void setFunloadno(String funloadno) {
        this.funloadno = funloadno;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTruepicktime() {
        return truepicktime;
    }

    public void setTruepicktime(String truepicktime) {
        this.truepicktime = truepicktime;
    }

    public String getTrueunloadtime() {
        return trueunloadtime;
    }

    public void setTrueunloadtime(String trueunloadtime) {
        this.trueunloadtime = trueunloadtime;
    }

    public String getPrepicktime() {
        return prepicktime;
    }

    public void setPrepicktime(String prepicktime) {
        this.prepicktime = prepicktime;
    }

    public String getPreunloadtime() {
        return preunloadtime;
    }

    public void setPreunloadtime(String preunloadtime) {
        this.preunloadtime = preunloadtime;
    }

    public String getFsendcarno() {
        return fsendcarno;
    }

    public void setFsendcarno(String fsendcarno) {
        this.fsendcarno = fsendcarno;
    }



    public String getFvicecard() {
        return fvicecard;
    }

    public void setFvicecard(String fvicecard) {
        this.fvicecard = fvicecard;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getFshouldpay() {
        return fshouldpay;
    }

    public void setFshouldpay(double fshouldpay) {
        this.fshouldpay = fshouldpay;
    }

    public double getFshouldpayOilcard() {
        return fshouldpayOilcard;
    }

    public void setFshouldpayOilcard(double fshouldpayOilcard) {
        this.fshouldpayOilcard = fshouldpayOilcard;
    }

    public double getFshouldreturnmoney() {
        return fshouldreturnmoney;
    }

    public void setFshouldreturnmoney(double fshouldreturnmoney) {
        this.fshouldreturnmoney = fshouldreturnmoney;
    }

    public double getFotherin() {
        return fotherin;
    }

    public void setFotherin(double fotherin) {
        this.fotherin = fotherin;
    }

    public double getFotherout() {
        return fotherout;
    }

    public void setFotherout(double fotherout) {
        this.fotherout = fotherout;
    }
    /**
     * "fid": 275,
     "ownsendcarid": 227,
     "pickcount": 1,
     "unloadcount": 1,
     "fdriverid": 78,
     "cash": 500,
     "ftaskstate": 0,
     "fsettlestate": 0,
     "fshouldpay": 1000,
     "othermoney": 0,
     "fshouldpayOilcard": 500,
     "fshouldreturnmoney": 0,
     "fpickupno": "610111",
     "funloadno": "610116",
     "start": "陕西省西安市灞桥区开元东路15号",
     "end": "陕西省西安市长安区航天中路388号",
     "truepicktime": null,
     "trueunloadtime": null,
     "prepicktime": 1565164800000,
     "preunloadtime": null,
     "fsendcarno": "D7820170901001",
     "fotherin": null,
     "fotherout": null,
     "fvicecard": null
     */
}
