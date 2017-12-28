package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.babuwyt.siji.views.PromptDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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
    private String phonenumbuter;
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
        mAdapter.setCallPhone(new LookAddressListAdapter.CallPhone() {
            @Override
            public void call(String numbuter) {
                phonenumbuter=numbuter;
                c();
            }
        });
    }

    private void c(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CALL_PHONE)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(LookAddressListActivity.this, rationale).show();
                    }
                })
                .callback(listener)
                .start();
    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.
            if (requestCode == 100) {
                // TODO ...
                call();
            }
        }

        @SuppressLint("NewApi")
        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if (requestCode == 100) {
                // TODO ...
                PromptDialog dialog = new PromptDialog(LookAddressListActivity.this);
                dialog.setTitle(getString(R.string.prompt));
                dialog.setMsg(getString(R.string.call_quanxian));
                dialog.setCanceledTouchOutside(true);
                dialog.setOnClick1(getString(R.string.cancal), new PromptDialog.Btn1OnClick() {
                    @Override
                    public void onClick() {


                    }
                });
                dialog.setOnClick2(getString(R.string.ok), new PromptDialog.Btn2OnClick() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivityForResult(intent, 0);
                    }
                });
                dialog.create();
                dialog.showDialog();

            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            call();
        }
    }

    @SuppressLint("MissingPermission")
    private void call(){
        if (TextUtils.isEmpty(phonenumbuter)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phonenumbuter));
        startActivity(intent);
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
