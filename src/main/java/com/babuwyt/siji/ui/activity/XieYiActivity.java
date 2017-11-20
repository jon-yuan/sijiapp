package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.XieyiBean;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/22.
 */
@ContentView(R.layout.activity_xieyi)
public class XieYiActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.webview)
    WebView webview;
    private String xieyi = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        getText();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setWebview();
    }

    private void getText() {
        XUtil.GetPing(BaseURL.GETTEXT, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<XieyiBean>() {
            @Override
            public void onSuccess(XieyiBean result) {
                super.onSuccess(result);
                if (result.isSuccess()) {
                    xieyi = result.getObj();
                    setWebview();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    private void setWebview() {
        WebSettings webSettings = webview.getSettings();
            //支持缩放，默认为true。
        webSettings.setSupportZoom(true);
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
//        webview.loadData(xieyi, "text/html; charset=UTF-8", null);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(BaseURL.BASE_URL+"appcommonLegalAgreement/getLegalAgreementHtml/APPLegalAgreement");
    }
    @Override
    protected void onDestroy() {
        if (webview!= null) {
            webview.loadUrl(null);
            webview.clearHistory();
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview= null;
        }
        super.onDestroy();
    }

    @Event(value = {R.id.tv_link})
    private void getE(View v){
        switch (v.getId()){
            case R.id.tv_link:
                testCall();
                break;
        }
    }
    public void testCall() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone(SessionManager.getInstance().getServicephone());
        }
    }
    @SuppressLint("MissingPermission")
    public void callPhone(String phonenum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phonenum);
        intent.setData(data);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(SessionManager.getInstance().getServicephone());
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("您没有授权成功，无法使用拨打电话功能");
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
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
