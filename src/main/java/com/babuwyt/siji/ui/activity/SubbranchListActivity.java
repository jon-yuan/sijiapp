package com.babuwyt.siji.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.SubbranchAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BankListBean;
import com.babuwyt.siji.entity.BankEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/24.
 * 查询大小额联行号，支行
 */

@ContentView(R.layout.activity_subbranchlist)
public class SubbranchListActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.listview)
    ListView listview;
    @ViewInject(R.id.searcheview)
    SearchView searcheview;


    private String bankclscode;
    private String citycode;
    private String areacitycode;

    private ArrayList<BankEntity> mList;
    private ArrayList<BankEntity> changList;
    private SubbranchAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getBankInfo();
    }
    @SuppressLint("ResourceAsColor")
    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bankclscode=getIntent().getStringExtra("bankclscode");
        citycode=getIntent().getStringExtra("citycode");
        areacitycode=getIntent().getStringExtra("areacitycode");
        mList=new ArrayList<BankEntity>();
        changList=new ArrayList<BankEntity>();
        mAdapter=new SubbranchAdapter(this);
        mAdapter.setmList(changList);
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.putExtra("bankCode",changList.get(i).getBankno());
                intent.putExtra("bankName",changList.get(i).getBankname());
                setResult(BindingBankCarkActivity.RESULT_CODE_SUBB,intent);
                finish();
            }
        });

        searcheview.setQueryHint("输入筛选内容！");
        searcheview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                changList.clear();
                for (BankEntity s:mList){
                    if (s.getBankname().contains(newText)){
                        changList.add(s);
                    }
                }
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }


    /**
     * 查询大小额联行号
     */
    private void getBankInfo() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(bankclscode);
        list.add(citycode);
        list.add(areacitycode);
        dialog.showDialog();
        XUtil.Post(BaseURL.BANKNOINFO, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankListBean>() {
            @Override
            public void onSuccess(BankListBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    mList.clear();
                    mList.addAll(result.getObj());
                    changList.addAll(mList);
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
}
