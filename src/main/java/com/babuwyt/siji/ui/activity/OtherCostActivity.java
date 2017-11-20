package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.OtherCostAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.OtherCostBean;
import com.babuwyt.siji.entity.OtherCostEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/21.
 * 其他费用 包括收入 扣除
 */
@ContentView(R.layout.activity_othercost)
public class OtherCostActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    @ViewInject(R.id.totalcost)
    TextView totalcost;

    private RecyclerView.LayoutManager manager;
    private OtherCostAdapter mAdapter;
    private ArrayList<OtherCostEntity> mList;
    private int type;
    private String fid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getIntExtra("type",-1);
        fid=getIntent().getStringExtra("fid");
        init();
        getHttp();
    }

    private void init() {
        toolbar.setTitle(type==1?getString(R.string.othershouru):getString(R.string.otherkouchu));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mList=new ArrayList<OtherCostEntity>();

        mAdapter=new OtherCostAdapter(this,mList);
        recyclerview.setAdapter(mAdapter);
        totalcost.setText(type==1?getString(R.string.other_cost_total)+0:getString(R.string.other_kouchu_total)+0);
    }

    private void setData(){
        double s=0;
        for (OtherCostEntity entity:mList){
            s+=entity.getFmoney();
        }
        totalcost.setText(type==1?getString(R.string.other_cost_total)+s:getString(R.string.other_kouchu_total)+s);
    }

    private void getHttp(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fid+"&"+type);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SELECT_INCOME,list,SessionManager.getInstance().getUser().getWebtoken(),new ResponseCallBack<OtherCostBean>(){
            @Override
            public void onSuccess(OtherCostBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    mList.clear();
                    mList.addAll(result.getObj());
                    mAdapter.notifyDataSetChanged();
                    setData();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
                UHelper.showToast(OtherCostActivity.this,ex+"");
            }
        });
    }
}
