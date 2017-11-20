package com.babuwyt.siji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.BankCardListAdapter;
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
 * Created by lenovo on 2017/10/20.
 */
@ContentView(R.layout.activity_bankcard)
public class BankCarListActivity extends BaseActivity {
    @ViewInject(R.id.listView)
    ListView listView;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.searcheview)
    SearchView searcheview;
    private BankCardListAdapter mAdapter;
    private ArrayList<BankEntity> mList;
    private ArrayList<BankEntity> changList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getBankList();
    }
    /**
     * 获取银行
     */
    private void getBankList() {
        dialog.showDialog();
        XUtil.Post(BaseURL.ALLBANKIFO1, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankListBean>() {
            @Override
            public void onSuccess(BankListBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess() && result.getObj() != null) {
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
                Log.d("111111111111",ex+"");
            }
        });
    }

    private void init(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mList=new ArrayList<BankEntity>();
        changList=new ArrayList<BankEntity>();
        mAdapter=new BankCardListAdapter(this);
        //过滤数据
        //过滤出姓名里面包含"张"的数据

        mAdapter.setmList(changList);
        listView.setAdapter(mAdapter);
        // 设置搜索文本监听
        searcheview.setQueryHint("输入筛选内容！");
//        searcheview.setSubmitButtonEnabled(false);
//        searcheview.setIconifiedByDefault(false);
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
                    if (s.getZbankSname().contains(newText)){
                        changList.add(s);
                    }
                }
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.putExtra("ZID",changList.get(i).getZid());
                intent.putExtra("ZBANKSNAME",changList.get(i).getZbankSname());
                setResult(BindingBankCarkActivity.RESULT_CODE,intent);
                finish();
            }
        });
    }
}
