package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.BankEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/10/24.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class SubbranchBean extends BaseBean {
    private BankEntity obj;

}
