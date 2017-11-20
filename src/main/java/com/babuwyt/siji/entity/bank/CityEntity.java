package com.babuwyt.siji.entity.bank;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/23.
 */

public class CityEntity implements Serializable,IPickerViewData {

    private String city_areacode;
    private String city_areaname;
    private String city_areatype;
    private String city_nodecode;
    private String city_topareacode2;
    private String city_oraareacode;
    private ArrayList<AreaEntity> qulist;

    public String getCity_areacode() {
        return city_areacode;
    }

    public void setCity_areacode(String city_areacode) {
        this.city_areacode = city_areacode;
    }

    public String getCity_areaname() {
        return city_areaname;
    }

    public void setCity_areaname(String city_areaname) {
        this.city_areaname = city_areaname;
    }

    public String getCity_areatype() {
        return city_areatype;
    }

    public void setCity_areatype(String city_areatype) {
        this.city_areatype = city_areatype;
    }

    public String getCity_nodecode() {
        return city_nodecode;
    }

    public void setCity_nodecode(String city_nodecode) {
        this.city_nodecode = city_nodecode;
    }

    public String getCity_topareacode2() {
        return city_topareacode2;
    }

    public void setCity_topareacode2(String city_topareacode2) {
        this.city_topareacode2 = city_topareacode2;
    }

    public String getCity_oraareacode() {
        return city_oraareacode;
    }

    public void setCity_oraareacode(String city_oraareacode) {
        this.city_oraareacode = city_oraareacode;
    }

    public ArrayList<AreaEntity> getQulist() {
        return qulist;
    }

    public void setQulist(ArrayList<AreaEntity> qulist) {
        this.qulist = qulist;
    }

    @Override
    public String getPickerViewText() {
        return this.city_areaname;
    }
}
