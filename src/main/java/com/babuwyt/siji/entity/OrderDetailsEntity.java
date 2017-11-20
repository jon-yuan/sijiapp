package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class OrderDetailsEntity {
    private int fstate;
    private String fsendcarno;
    private long fdelivergoodtime;//提货时间
    private long farrivetime;//抵达时间
    private String fpiecenum;//托书数
    private String fbulk;//体积
    private String fvalue;//保值
    private String fremark;//备注
    private ArrayList<LookAddressEntity> loadpick;
    private String fid;
    private String ftrucktype;
    private String fboxtype;
    private String funloadno;
    private String fpickupno;
    private String fweight;
    private String fshortflag;
    private String fofferingtype;
    private String ftransportno;
    private String line;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFtrucktype() {
        return ftrucktype;
    }

    public void setFtrucktype(String ftrucktype) {
        this.ftrucktype = ftrucktype;
    }

    public String getFboxtype() {
        return fboxtype;
    }

    public void setFboxtype(String fboxtype) {
        this.fboxtype = fboxtype;
    }

    public String getFunloadno() {
        return funloadno;
    }

    public void setFunloadno(String funloadno) {
        this.funloadno = funloadno;
    }

    public String getFpickupno() {
        return fpickupno;
    }

    public void setFpickupno(String fpickupno) {
        this.fpickupno = fpickupno;
    }

    public String getFweight() {
        return fweight;
    }

    public void setFweight(String fweight) {
        this.fweight = fweight;
    }

    public String getFshortflag() {
        return fshortflag;
    }

    public void setFshortflag(String fshortflag) {
        this.fshortflag = fshortflag;
    }

    public String getFofferingtype() {
        return fofferingtype;
    }

    public void setFofferingtype(String fofferingtype) {
        this.fofferingtype = fofferingtype;
    }

    public String getFtransportno() {
        return ftransportno;
    }

    public void setFtransportno(String ftransportno) {
        this.ftransportno = ftransportno;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    /**
     *  "fid": 292,
     "fsendcarno": "WD201709080001",
     "ftrucktype": 19,
     "fboxtype": null,
     "fdelivergoodtime": 1505117030000,
     "farrivetime": null,
     "fpickupno": "110105",
     "funloadno": "530111",
     "fpiecenum": 123,
     "fweight": 123,
     "fbulk": 123,
     "fvalue": 123,
     "fshortflag": null,
     "fofferingtype": "TRUCK",
     "fremark": "",
     "ftransportno": "W201709080001",
     "line": null,
     * @return
     */



    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public String getFsendcarno() {
        return fsendcarno;
    }

    public void setFsendcarno(String fsendcarno) {
        this.fsendcarno = fsendcarno;
    }

    public long getFdelivergoodtime() {
        return fdelivergoodtime;
    }

    public void setFdelivergoodtime(long fdelivergoodtime) {
        this.fdelivergoodtime = fdelivergoodtime;
    }

    public long getFarrivetime() {
        return farrivetime;
    }

    public void setFarrivetime(long farrivetime) {
        this.farrivetime = farrivetime;
    }

    public String getFpiecenum() {
        return fpiecenum;
    }

    public void setFpiecenum(String fpiecenum) {
        this.fpiecenum = fpiecenum;
    }

    public String getFbulk() {
        return fbulk;
    }

    public void setFbulk(String fbulk) {
        this.fbulk = fbulk;
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }

    public ArrayList<LookAddressEntity> getLoadpick() {
        return loadpick;
    }

    public void setLoadpick(ArrayList<LookAddressEntity> loadpick) {
        this.loadpick = loadpick;
    }

}
