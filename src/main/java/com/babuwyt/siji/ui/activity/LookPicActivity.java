package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.HotelEntityAdapter;
import com.babuwyt.siji.adapter.LookPicAdapter;
import com.babuwyt.siji.adapter.SectionedSpanSizeLookup;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.LookPicBean;
import com.babuwyt.siji.entity.ActionPicEntity;
import com.babuwyt.siji.entity.LookPicEntity;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.SectionDecoration;
import com.google.gson.Gson;

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

    private HotelEntityAdapter hotelEntityAdapter;
    private ArrayList<ActionPicEntity> list;
    private ArrayList<PicEntity> list1;
    private ArrayList<PicEntity> list2;
    private ArrayList<PicEntity> list3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fownsendcarid=getIntent().getStringExtra("fownsendcarid");
//        init();
        inits();
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
        recyclerview.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(mList.get(position).getFstate()+"".charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return mList.get(position).getFstate()+"".toUpperCase();
            }
        }));
    }
    private void inits(){
        list=new ArrayList<ActionPicEntity>();
        list1=new ArrayList<PicEntity>();
        list2=new ArrayList<PicEntity>();
        list3=new ArrayList<PicEntity>();
        mList=new ArrayList<PicEntity>();
        hotelEntityAdapter = new HotelEntityAdapter(this);
        GridLayoutManager manager1 = new GridLayoutManager(this,3);
        //设置header
        manager1.setSpanSizeLookup(new SectionedSpanSizeLookup(hotelEntityAdapter,manager1));
        recyclerview.setLayoutManager(manager1);
        hotelEntityAdapter.setData(list);
        recyclerview.setAdapter(hotelEntityAdapter);


    }
    private void getPic(){
        ArrayList<String> list=new ArrayList<String>();
        list.add(fownsendcarid);
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SIGNPICTURE, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<LookPicBean>() {
            @Override
            public void onSuccess(LookPicBean result) {
                super.onSuccess(result);
                Log.d("==照片==",new Gson().toJson(result));
                dialog.dissDialog();
                if (result.isSuccess() && result.getObj()!=null && result.getObj().size()>0){
                    mList.clear();
                    for (int i=0;i<result.getObj().size();i++){
                        mList.addAll(result.getObj().get(i).getHttpSignPictureRess());
                    }
                    s();
//                    mAdapter.notifyDataSetChanged();
                }else {
                    UHelper.showToast(LookPicActivity.this,result.getMsg());
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
                Log.d("===数据组===",ex+"");
            }
        });
    }

    private void s(){
        for (PicEntity entity: mList){
            if (entity.getFstate()==1){
                list1.add(entity);
            }else if (entity.getFstate()==2){
                list2.add(entity);
            }else {
                list3.add(entity);
            }
        }
        ActionPicEntity entity1=new ActionPicEntity();
        entity1.setName("装货照片");
        entity1.setPicEntities(list1);

        ActionPicEntity entity2=new ActionPicEntity();
        entity2.setName("签收照片");
        entity2.setPicEntities(list2);

        ActionPicEntity entity3=new ActionPicEntity();
        entity3.setName("卸货照片");
        entity3.setPicEntities(list3);

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);

        hotelEntityAdapter.notifyDataSetChanged();
        Log.d("===数据组===",new Gson().toJson(list));
    }
}
