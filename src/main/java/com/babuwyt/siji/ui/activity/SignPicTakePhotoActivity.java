package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.CameraUtils;
import com.babuwyt.siji.utils.CommonUtil;
import com.babuwyt.siji.utils.FilesSizeUtil;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.ImageOptions;
import com.babuwyt.siji.views.ImgCheckDialog;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

/**
 * Created by lenovo on 2017/9/26.
 */
@ContentView(R.layout.activity_signpic_takephoto)
public class SignPicTakePhotoActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private int REQUEST_CODE = 0x01;
    private int MY_PERMISSIONS_REQUEST_CAMERA = 777;
    private static int MY_PERMISSIONS_REQUEST_READ=888;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.img_photo)
    ImageView img_photo;
    @ViewInject(R.id.img_saoyisao)
    ImageView img_saoyisao;
    @ViewInject(R.id.et_sign_num)
    EditText et_sign_num;
    @ViewInject(R.id.tv_commit)
    TextView tv_commit;
    private String srcPath = "";//本地文件的绝对路径
    private String cosPathUrl = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
    }
    @Event(value = {R.id.img_saoyisao,R.id.tv_commit})
    private void getE(View v){
        switch (v.getId()){
            case R.id.img_saoyisao:
                if (CommonUtil.isCameraCanUse()) {
                    Intent intent = new Intent(this, RQCodeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(cosPathUrl)){
                    UHelper.showToast(this,"请上传照片");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("signnum",et_sign_num.getText().toString().trim());
                intent.putExtra("photo",cosPathUrl);
                setResult(1,intent);
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode==REQUEST_CODE && resultCode == 1) { //RESULT_OK = -1
            String scanResult = data.getStringExtra("rqcode");
            //将扫描出的信息显示出来
            et_sign_num.setText(scanResult);
        }
//相册
        if (requestCode == 1 && resultCode==Activity.RESULT_OK) {
            final String path = data.getStringExtra("PHOTO");
            getPath(path);
        }
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
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
    //获取到照片地址 进行压缩后上传
    private void getPath(String path) {
        if (FilesSizeUtil.getFileSize(path) > 500 * 1024) {
            try {
                File compressedImageFile = new Compressor(SignPicTakePhotoActivity.this).compressToFile(new File(path));
                srcPath = compressedImageFile.getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            srcPath = path;
        }
        x.image().bind(img_photo,srcPath,ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        upload(srcPath);
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
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, 0);
    }
    private void startPhoto() {
        Intent intent = new Intent(SignPicTakePhotoActivity.this, PhotoActivity.class);
        startActivityForResult(intent, 1);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length<=0 || grantResults[0]!= PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
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
                if (!TextUtils.isEmpty(cosPathUrl)){
                    delete(cosPathUrl);
                }
                cosPathUrl=cosPath;
                x.image().bind(img_photo, BaseURL.BASE_IMAGE_URI+cosPath, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
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

//    private void TackAgin(){
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setMessage(getString(R.string.chongxinpaizhao));
//        builder.setPositiveButton(R.string.queding, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                delete(cosPathUrl);
//                cosPathUrl="";
//                srcPath="";
//                Quanxian();
//            }
//        });
//        builder.setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
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




}
