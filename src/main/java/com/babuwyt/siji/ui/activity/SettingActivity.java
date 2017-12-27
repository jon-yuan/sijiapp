package com.babuwyt.siji.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.VersionBean;
import com.babuwyt.siji.entity.VersionEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseProgressCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/23.
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_version)
    TextView tv_version;
    @ViewInject(R.id.layout_version)
    LinearLayout layout_version;
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
        String versionName= UHelper.getAppVersionInfo(this,UHelper.TYPE_VERSION_NAME);
        tv_version.setText(versionName);

    }


    @Event(value = {R.id.tv_commit,R.id.layout_version})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_commit:
                logout();
                break;
                case R.id.layout_version:
                getVersion();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void logout() {
        PromptDialog dialog = new PromptDialog(this);
        dialog.setTitle(getString(R.string.prompt));
        dialog.setMsg(getString(R.string.logout));
        dialog.setCanceledTouchOutside(true);
        dialog.setOnClick1(getString(R.string.queding), new PromptDialog.Btn1OnClick() {
            @Override
            public void onClick() {
                ((ClientApp)getApplication()).clearLoginUser();
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        dialog.setOnClick2(getString(R.string.cancal), new PromptDialog.Btn2OnClick() {
            @Override
            public void onClick() {

            }
        });
        dialog.create();
        dialog.showDialog();
    }

    //版本检测
    private void getVersion() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(1 + "");
        XUtil.GetPing(BaseURL.CHECKVERSION, list, new ResponseCallBack<VersionBean>() {
            @Override
            public void onSuccess(VersionBean o) {
                if (o.isSuccess()) {
                    setVersion(o.getObj());
                    SessionManager.getInstance().setServicephone(o.getObj().getFservicephone());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }

    private void setVersion(final VersionEntity entity) {
        String vsersionCode = UHelper.getAppVersionInfo(this, UHelper.TYPE_VERSION_CODE);
        if (entity.getFversion() > Integer.parseInt(vsersionCode)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.new_version));
            builder.setMessage(entity.getFupdateinfo());
            builder.setPositiveButton(getString(R.string.updata), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    DownLoadFile(entity.getFurl());
                }
            });
            if (entity.getFisforceupdate()) {
                builder.setCancelable(false);
            } else {
                builder.setCancelable(true);
                builder.setNegativeButton(getString(R.string.cancal), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
            builder.create().show();
        }else {
            UHelper.showToast(this,getString(R.string.is_new_version));
        }
    }

    /**
     * 下载现版本APP
     */
    private File filepath;

    private void DownLoadFile(String url) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取SD卡的目录
            String path = Environment.getExternalStorageDirectory().getPath();
            filepath = new File(path + File.separator + "apk" + File.separator + "siji.apk");//仅创建路径的File对象
            if (!filepath.exists()) {
                filepath.mkdir();//如果路径不存在就先创建路径
            }
        }
        // 准备进度条Progress弹窗
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        dialog.setTitle("下载中...");
        //Progress弹窗设置为水平进度条
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条

        //"http://acj3.pc6.com/pc6_soure/2017-8/me.ele_190.apk"
        XUtil.DownLoadFile(url, filepath.getPath().toString(), new ResponseProgressCallBack<File>() {
            @Override
            public void Started() {
                dialog.show();
            }

            @Override
            public void Success(File o) {
                dialog.dismiss();
                install(filepath);
            }

            @Override
            public void Loading(long total, long current, boolean isDownloading) {
                dialog.setMax((int) total);
                dialog.setProgress((int) current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dismiss();
            }
        });

    }


    private void install(File filePath) {
        File apkFile = filePath;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    this
                    , "com.babuwyt.siji.fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
