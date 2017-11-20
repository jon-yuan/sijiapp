package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HistoryOrderItemEntity {
    private String ftotal;
    private String fgoodname;
    private String fremark;
    private int fsettlestate;
    private String fgiving;
    private String ftransport;
    private String foilcard;
    private String fotherin;
    private String fotherout;
    private String fsendcarid;
    private String fsendcarno;
    private String fshipmentarea;
    private String funloadarea;
    private long fshipmenttime;
    private long funloadtime;
    private String fownersendcarid;
    private String fownersendcarno;
    private String facceptratio;

    public String getFgoodname() {
        return fgoodname;
    }

    public void setFgoodname(String fgoodname) {
        this.fgoodname = fgoodname;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }

    public int getFsettlestate() {
        return fsettlestate;
    }

    public void setFsettlestate(int fsettlestate) {
        this.fsettlestate = fsettlestate;
    }

    public String getFtotal() {
        return ftotal;
    }

    public void setFtotal(String ftotal) {
        this.ftotal = ftotal;
    }

    public String getFgiving() {
        return fgiving;
    }

    public void setFgiving(String fgiving) {
        this.fgiving = fgiving;
    }

    public String getFtransport() {
        return ftransport;
    }

    public void setFtransport(String ftransport) {
        this.ftransport = ftransport;
    }

    public String getFoilcard() {
        return foilcard;
    }

    public void setFoilcard(String foilcard) {
        this.foilcard = foilcard;
    }

    public String getFotherin() {
        return fotherin;
    }

    public void setFotherin(String fotherin) {
        this.fotherin = fotherin;
    }

    public String getFotherout() {
        return fotherout;
    }

    public void setFotherout(String fotherout) {
        this.fotherout = fotherout;
    }

    public String getFsendcarid() {
        return fsendcarid;
    }

    public void setFsendcarid(String fsendcarid) {
        this.fsendcarid = fsendcarid;
    }

    public String getFsendcarno() {
        return fsendcarno;
    }

    public void setFsendcarno(String fsendcarno) {
        this.fsendcarno = fsendcarno;
    }

    public String getFshipmentarea() {
        return fshipmentarea;
    }

    public void setFshipmentarea(String fshipmentarea) {
        this.fshipmentarea = fshipmentarea;
    }

    public String getFunloadarea() {
        return funloadarea;
    }

    public void setFunloadarea(String funloadarea) {
        this.funloadarea = funloadarea;
    }

    public long getFshipmenttime() {
        return fshipmenttime;
    }

    public void setFshipmenttime(long fshipmenttime) {
        this.fshipmenttime = fshipmenttime;
    }

    public long getFunloadtime() {
        return funloadtime;
    }

    public void setFunloadtime(long funloadtime) {
        this.funloadtime = funloadtime;
    }

    public String getFownersendcarid() {
        return fownersendcarid;
    }

    public void setFownersendcarid(String fownersendcarid) {
        this.fownersendcarid = fownersendcarid;
    }

    public String getFownersendcarno() {
        return fownersendcarno;
    }

    public void setFownersendcarno(String fownersendcarno) {
        this.fownersendcarno = fownersendcarno;
    }

    public String getFacceptratio() {
        return facceptratio;
    }

    public void setFacceptratio(String facceptratio) {
        this.facceptratio = facceptratio;
    }
    /**
     * {
     "ftotal": 0,
     "foilcard": null,
     "fgiving": null,
     "ftransport": null,
     "fotherin": null,
     "fotherout": null,
     "fsendcarid": 275,
     "fsendcarno": "D7820170901001",
     "fshipmentarea": "西安市",
     "funloadarea": "西安市",
     "fshipmenttime": null,
     "funloadtime": null,
     "fownersendcarid": 227,
     "fownersendcarno": "DD201709010000",
     "facceptratio": "50"
     }
     */
}
