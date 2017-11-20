package com.babuwyt.siji.bean;

import com.babuwyt.siji.entity.HistoryOrderItemEntity;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/26.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HistoryOrderBean extends BaseBean {
    private int pageCurrent;
    private int itemTotal;
    private int pageSize;
    private int pageCount;
    private int startIndex;
    private HistoryOrderItemEntity obj;
    private ArrayList<HistoryOrderItemEntity> items;

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

    public ArrayList<HistoryOrderItemEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<HistoryOrderItemEntity> items) {
        this.items = items;
    }

    public HistoryOrderItemEntity getObj() {
        return obj;
    }

    public void setObj(HistoryOrderItemEntity obj) {
        this.obj = obj;
    }
}
