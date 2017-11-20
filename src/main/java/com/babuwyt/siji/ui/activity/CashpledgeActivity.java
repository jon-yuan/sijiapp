package com.babuwyt.siji.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BankCallBackBean;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.entity.BankInfoEntity;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.DataSynEvent;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.GetPriceDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by lenovo on 2017/10/9.
 */
@ContentView(R.layout.activity_cashpledge)
public class CashpledgeActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_backname)
    TextView tv_backname;
    @ViewInject(R.id.tv_msg)
    TextView tv_msg;
    @ViewInject(R.id.tv_commit)
    TextView tv_commit;
    private BankInfoEntity entity;
    private int type;
    private String SerialNo="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity= (BankInfoEntity) getIntent().getSerializableExtra("obj");
        type=getIntent().getIntExtra("type",0);//1 交纳  2 提现
        init();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (entity!=null){
            toolbar.setTitle(type==1?getString(R.string.jiaona):getString(R.string.tuihuan));
            tv_msg.setText(type==1?getString(R.string.jiaonajine):getString(R.string.tuihuanjine));
            tv_commit.setText(type==1?getString(R.string.jiaona):getString(R.string.tuihuan));
            int size = entity.getFbankCard().length();
            if (size>4){
                final String str = entity.getFbankCard().substring(size - 4, size);
                tv_backname.setText(entity.getZbankSname()+"("+str+")");
            }
        }
    }

    @Event(value = {R.id.tv_commit})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_commit:
                isType();
                break;
        }
    }

    private void isType(){
        if (type==1){
            jiaoNa(type);
        }
        if (type==2){
            tuiHuan();
        }
    }
    private void jiaoNa(int type){
        costMsg(1000,type);
    }
    private void tuiHuan(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(SessionManager.getInstance().getUser().getFdriverid());
        XUtil.GetPing(BaseURL.SENDMESSAGE,list,new ResponseCallBack<BaseBean>(){
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                if (result.isSuccess()){
                    UHelper.showToast(CashpledgeActivity.this,getString(R.string.tuihuanbaozhengjin));
                    finish();
                }else {
                    UHelper.showToast(CashpledgeActivity.this,result.getMsg());
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }
    /**
     * 发送验证码
     */
    //MSG_DYNAMICCODE
    private void costMsg(final double price, final int type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
        map.put("tranType", 2);
        map.put("tranAmount", price);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.MSG_DYNAMICCODE, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankCallBackBean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(BankCallBackBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
//                    getPriceDialog.setDownTime();
//                    SerialNo=result.getObj().getSerialNo();
//                    tixian(price,backnum,backName);
                    SerialNo =result.getObj().getSerialNo();
                    isType(type);
                }else {
                    UHelper.showToast(CashpledgeActivity.this,result.getMsg());
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void isType(int type){
        if (type==1){
            //缴纳保证金
            int size = entity.getFbankCard().length();
            String str =entity.getFbankCard().substring(size - 4, size);
            final GetPriceDialog dialog=new GetPriceDialog(this);
            dialog.setMsg("已向尾号(" + str + ")的银行卡预留手机号发送验证码，请在下方输入验证码");
            dialog.setCanceledTouchOutside(true);
            dialog.setAuthCallBack(new GetPriceDialog.AuthCallBack() {
                @Override
                public void onAuth() {
                    costMsg(1000,1);
                }
            });
            dialog.setCommitCallBack(new GetPriceDialog.CommitCallBack() {
                @Override
                public void onCommit(String auth) {
                    dialog.dissDialog();
                    sendBaozhengjin(auth);
                }
            });
            dialog.create();
            dialog.showDialog();
            dialog.setDownTime();
        }
        if (type==2){

        }
    }
    //缴纳保证金
    private void sendBaozhengjin(String auth){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("memId",SessionManager.getInstance().getUser().getFdriverid()+"");
        map.put("tranAmount",1000);
        map.put("thirdHtId","");
        map.put("serialNo",SerialNo);
        map.put("messageCode",auth);
        XUtil.PostJsonObj(BaseURL.MEMSAMONG_TRANSACTIONPRIVATE,map,SessionManager.getInstance().getUser().getWebtoken(),new ResponseCallBack<BaseBean>(){
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                if (result.isSuccess()){
                    setEvent();
                }
                UHelper.showToast(CashpledgeActivity.this,result.getMsg());
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    private void setEvent(){
        UserInfoEntity user = SessionManager.getInstance().getUser();
        user.setFcautionmoney(1000);
        ((ClientApp) getApplication()).saveLoginUser(user);
        DataSynEvent event = new DataSynEvent();
        event.setType(event.DATA_SYNEVENT_CODE1);
        EventBus.getDefault().post(event);
        finish();
    }
}
