package com.babuwyt.siji.ui.activity;

import android.Manifest;
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

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.finals.Constants;

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
            startActivity(new Intent(this,MainActivity.class));
        }else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
    }
    //授权读写权限
    private void storageCard(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.MY_PERMISSIONS_REQUEST_READ);
        }else {
            timeDown();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode== Constants.MY_PERMISSIONS_REQUEST_READ){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                timeDown();
            }else {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("您没有授权读写权限，程序将无法使用！\n 该权限为应用储存和读取SD卡照片使用。请前往设置授权后重新打开APP");
                builder.setTitle("授权失败");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
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
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
