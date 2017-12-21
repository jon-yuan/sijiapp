package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.bean.CarTypeBean;
import com.babuwyt.siji.entity.CarTypeEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.CameraUtils;
import com.babuwyt.siji.utils.FilesSizeUtil;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.ImageOptions;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.ImgCheckDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

/**
 * Created by lenovo on 2017/9/22.
 * 个人信息认证
 */
@ContentView(R.layout.activity_peisonalinfoauth)
public class PersonalInfoAuthActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_name)
    TextView tv_name;//姓名
    @ViewInject(R.id.tv_idcard)
    TextView tv_idcard;//身份证
    @ViewInject(R.id.tv_phonenum)
    TextView tv_phonenum;//手机号
    @ViewInject(R.id.img_idcardimg1)
    ImageView img_idcardimg1;
    @ViewInject(R.id.img_idcardimg2)
    ImageView img_idcardimg2;
    @ViewInject(R.id.img_idcardimg3)
    ImageView img_idcardimg3;
    @ViewInject(R.id.tv_carnum)
    TextView tv_carnum;
    @ViewInject(R.id.cartype)
    TextView cartype;
    @ViewInject(R.id.tv_xingshizheng)
    TextView tv_xingshizheng;
    @ViewInject(R.id.tv_jiashizheng)
    TextView tv_jiashizheng;
    @ViewInject(R.id.tv_carsuoyouren)
    TextView tv_carsuoyouren;
    @ViewInject(R.id.img_jiashiimg1)
    ImageView img_jiashiimg1;
    @ViewInject(R.id.img_xingshizhengimg1)
    ImageView img_xingshizhengimg1;
    private String srcPath = "";
    private String idcardimg1, idcardimg2, idcardimg3, jiashiimg1, xingshizhengimg1;
    private String ftrucktypeid = "";
    private int type = 0;
    private ArrayList<CarTypeEntity> mCars;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getCarType();
    }

    private void init() {
        mCars = new ArrayList<CarTypeEntity>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_phonenum.setText(SessionManager.getInstance().getUser().getFphone());


        tv_idcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_jiashizheng.setText(charSequence + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tv_carnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_xingshizheng.setText(charSequence + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void isEmpty() {
        if (TextUtils.isEmpty(tv_name.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_name));
            return;
        }
        if (TextUtils.isEmpty(tv_idcard.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_idcard));
            return;
        }
        if (!UHelper.isIdCard(tv_idcard.getText().toString())) {
            UHelper.showToast(this, getString(R.string.idcard_is_fail));
            return;
        }
        if (!UHelper.isPhone(tv_phonenum.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_success_phone_numbuter));
            return;
        }
        if (!UHelper.isCar(tv_carnum.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_carnum));
            return;
        }
        if (TextUtils.isEmpty(ftrucktypeid)) {
            UHelper.showToast(this, getString(R.string.please_select_cartype));
            return;
        }
        if (TextUtils.isEmpty(tv_xingshizheng.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_xingshizheng));
            return;
        }
        if (TextUtils.isEmpty(tv_jiashizheng.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_jiashizheng));
            return;
        }
        if (TextUtils.isEmpty(tv_carsuoyouren.getText().toString())) {
            UHelper.showToast(this, getString(R.string.please_input_cheliangsuoyouren));
            return;
        }
        if (TextUtils.isEmpty(idcardimg1)) {
            UHelper.showToast(this, getString(R.string.please_load_idcardim1));
            return;
        }
        if (TextUtils.isEmpty(idcardimg2)) {
            UHelper.showToast(this, getString(R.string.please_load_idcardim2));
            return;
        }
        if (TextUtils.isEmpty(idcardimg3)) {
            UHelper.showToast(this, getString(R.string.please_load_idcardim3));
            return;
        }
        if (TextUtils.isEmpty(jiashiimg1)) {
            UHelper.showToast(this, getString(R.string.please_load_jiashizheng));
            return;
        }
        if (TextUtils.isEmpty(xingshizhengimg1)) {
            UHelper.showToast(this, getString(R.string.please_load_xingshizheng));
            return;
        }
        getAuth();
    }

    /**
     * fdrivername: '',//姓名
     * fcard: '',//身份证号
     * fphone: '',//手机号
     * fbankid: '',//银行id
     * fbankcard: '',//卡号
     * fcardpicture: '',//身份证照片正面
     * fcardpicturerear: '',//身份证照片反面
     * fhandcardpicture: '',//手持身份证照片
     * fplateno: '',//车牌√
     * ftrucktypeid: '',//车辆类型id
     * fdrivenumber: '',//行驶证号码
     * fdrivingcard: '',//驾驶证号码
     * fattribution: '',//车辆所有人
     * fdrivecard: '',//行驶证照片
     * fdrivingpicture: '',//驾驶证照片
     */
    private void getAuth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdrivername", tv_name.getText().toString().trim());
        map.put("fcard", tv_idcard.getText().toString().trim());
        map.put("fphone", tv_phonenum.getText().toString().trim());
        map.put("fbankid", "");
        map.put("fbankcard", "");
        map.put("fcardpicture", idcardimg1);
        map.put("fcardpicturerear", idcardimg2);
        map.put("fhandcardpicture", idcardimg3);
        map.put("fplateno", tv_carnum.getText().toString().trim());
        map.put("ftrucktypeid", ftrucktypeid);
        map.put("fdrivenumber", tv_xingshizheng.getText().toString().trim());
        map.put("fdrivingcard", tv_jiashizheng.getText().toString().trim());
        map.put("fattribution", tv_carsuoyouren.getText().toString().trim());
        map.put("fdrivecard", xingshizhengimg1);
        map.put("fdrivingpicture", jiashiimg1);
        XUtil.PostJsonObj(BaseURL.AUTHENTICATION, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                if (result.isSuccess()) {
                    finish();
                }
                UHelper.showToast(PersonalInfoAuthActivity.this, result.getMsg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    @Event(value = {R.id.cartype, R.id.tv_commit, R.id.img_idcardimg1, R.id.img_idcardimg2, R.id.img_idcardimg3, R.id.img_jiashiimg1, R.id.img_xingshizhengimg1})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                isEmpty();
                break;
            case R.id.img_idcardimg1:
                isShowDialog(1);
                break;
            case R.id.img_idcardimg2:
                isShowDialog(2);
                break;
            case R.id.img_idcardimg3:
                isShowDialog(3);
                break;
            case R.id.img_jiashiimg1:
                isShowDialog(4);
                break;
            case R.id.img_xingshizhengimg1:
                isShowDialog(5);
                break;
            case R.id.cartype:
                showCar();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void isShowDialog(final int type) {
        ImgCheckDialog dialog = new ImgCheckDialog(this);
        dialog.setCallBackPaizhao(new ImgCheckDialog.CallBackPaizhao() {
            @Override
            public void callbackPaizhao() {
                cameraAuthorization(type);
            }
        });
        dialog.setCallBackXiangce(new ImgCheckDialog.CallBackXiangce() {
            @Override
            public void callbackXiangce() {
                startPhoto(type);
            }
        });
        dialog.create();
        dialog.showDialog();
    }


    public void startCamera(int type) {
        this.type = type;
        Intent intent = new Intent(this, PaiZhaoActivity.class);
        startActivityForResult(intent, 3);
    }

    private void startPhoto(int type) {
        this.type = type;
        Intent intent = new Intent(PersonalInfoAuthActivity.this, PhotoActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //相册
            if (requestCode == 1) {
                final String path = data.getStringExtra("PHOTO");
                withTpye(path);
            }
        }
        if (resultCode == 4 && requestCode == 3) {
            String path = data.getStringExtra("path");
            withTpye(path);
        }
    }

    //idcardimg1, idcardimg2, idcardimg3, jiashiimg1, xingshizhengimg1;
    private void withTpye(String path) {
        switch (type) {
            case 1:
                TencentYunUtils.Del(this, idcardimg1);
                idcardimg1 = path;
                x.image().bind(img_idcardimg1, BaseURL.BASE_IMAGE_URI + path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
                break;
            case 2:
                TencentYunUtils.Del(this, idcardimg2);
                idcardimg2 = path;
                x.image().bind(img_idcardimg2, BaseURL.BASE_IMAGE_URI + path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
                break;
            case 3:
                TencentYunUtils.Del(this, idcardimg3);
                idcardimg3 = path;
                x.image().bind(img_idcardimg3, BaseURL.BASE_IMAGE_URI + path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
                break;
            case 4:
                TencentYunUtils.Del(this, jiashiimg1);
                jiashiimg1 = path;
                x.image().bind(img_jiashiimg1, BaseURL.BASE_IMAGE_URI + path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
                break;
            case 5:
                TencentYunUtils.Del(this, xingshizhengimg1);
                xingshizhengimg1 = path;
                x.image().bind(img_xingshizhengimg1, BaseURL.BASE_IMAGE_URI + path, ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
                break;
        }
    }

    private void showCar() {
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                cartype.setText(mCars.get(options1).getPickerViewText());
                ftrucktypeid = mCars.get(options1).getFid();
            }
        }).build();
        pvOptions.setPicker(mCars);
        pvOptions.show();
    }

    private void getCarType() {
        dialog.showDialog();
        XUtil.GetPing(BaseURL.SELECT_CAR_TYPE, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<CarTypeBean>() {
            @Override
            public void onSuccess(CarTypeBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    mCars.addAll(result.getObj());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    //相机授权
    private void cameraAuthorization(int type) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    Constants.MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            startCamera(type);
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
                UHelper.showToast(this, "相机授权成功！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
