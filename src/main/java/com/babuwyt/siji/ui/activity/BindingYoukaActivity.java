package com.babuwyt.siji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */
@ContentView(R.layout.activity_bindingyouka)
public class BindingYoukaActivity extends BaseActivity {
    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.et_youka)
    EditText et_youka;

    private String fid="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fid=getIntent().getStringExtra("fid");
        init();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Event(value = {R.id.tv_binding,R.id.img_saoyisao})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_binding:
                binding();
                break;
            case R.id.img_saoyisao:

                    Intent intent = new Intent(this, RQCodeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == 1) { //RESULT_OK = -1
            String scanResult = data.getStringExtra("rqcode");
            //将扫描出的信息显示出来
            et_youka.setText(scanResult);
        }
    }
    private void binding(){
        if (TextUtils.isEmpty(et_youka.getText().toString().trim())){
            UHelper.showToast(this,getString(R.string.please_input_youkacode));
            return;
        }
        //BINDINGYOUKA
        ArrayList<String> list=new ArrayList<String>();
        list.add(fid+"&"+et_youka.getText().toString().trim());
        dialog.showDialog();
        XUtil.Post(BaseURL.BINDINGYOUKA, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    UHelper.showToast(BindingYoukaActivity.this,getString(R.string.binding_success));
                    Intent intent=new Intent();
                    intent.putExtra("fvicecard",et_youka.getText().toString().trim());
                    setResult(2,intent);
                    finish();
                }else {
                    UHelper.showToast(BindingYoukaActivity.this,result.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }


}
