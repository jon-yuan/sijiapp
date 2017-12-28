package com.babuwyt.siji.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.LoadingPicAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.bean.PicBean;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/25.
 */
@ContentView(R.layout.activity_loadingpic)
public class SignPicActivity extends BaseActivity implements LoadingPicAdapter.DeleteListener, Toolbar.OnMenuItemClickListener {
    private static int MY_PERMISSIONS_REQUEST_CAMERA=777;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.gridview)
    GridView gridview;

    private RecyclerView.LayoutManager manager;
    private ArrayList<PicEntity> mList;
    private LoadingPicAdapter mAdapter;
    private String srcPath = "";//本地文件的绝对路径

    private String ownsendcarid=null;
    private String addressno=null;
    private double latitude=-1;
    private double longitude=-1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ownsendcarid=getIntent().getStringExtra("ownsendcarid");
        addressno=getIntent().getStringExtra("addressno");
        latitude=getIntent().getDoubleExtra("latitude",-1);
        longitude=getIntent().getDoubleExtra("longitude",-1);

        init();
        getPics();
    }

    private void init() {
        toolbar.setTitle(R.string.signpic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);
//        manager = new GridLayoutManager(this, 3);
//        recyclerview.setLayoutManager(manager);
        mList = new ArrayList<PicEntity>();
        mAdapter = new LoadingPicAdapter(this);
        mAdapter.setmList(mList);
        gridview.setAdapter(mAdapter);
        mAdapter.setDeleteListener(this);
    }
    @Event(value = {R.id.tv_commit})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_commit:
                getHttpUpload();
                break;
        }
    }

    private void getPics() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(ownsendcarid + "&" + addressno + "&2");
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GETPICTRUES, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<PicBean>() {
            @Override
            public void onSuccess(PicBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    mList.clear();
                    mList.addAll(result.getObj());
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

    @Override
    public void onDelete(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_pic));
        builder.setNegativeButton(getString(R.string.queding), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                PicEntity s = mList.get(position);
                mList.remove(s);
                mAdapter.notifyDataSetChanged();
                delete(s.getFpicture());
            }
        });
        builder.setPositiveButton(getString(R.string.quxiao), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent=new Intent();
                intent.setClass(this,SignPicTakePhotoActivity.class);
                startActivityForResult(intent,0);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==1){
            String signnum=data.getStringExtra("signnum");
            String photoPath=data.getStringExtra("photo");
//            upload(photoPath,signnum);
            PicEntity entity = new PicEntity();
            entity.setPicture(photoPath);
            entity.setfSignNo(signnum);
            mList.add(entity);
            mAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 删除图片
     * @param path
     */
    private void delete(String path) {
        TencentYunUtils.Del(this,path);
    }

    private void getHttpUpload(){
        if (mList.size()<=0){
            return;
        }
        ArrayList<String> pics=new ArrayList<String>();
        ArrayList<String> fSignNo=new ArrayList<String>();
        for (PicEntity entity:mList){
            pics.add(entity.getPicture());
            fSignNo.add(entity.getfSignNo());
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("addressno",addressno);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        map.put("ownerid",ownsendcarid);
        map.put("pciture",pics);
        map.put("fSignNo",fSignNo);
        map.put("fstate",2);
        dialog.showDialog();
        Log.d("上传三叔==",new Gson().toJson(map));
        XUtil.PostJsonObj(BaseURL.PUSH_INSERT, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    finish();
                }
                UHelper.showToast(SignPicActivity.this,result.getMsg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

}
