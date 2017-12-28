package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.HistoryOrderBean;
import com.babuwyt.siji.entity.HistoryOrderItemEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.DateUtils;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/14.
 */
@ContentView(R.layout.activity_historyorderdetails)
public class HistoryOrderDetailsActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_otherkouchu)
    TextView tv_otherkouchu;
    @ViewInject(R.id.tv_othershouru)
    TextView tv_othershouru;
    @ViewInject(R.id.tv_zengsong)
    TextView tv_zengsong;
    @ViewInject(R.id.tv_xianjin)
    TextView tv_xianjin;
    @ViewInject(R.id.tv_youkajine)
    TextView tv_youkajine;
    @ViewInject(R.id.tv_youkahui)
    TextView tv_youkahui;
    @ViewInject(R.id.tv_shouru)
    TextView tv_shouru;
    @ViewInject(R.id.tv_huowu)
    TextView tv_huowu;
    @ViewInject(R.id.tv_remark)
    TextView tv_remark;
    @ViewInject(R.id.tv_xiehuoshijian)
    TextView tv_xiehuoshijian;
    @ViewInject(R.id.tv_tihuoshijian)
    TextView tv_tihuoshijian;
    @ViewInject(R.id.tv_luxian)
    TextView tv_luxian;
    @ViewInject(R.id.tv_orderNum)
    TextView tv_orderNum;
    @ViewInject(R.id.tv_state)
    TextView tv_state;
    private String fsendcarid;
    private HistoryOrderItemEntity entity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fsendcarid=getIntent().getStringExtra("fsendcarid");
        init();
        getOrderDeatils();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getOrderDeatils(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fsendcarid);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GET_QIANBAO_DETAILS,list, SessionManager.getInstance().getUser().getWebtoken(),new ResponseCallBack<HistoryOrderBean>(){
            @Override
            public void onSuccess(HistoryOrderBean result) {
                super.onSuccess(result);

                dialog.dissDialog();
                if (result.isSuccess()){
                    entity=result.getObj();
                    setData();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
    private void setData(){
        if (entity.getFsettlestate()==0){
            tv_state.setText(getString(R.string.weijiesuan));
        }if (entity.getFsettlestate()==1){
            tv_state.setText(getString(R.string.jiesuanyouka));
        }if (entity.getFsettlestate()==2){
            tv_state.setText(getString(R.string.jiesuanyunfei));
        }if (entity.getFsettlestate()==3){
            tv_state.setText(getString(R.string.yijiesuan));
        }
        tv_orderNum.setText(entity.getFsendcarno());
        tv_luxian.setText(entity.getFshipmentarea()+" -- "+entity.getFunloadarea());
        tv_tihuoshijian.setText(DateUtils.timedate(entity.getFshipmenttime()));
        tv_xiehuoshijian.setText(DateUtils.timedate(entity.getFunloadtime()));
        tv_remark.setText(entity.getFremark());
        tv_huowu.setText(entity.getFgoodname());
        tv_shouru.setText(TextUtils.isEmpty(entity.getFtotal())?"0":entity.getFtotal()+"");
        tv_youkahui.setText(TextUtils.isEmpty(entity.getFgiving())?"0":entity.getFgiving());
        tv_othershouru.setText(TextUtils.isEmpty(entity.getFotherin())?"0":entity.getFotherin());
        tv_otherkouchu.setText(TextUtils.isEmpty(entity.getFotherout())?"0":entity.getFotherout());
        tv_zengsong.setText(TextUtils.isEmpty(entity.getFacceptratio())?"0":entity.getFacceptratio());
        tv_youkajine.setText(TextUtils.isEmpty(entity.getFoilcard())?"0":entity.getFoilcard());
        tv_xianjin.setText(TextUtils.isEmpty(entity.getFtransport())?"0":entity.getFtransport());
    }
}
