package com.babuwyt.siji.ui.activity;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.HistoryOrderAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.AcountBean;
import com.babuwyt.siji.bean.HistoryOrderBean;
import com.babuwyt.siji.entity.AcountEntity;
import com.babuwyt.siji.entity.HistoryOrderItemEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.RecycleViewDivider;
import com.bigkoo.pickerview.TimePickerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lenovo on 2017/9/22.
 */
@ContentView(R.layout.activity_historyorder)
public class HistoryOrderActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    @ViewInject(R.id.tv_total)
    TextView tv_total;
    @ViewInject(R.id.tv_youka)
    TextView tv_youka;
    @ViewInject(R.id.tv_kouchu)
    TextView tv_kouchu;
    @ViewInject(R.id.tv_zengsong)
    TextView tv_zengsong;
    @ViewInject(R.id.tv_shouru)
    TextView tv_shouru;
    @ViewInject(R.id.tv_xianjin)
    TextView tv_xianjin;
    @ViewInject(R.id.tv_chose_time)
    TextView tv_chose_time;
    private String y=null;
    private String m=null;
    private HistoryOrderAdapter mAdapter;
    private ArrayList<HistoryOrderItemEntity> mList;
    private RecyclerView.LayoutManager manager;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(false);
        if (y==null){
            y=new SimpleDateFormat("yyyy").format(new Date());
        }
        if (m==null){
            m=new SimpleDateFormat("MM").format(new Date());
        }
        init();
        getAcount();
        getAcountList();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        //添加线
        recyclerview.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.main_gray_line)));
        mList=new ArrayList<HistoryOrderItemEntity>();
        mAdapter=new HistoryOrderAdapter(this,mList);
        recyclerview.setAdapter(mAdapter);

        tv_chose_time.setText(y+"/"+m);
    }


    @Event(value = {R.id.tv_chose_time,R.id.img_chose_time})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_chose_time:
            case R.id.img_chose_time:
                showTimeSelect();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAcountList(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("1");
        list.add(y);
        list.add(m);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GETACOUNT_LIST, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<HistoryOrderBean>() {
            @Override
            public void onSuccess(HistoryOrderBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    mList.clear();
                    mList.addAll(result.getItems()==null? mList:result.getItems());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
    private void getAcount(){
        XUtil.GetPing(BaseURL.GETACOUNT, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<AcountBean>() {
            @Override
            public void onSuccess(AcountBean result) {
                super.onSuccess(result);

                if (result.isSuccess()){
                    setAcount(result.getObj());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }
    private void setAcount(AcountEntity acount){
        if (acount==null){
            return;
        }
        tv_total.setText(acount.getFtotal());
        tv_youka.setText(acount.getFoilcard());
        tv_xianjin.setText(acount.getFtransport());
        tv_shouru.setText(acount.getFotherin());
        tv_kouchu.setText(acount.getFotherout());
        tv_zengsong.setText(acount.getFgiving());
    }

    private void showTimeSelect(){
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat Y=new SimpleDateFormat("yyyy");
                SimpleDateFormat M=new SimpleDateFormat("MM");
                y=Y.format(date);
                m=M.format(date);
                tv_chose_time.setText(y+"/"+m);
                getAcountList();
            }
        }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setLabel(getString(R.string.year),getString(R.string.month),"","","","")//默认设置为年月日时分秒
                .build();
        pvTime.show();

    }
}
