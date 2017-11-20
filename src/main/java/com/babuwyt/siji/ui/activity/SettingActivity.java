package com.babuwyt.siji.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by lenovo on 2017/10/23.
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_version)
    TextView tv_version;
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


    @Event(value = {R.id.tv_commit})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_commit:
                logout();
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
}
