package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.GrabOrderItemEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class GrabOrderBean extends BaseBean {
    private int pageCurrent;
    private int itemTotal;
    private int pageSize;
    private int pageCount;
    private int startIndex;
    private Object obj;
    private ArrayList<GrabOrderItemEntity> items;

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public ArrayList<GrabOrderItemEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<GrabOrderItemEntity> items) {
        this.items = items;
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
    /**
     *   "pageCurrent": 1,
     "itemTotal": 0,
     "pageSize": 0,
     "pageCount": 0,
     "startIndex": 0,
     "success": true,
     "msg": "当前没有任务!",
     "obj": null,
     "items": []
     */
}
