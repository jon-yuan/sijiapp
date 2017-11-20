package com.babuwyt.siji.entity;


import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LookPicEntity  {
    private String fpicture;
    private String faddressNo;
    private ArrayList<PicEntity> httpSignPictureRess;

    public String getFpicture() {
        return fpicture;
    }

    public void setFpicture(String fpicture) {
        this.fpicture = fpicture;
    }

    public ArrayList<PicEntity> getHttpSignPictureRess() {
        return httpSignPictureRess;
    }

    public void setHttpSignPictureRess(ArrayList<PicEntity> httpSignPictureRess) {
        this.httpSignPictureRess = httpSignPictureRess;
    }

    public String getFaddressNo() {
        return faddressNo;
    }

    public void setFaddressNo(String faddressNo) {
        this.faddressNo = faddressNo;
    }
}
