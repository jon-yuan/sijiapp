package com.babuwyt.siji.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by lenovo on 2018/1/3.
 */

public class SignNoEntity implements IPickerViewData {
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getPickerViewText() {
        return label;
    }
    /**
     *  "value": "1031",
     "label": "5943"
     */
}
