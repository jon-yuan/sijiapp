package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.LookAddressListAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.LookAddressBean;
import com.babuwyt.siji.entity.LookAddressEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 * 查看提卸货地详情
 */
@ContentView(R.layout.activity_lookaddresslist)
public class LookAddressListActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    private RecyclerView.LayoutManager manager;
    private ArrayList<LookAddressEntity> mList;
    private LookAddressListAdapter mAdapter;
    private String fownsendcarid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fownsendcarid=getIntent().getStringExtra("fownsendcarid");
        init();
        getAddress();
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
        mList=new ArrayList<LookAddressEntity>();
        mAdapter=new LookAddressListAdapter(this,mList);
        recyclerview.setAdapter(mAdapter);
    }
    private void getAddress(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fownsendcarid);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SELECT_ADDRESS, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<LookAddressBean>() {
            @Override
            public void onSuccess(LookAddressBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    mList.clear();
                    mList.addAll(result.getObj()==null?mList:result.getObj());
                    mAdapter.notifyDataSetChanged();
                }else {
                    UHelper.showToast(LookAddressListActivity.this,result.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
}
