package com.babuwyt.siji.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/10/9.
 */

public class TransactionEntity implements Serializable,IPickerViewData {
    private String type;
    private int typeId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String getPickerViewText() {
        return this.type;
    }
}
