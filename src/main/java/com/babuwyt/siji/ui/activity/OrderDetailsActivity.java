package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.OrderDetailsAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.OrderDetailsBean;
import com.babuwyt.siji.entity.LookAddressEntity;
import com.babuwyt.siji.entity.OrderDetailsEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.DateUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/28.
 */
@ContentView(R.layout.activity_orderdetails)
public class OrderDetailsActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;

    private TextView tv_state,tv_orderNum,tv_luxian,tv_tihuoshijian,tv_xiehuoshijian,tv_tuoshu,tv_tiji,tv_baozhi,tv_remark;
    private OrderDetailsAdapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<LookAddressEntity> mList;
    private String fsendcarno;
    private OrderDetailsEntity entity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fsendcarno=getIntent().getStringExtra("fsendcarno");
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
        manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mAdapter=new OrderDetailsAdapter(this);
        mList=new ArrayList<LookAddressEntity>();
        mAdapter.setmList(mList);
        recyclerview.setAdapter(mAdapter);
        setHeaderView(recyclerview);
    }
    //
    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.orderdetails_header, view, false);
        tv_state=header.findViewById(R.id.tv_state);
        tv_orderNum=header.findViewById(R.id.tv_orderNum);
        tv_luxian=header.findViewById(R.id.tv_luxian);
        tv_tihuoshijian=header.findViewById(R.id.tv_tihuoshijian);
        tv_xiehuoshijian=header.findViewById(R.id.tv_xiehuoshijian);
        tv_tuoshu=header.findViewById(R.id.tv_tuoshu);
        tv_tiji=header.findViewById(R.id.tv_tiji);
        tv_baozhi=header.findViewById(R.id.tv_baozhi);
        tv_remark=header.findViewById(R.id.tv_remark);
        mAdapter.setmHeaderView(header);
        mAdapter.notifyDataSetChanged();
    }
    private void getOrderDeatils(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fsendcarno);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GET_ORDER_DETAILS,list, SessionManager.getInstance().getUser().getWebtoken(),new ResponseCallBack<OrderDetailsBean>(){
            @Override
            public void onSuccess(OrderDetailsBean result) {
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
    //tv_state,tv_orderNum,tv_luxian,tv_tihuoshijian,tv_xiehuoshijian,tv_tuoshu,tv_tiji,tv_baozhi,tv_remark;
    private void setData(){
        if (entity==null){
            return;
        }
        mList.addAll(entity.getLoadpick());
        mAdapter.notifyDataSetChanged();
        tv_state.setText(UHelper.setState(entity.getFstate()+""));
        tv_orderNum.setText(entity.getFsendcarno());
        tv_luxian.setText(mList.get(0).getSsq()+"-"+mList.get(mList.size()-1).getSsq());
        tv_tihuoshijian.setText(DateUtils.timedate(entity.getFdelivergoodtime()));
        tv_xiehuoshijian.setText(DateUtils.timedate(entity.getFarrivetime()));
        tv_tuoshu.setText(entity.getFpiecenum());
        tv_tiji.setText(entity.getFbulk());
        tv_baozhi.setText(entity.getFvalue());
        tv_remark.setText(entity.getFremark());
    }

}
