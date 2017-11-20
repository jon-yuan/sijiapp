package com.babuwyt.siji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by lenovo on 2017/9/25.
 */
@ContentView(R.layout.activity_rqcode)
public class RQCodeActivity extends BaseActivity implements QRCodeView.Delegate{
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.zxingview)
    ZXingView mQRCodeView;
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
        mQRCodeView.setDelegate(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
        //打开后置摄像头预览,但并没有开始扫描
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Intent intent=new Intent();
        intent.putExtra("rqcode",result);
        setResult(1,intent);
        finish();
//        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
    }
}
