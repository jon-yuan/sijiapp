package com.babuwyt.siji.entity.bank;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/23.
 */

public class ProvinceEntity implements Serializable,IPickerViewData {

    private String node_nodecode;
    private String node_nodename;
    private ArrayList<CityEntity> listShi;

    public String getNode_nodecode() {
        return node_nodecode;
    }

    public void setNode_nodecode(String node_nodecode) {
        this.node_nodecode = node_nodecode;
    }

    public String getNode_nodename() {
        return node_nodename;
    }

    public void setNode_nodename(String node_nodename) {
        this.node_nodename = node_nodename;
    }

    public ArrayList<CityEntity> getListShi() {
        return listShi;
    }

    public void setListShi(ArrayList<CityEntity> listShi) {
        this.listShi = listShi;
    }

    @Override
    public String getPickerViewText() {
        return this.node_nodename;
    }
}
