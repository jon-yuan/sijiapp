package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.GrabOrderListAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.GrabOrderBean;
import com.babuwyt.siji.entity.GrabOrderItemEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 * 抢单大厅
 */
@ContentView(R.layout.activity_graborder)
public class GrabOrderListActivity extends BaseActivity implements GrabOrderListAdapter.CallPhoneListener{
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    @ViewInject(R.id.springview)
    SpringView springview;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    private RecyclerView.LayoutManager manager;
    private GrabOrderListAdapter mAdapter;
    private ArrayList<GrabOrderItemEntity> mList;
    private String phonenum="10086";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getOrderList();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);

        mList = new ArrayList<GrabOrderItemEntity>();
        mAdapter = new GrabOrderListAdapter(this, mList);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setListener(this);
//        springview.setType(SpringView.Type.FOLLOW);
        springview.setHeader(new DefaultHeader(this));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getOrderList();
            }

            @Override
            public void onLoadmore() {

            }
        });


    }

    private void getOrderList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(1 + "");
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SELECTOBORDER, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<GrabOrderBean>() {
            @Override
            public void onSuccess(GrabOrderBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    mList.clear();
                    mList.addAll(result.getItems());
                    mAdapter.notifyDataSetChanged();
                }
                springview.onFinishFreshAndLoad();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
                springview.onFinishFreshAndLoad();
            }
        });
    }

    public void testCall() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone(phonenum);
        }
    }

    @SuppressLint("MissingPermission")
    public void callPhone(String phonenum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phonenum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(phonenum);
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("您没有授权成功，无法使用拨打电话功能");
                builder.setTitle("授权失败");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onListener(int position) {
        phonenum=mList.get(position).getFmobilephone();
        testCall();
    }
}
