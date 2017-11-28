package com.babuwyt.siji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.UserInfoBean;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.jpush.TagAliasOperatorHelper;
import com.babuwyt.siji.utils.jpush.TagAliasOperatorHelper.TagAliasBean;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2017/9/21.
 * 登录界面
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.et_authCode)
    EditText et_authCode;
    @ViewInject(R.id.et_phoneNum)
    EditText et_phoneNum;
    @ViewInject(R.id.tv_authCode)
    TextView tv_authCode;
    @ViewInject(R.id.tv_commit)
    TextView tv_commit;
    private int time=60;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(time>0){
                tv_authCode.setText("("+time+"s)"+getString(R.string.after_get));
                tv_authCode.setEnabled(false);
            }else{
                timer.cancel();
                timer=null;
                time=60;
                tv_authCode.setText(getString(R.string.getAuthCode));
                tv_authCode.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = {R.id.tv_commit,R.id.tv_authCode})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                commit();
                break;
            case R.id.tv_authCode:
                getAuthCode();
                break;
        }
    }
    private void commit(){
        if (!UHelper.isPhone(et_phoneNum.getText().toString().trim())){
            return;
        }
        if (TextUtils.isEmpty(et_authCode.getText().toString().trim())){
            return;
        }
        Login();
    }

    private void timeDown(){

        timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                time--;
                Message msg=handler.obtainMessage();
                msg.what=1;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask,0,1000);
    }


    private void getAuthCode(){
        if (!UHelper.isPhone(et_phoneNum.getText().toString().trim())){
            UHelper.showToast(this,getString(R.string.phone_fail));
            return;
        }

        ArrayList<String> list=new ArrayList<>();
        list.add(et_phoneNum.getText().toString().trim()+"");
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GETA_UTNCODE, list, new ResponseCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    timeDown();
                    UHelper.showToast(LoginActivity.this,getString(R.string.has_send_to_this_phone));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
    private void Login(){
        Map<String,Object> map=new HashMap<>();
        map.put("fphone",et_phoneNum.getText().toString().trim());
        map.put("fvalidate",et_authCode.getText().toString().trim());
        map.put("faliasState",1);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.LOGIN, map, new ResponseCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    UserInfoEntity entity=result.getObj();
                    ((ClientApp) getApplication()).saveLoginUser(entity);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    setAlias();
                    finish();
                }else {
                    UHelper.showToast(LoginActivity.this,result.getMsg());
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
    private void setAlias() {
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagAliasBean.alias= SessionManager.getInstance().getUser().getFdriverid();
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(this,1,tagAliasBean );
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }
}
