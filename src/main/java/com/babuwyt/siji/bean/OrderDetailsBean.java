package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.OrderDetailsEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lenovo on 2017/9/28.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class OrderDetailsBean extends BaseBean{
    private OrderDetailsEntity obj;

    public OrderDetailsEntity getObj() {
        return obj;
    }

    public void setObj(OrderDetailsEntity obj) {
        this.obj = obj;
    }
}
