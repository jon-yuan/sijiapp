package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;

/**
 * Created by lenovo on 2017/9/21.
 */
@ContentView(R.layout.activity_welcome)
public class WelComeActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageCard();
    }
    //两秒后页面切换
    private void timeDown(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToActivity();
            }
        }, 2000);
    }

    private void jumpToActivity(){
        isLogin();
    }

    private void isLogin(){
        //todo 判断是否已经登陆
        if (SessionManager.getInstance().isLogin()){
            Intent intent=new Intent(this,MainActivity.class);
//            if(getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null){
//                intent.putExtra(Constants.EXTRA_BUNDLE,
//                        getIntent().getStringExtra(Constants.EXTRA_BUNDLE));
//            }
            startActivity(intent);
        }else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
    }
    //授权读写权限
    private void storageCard(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_READ);
        }else {
            timeDown();
        }
    }
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode== Constants.MY_PERMISSIONS_REQUEST_READ){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                timeDown();
            }else {
                PromptDialog dialog = new PromptDialog(this);
                dialog.setTitle(getString(R.string.prompt));
                dialog.setMsg(getString(R.string.plsase_shouquan_sdcard));
                String str="";
                if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    str=getString(R.string.plsase_shouquan_sdcard);
                }
                if (grantResults[1]!=PackageManager.PERMISSION_GRANTED){
                    str=getString(R.string.plsase_location_sdcard);
                }
                if (grantResults[0]!=PackageManager.PERMISSION_GRANTED && grantResults[1]!=PackageManager.PERMISSION_GRANTED){
                    str=getString(R.string.plsase_shouquan_location_sdcard);
                }
                dialog.setCanceledTouchOutside(false);
                dialog.setOnClick1(getString(R.string.queding), new PromptDialog.Btn1OnClick() {
                    @Override
                    public void onClick() {
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                        finish();

                    }
                });
                dialog.setOnClick2(getString(R.string.cancal), new PromptDialog.Btn2OnClick() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
                dialog.create();
                dialog.showDialog();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
