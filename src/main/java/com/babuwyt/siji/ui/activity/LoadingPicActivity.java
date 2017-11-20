package com.babuwyt.siji.ui.activity;

import android.Manifest;
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
import android.view.MenuItem;
import android.view.View;


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
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
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
    private static int MY_PERMISSIONS_REQUEST_CAMERA = 777;
    private static int MY_PERMISSIONS_REQUEST_READ = 888;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;

    private RecyclerView.LayoutManager manager;
    private ArrayList<PicEntity> mList;
    private LoadingPicAdapter mAdapter;
    private String srcPath = "";//本地文件的绝对路径

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);
        manager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(manager);
        mList = new ArrayList<PicEntity>();
        mAdapter = new LoadingPicAdapter(this);
        mAdapter.setmList(mList);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setDeleteListener(this);
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
                delete(s.getPicture());
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
                Quanxian();
                break;
        }
        return true;
    }
    @Event(value = {R.id.tv_commit})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_commit:
                getHttpUpload();
                break;
        }
    }

    public void startCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                if (data != null) {
                    // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                    Uri uri = data.getData();
                    /*
                     * 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                     * 拍照后保存到相册的手机
                     */
                    if (uri != null) {
                        Cursor cursor = getContentResolver().query(uri, null,
                                null, null, null);
                        if (cursor.moveToFirst()) {
                            srcPath = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
                            getPath(srcPath);
                        }
                    }
                    //小米等 拍照后不保存的手机
                    else {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        String path = CameraUtils.getPath(bm);
                        getPath(path);
                    }
                }
            }
        }
    }
    //获取到照片地址 进行压缩后上传
    private void getPath(String path) {
        File compressedImageFile = null;
        try {
            compressedImageFile = new Compressor(LoadingPicActivity.this).compressToFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        srcPath = compressedImageFile.getPath();
        upload(srcPath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length<=0 || grantResults[0]!= PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
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
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
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
     * 拍照权限
     */
    private void Quanxian() {
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
    /**
     * 上传图片
     * @param srcPath
     */
    public void upload(final String srcPath) {
        if (TextUtils.isEmpty(srcPath)){
            return;
        }
        final String cosPath = "SiJi/wyt" + System.currentTimeMillis() / 1000 + ".jpg";
        TencentYunUtils.upload(this, srcPath,cosPath, new IUploadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long l, long l1) {
            }
            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
            }
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                PicEntity entity = new PicEntity();
                entity.setPicture(cosPath);
                mList.add(entity);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
            }
        });
    }

    /**
     * 删除图片
     * @param path
     */
    private void delete(String path) {
        TencentYunUtils.Del(this,path);
    }

    /**
     * let upPhotosData={
     addressno:'',
     latitude:'',
     longitude:'',
     ownerid:'',
     pciture:[],
     fstate:'1',
     };
     */
    private void getHttpUpload(){
        if (mList.size()<=0){
            return;
        }
        ArrayList<String> pics=new ArrayList<>();
        for (PicEntity entity:mList){
            pics.add(entity.getPicture());
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("addressno",addressno);
        map.put("latitude",latitude);
        map.put("longitude",longitude);
        map.put("ownerid",ownsendcarid);
        map.put("pciture",pics);
        map.put("fstate",1);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.PUSH_INSERT, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    finish();
                }
                UHelper.showToast(LoadingPicActivity.this,result.getMsg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }


}
