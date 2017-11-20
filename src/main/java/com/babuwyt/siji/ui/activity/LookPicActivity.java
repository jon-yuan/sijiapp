package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.LookPicAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.LookPicBean;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */
@ContentView(R.layout.activity_lookpic)
public class LookPicActivity extends BaseActivity{
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    private RecyclerView.LayoutManager manager;
    private ArrayList<PicEntity> mList;
    private LookPicAdapter mAdapter;
    private String fownsendcarid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fownsendcarid=getIntent().getStringExtra("fownsendcarid");
        init();
        getPic();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        manager=new GridLayoutManager(this,3);
        recyclerview.setLayoutManager(manager);
        mList=new ArrayList<PicEntity>();
        mAdapter=new LookPicAdapter(this,mList);
        recyclerview.setAdapter(mAdapter);
    }
    private void getPic(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fownsendcarid);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SIGNPICTURE, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<LookPicBean>() {
            @Override
            public void onSuccess(LookPicBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess() && result.getObj()!=null && result.getObj().size()>0){
                    mList.clear();
                    mList.addAll(result.getObj().get(0).getHttpSignPictureRess());
                    mAdapter.notifyDataSetChanged();
                }else {
                    UHelper.showToast(LookPicActivity.this,result.getMsg());
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
