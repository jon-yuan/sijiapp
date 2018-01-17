package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.CameraUtils;
import com.babuwyt.siji.utils.FilesSizeUtil;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.ImgCheckDialog;
import com.google.gson.Gson;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

/**
 * Created by lenovo on 2017/9/25.
 */
@ContentView(R.layout.activity_loadingpic)
public class LoadingPicActivity extends BaseActivity implements LoadingPicAdapter.DeleteListener, Toolbar.OnMenuItemClickListener {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.gridview)
    GridView gridview;

    private ArrayList<PicEntity> mList;
    private LoadingPicAdapter mAdapter;

    private String ownsendcarid = null;
    private String addressno = null;
    private double latitude = -1;
    private double longitude = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ownsendcarid = getIntent().getStringExtra("ownsendcarid");
        addressno = getIntent().getStringExtra("addressno");
        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);
        init();
        getPics();
    }

    private void init() {
        toolbar.setTitle(getString(R.string.zhuanghuozhaopian));
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
        mAdapter.setDeleteListener(this);
        mAdapter.setmList(mList);
        gridview.setAdapter(mAdapter);

    }

    private void getPics() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(ownsendcarid + "&" + addressno + "&1");
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GETPICTRUES, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<PicBean>() {
            @Override
            public void onSuccess(PicBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_pic));
        builder.setNegativeButton(getString(R.string.queding), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //todo 删除的时候要判断是本地图片还是云上的图片
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
//                startCamera();
                isShowDialog();
                break;
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void isShowDialog() {
        ImgCheckDialog dialog = new ImgCheckDialog(this);
        dialog.setCallBackPaizhao(new ImgCheckDialog.CallBackPaizhao() {
            @Override
            public void callbackPaizhao() {
                cameraAuthorization();
            }
        });
        dialog.setCallBackXiangce(new ImgCheckDialog.CallBackXiangce() {
            @Override
            public void callbackXiangce() {
                startPhoto();
            }
        });
        dialog.create();
        dialog.showDialog();
    }

    private void cameraAuthorization() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    Constants.MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            startCamera();
        }
    }

    public void startCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 1);
        Intent intent = new Intent(this, PaiZhaoActivity.class);
        startActivityForResult(intent, 3);
    }

    private void startPhoto() {
        Intent intent = new Intent(LoadingPicActivity.this, PhotoActivity.class);
        startActivityForResult(intent, 2);
    }

    @Event(value = {R.id.tv_commit})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                getHttpUpload();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            final String path = data.getStringExtra("PHOTO");
            final PicEntity entity = new PicEntity();
            entity.setPicture(path);
            mList.add(entity);
            mAdapter.notifyDataSetChanged();
        }
        if (resultCode == 4 && requestCode == 3) {
            String path = data.getStringExtra("path");
//            x.image().bind(img_idcardimg2, path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
//            getPath(path);
            final PicEntity entity = new PicEntity();
            entity.setPicture(path);
            mList.add(entity);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("您没有授权成功，无法使用相机进行拍照功能，请前往设置授权！");
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
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            } else {
                startCamera();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 删除图片
     *
     * @param path
     */
    private void delete(String path) {
        TencentYunUtils.Del(this, path);
    }

    /**
     * let upPhotosData={
     * addressno:'',
     * latitude:'',
     * longitude:'',
     * ownerid:'',
     * pciture:[],
     * fstate:'1',
     * };
     */
    private void getHttpUpload() {
        if (mList.size() <= 0) {
            return;
        }
        ArrayList<String> pics = new ArrayList<>();
        for (PicEntity entity : mList) {
            pics.add(entity.getPicture());
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addressno", addressno);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("ownerid", ownsendcarid);
        map.put("pciture", pics);
        map.put("fstate", 1);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.PUSH_INSERT, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    finish();
                }
                UHelper.showToast(LoadingPicActivity.this, result.getMsg());
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
}
