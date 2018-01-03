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
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.SignNoBean;
import com.babuwyt.siji.entity.SignNoEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.CameraUtils;
import com.babuwyt.siji.utils.CommonUtil;
import com.babuwyt.siji.utils.FilesSizeUtil;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.ImageOptions;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.ImgCheckDialog;
import com.babuwyt.siji.views.SignNoCheckDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

/**
 * Created by lenovo on 2017/9/26.
 */
@ContentView(R.layout.activity_signpic_takephoto)
public class SignPicTakePhotoActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private int REQUEST_CODE = 0x01;
    private int MY_PERMISSIONS_REQUEST_CAMERA = 777;
    private static int MY_PERMISSIONS_REQUEST_READ = 888;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.img_photo)
    ImageView img_photo;
    @ViewInject(R.id.img_saoyisao)
    ImageView img_saoyisao;
    @ViewInject(R.id.et_sign_num)
    TextView et_sign_num;
    @ViewInject(R.id.tv_commit)
    TextView tv_commit;
    private String srcPath = "";//本地文件的绝对路径
    private String cosPathUrl = "";
    private String fsendcarno;
    private ArrayList<SignNoEntity> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fsendcarno = getIntent().getStringExtra("fsendcarno");
        init();
        selectSignNo();
    }

    private void init() {
        mList = new ArrayList<SignNoEntity>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Event(value = {R.id.img_saoyisao, R.id.tv_saoyisao, R.id.tv_commit, R.id.et_sign_num, R.id.img_photo})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.img_saoyisao:
                showSing();
                break;
            case R.id.tv_saoyisao:
                if (CommonUtil.isCameraCanUse()) {
                    Intent intent = new Intent(this, RQCodeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(cosPathUrl)) {
                    UHelper.showToast(this, getString(R.string.please_load_xianshoudanzhaopian));
                    return;
                }
                if (TextUtils.isEmpty(et_sign_num.getText().toString().trim())){
                    UHelper.showToast(this, getString(R.string.please_input_sign_num));
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("signnum", et_sign_num.getText().toString().trim());
                intent.putExtra("photo", cosPathUrl);
                setResult(1, intent);
                finish();
                break;
            case R.id.img_photo:
                isShowDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == REQUEST_CODE && resultCode == 1) { //RESULT_OK = -1
            String scanResult = data.getStringExtra("rqcode");
            //将扫描出的信息显示出来
            et_sign_num.setText(scanResult);
        }
//相册
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final String path = data.getStringExtra("PHOTO");

            if (!TextUtils.isEmpty(cosPathUrl)) {
                delete(cosPathUrl);
            }
            cosPathUrl = path;
            x.image().bind(img_photo, BaseURL.BASE_IMAGE_URI + cosPathUrl, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));

        }
        if (resultCode == 4 && requestCode == 3) {
            String path = data.getStringExtra("path");
            if (!TextUtils.isEmpty(cosPathUrl)) {
                delete(cosPathUrl);
            }
            cosPathUrl = path;
            x.image().bind(img_photo, BaseURL.BASE_IMAGE_URI + cosPathUrl, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        }

    }
    //获取到照片地址 进行压缩后上传

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
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(openCameraIntent, 0);

        Intent intent = new Intent(this, PaiZhaoActivity.class);
        startActivityForResult(intent, 3);
    }

    private void startPhoto() {
        Intent intent = new Intent(SignPicTakePhotoActivity.this, PhotoActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

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

    private void selectSignNo() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(fsendcarno);
//        list.add("D180103101730597002");
        XUtil.GetPing(BaseURL.SELECT_SIGNNO, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<SignNoBean>() {
            @Override
            public void onSuccess(SignNoBean result) {
                super.onSuccess(result);
                if (result.isSuccess()) {
                    mList.clear();
                    mList.addAll(result.getObj());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    private void showSing() {
        if (mList.size()<=0){
            UHelper.showToast(this,getString(R.string.no_signno));
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                et_sign_num.setText(mList.get(options1).getPickerViewText());
            }
        }).build();
        pvOptions.setPicker(mList);
        pvOptions.show();
    }

}
