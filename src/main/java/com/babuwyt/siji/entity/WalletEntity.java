package com.babuwyt.siji.entity;


import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.utils.request.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/9.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class WalletEntity extends BaseBean {
    private BalanceCashMoneyEntity balanceCashMoney;
    private double totalMoney;
    private ArrayList<TranListEntity> tranList;

    public BalanceCashMoneyEntity getBalanceCashMoney() {
        return balanceCashMoney;
    }

    public void setBalanceCashMoney(BalanceCashMoneyEntity balanceCashMoney) {
        this.balanceCashMoney = balanceCashMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }
    public String getTotalMoney1(){
        if (totalMoney>0){
            return totalMoney+"";
        }
        return "0";
    }
    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public ArrayList<TranListEntity> getTranList() {
        return tranList;
    }

    public void setTranList(ArrayList<TranListEntity> tranList) {
        this.tranList = tranList;
    }
    /**
     * "balanceCashMoney": null,
     "tranList": [
     {
     "dealType": 3,
     "dealTime": "2017年09月29 11:40",
     "dealMoney": 5,
     "transportNo": "T201709010006"
     },
     {
     "dealType": 3,
     "dealTime": "2017年09月29 13:56",
     "dealMoney": 100,
     "transportNo": "T201709010006"
     },
     {
     "dealType": 3,
     "dealTime": "2017年09月29 13:59",
     "dealMoney": 100,
     "transportNo": "T201709010006"
     },
     {
     "dealType": 3,
     "dealTime": "2017年09月29 14:10",
     "dealMoney": 10,
     "transportNo": "T201709010006"
     }
     ],
     "totalMoney": 7535
     */
}
