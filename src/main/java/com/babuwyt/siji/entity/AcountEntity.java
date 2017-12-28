package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class AcountEntity {
    private String ftotal;
    private String foilcard;//油卡金额
    private String ftransport;//现金金额
    private String fgiving;//赠送
    private String fotherin;//其他收入
    private String fotherout;//其他扣除

    public String getFtotal() {
        return ftotal;
    }

    public void setFtotal(String ftotal) {
        this.ftotal = ftotal;
    }

    public String getFoilcard() {
        return foilcard;
    }

    public void setFoilcard(String foilcard) {
        this.foilcard = foilcard;
    }

    public String getFtransport() {
        return ftransport;
    }

    public void setFtransport(String ftransport) {
        this.ftransport = ftransport;
    }

    public String getFgiving() {
        return fgiving;
    }

    public void setFgiving(String fgiving) {
        this.fgiving = fgiving;
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
}
