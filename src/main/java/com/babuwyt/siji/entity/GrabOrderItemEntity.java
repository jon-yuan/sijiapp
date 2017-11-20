package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/25.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class GrabOrderItemEntity {
    private String fscityname;
    private String fsendcarno;
    private String fucityname;
    private String ftrucktype;
    private String fweight;
    private String fgoodtype;
    private long fdelivergoodtime;
    private String fmobilephone;

    public String getFsendcarno() {
        return fsendcarno;
    }

    public void setFsendcarno(String fsendcarno) {
        this.fsendcarno = fsendcarno;
    }

    public String getFscityname() {
        return fscityname;
    }

    public void setFscityname(String fscityname) {
        this.fscityname = fscityname;
    }

    public String getFucityname() {
        return fucityname;
    }

    public void setFucityname(String fucityname) {
        this.fucityname = fucityname;
    }

    public String getFtrucktype() {
        return ftrucktype;
    }

    public void setFtrucktype(String ftrucktype) {
        this.ftrucktype = ftrucktype;
    }

    public String getFweight() {
        return fweight;
    }

    public void setFweight(String fweight) {
        this.fweight = fweight;
    }

    public String getFgoodtype() {
        return fgoodtype;
    }

    public void setFgoodtype(String fgoodtype) {
        this.fgoodtype = fgoodtype;
    }

    public long getFdelivergoodtime() {
        return fdelivergoodtime;
    }

    public void setFdelivergoodtime(long fdelivergoodtime) {
        this.fdelivergoodtime = fdelivergoodtime;
    }

    public String getFmobilephone() {
        return fmobilephone;
    }

    public void setFmobilephone(String fmobilephone) {
        this.fmobilephone = fmobilephone;
    }

    /**
     * fscityname
     * fucityname
     * ftrucktype
     * fweight
     * fgoodtype
     * fdelivergoodtime
     * fmobilephone
     */
}
