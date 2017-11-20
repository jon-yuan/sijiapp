package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class AcountEntity {
    private double ftotal;
    private double foilcard;//油卡金额
    private double ftransport;//现金金额
    private double fgiving;//赠送
    private double fotherin;//其他收入
    private double fotherout;//其他扣除

    public double getFtotal() {
        return ftotal;
    }

    public void setFtotal(double ftotal) {
        this.ftotal = ftotal;
    }

    public double getFoilcard() {
        return foilcard;
    }

    public void setFoilcard(double foilcard) {
        this.foilcard = foilcard;
    }

    public double getFtransport() {
        return ftransport;
    }

    public void setFtransport(double ftransport) {
        this.ftransport = ftransport;
    }

    public double getFgiving() {
        return fgiving;
    }

    public void setFgiving(double fgiving) {
        this.fgiving = fgiving;
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
}
