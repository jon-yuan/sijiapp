package com.babuwyt.siji.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.BankInfoEntity;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.finals.SharePrefKeys;
import com.babuwyt.siji.ui.activity.MainActivity;
import com.babuwyt.siji.utils.MapUtil;
import com.babuwyt.siji.utils.SharePreferencesUtils;
import com.babuwyt.siji.utils.UHelper;
import com.google.gson.Gson;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by lenovo on 2017/9/21.
 */

public class ClientApp extends Application {
    public static double lat = 0;
    public static double lng=0;
    public static String adCode=null;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        initUserInfo();
        initJpush();
        Location();
    }
    private void initJpush(){
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    private void initUserInfo() {
        String data = SharePreferencesUtils.getString(this, SharePrefKeys.XML_PREFERENCES, SharePrefKeys.XML_NAME_USER_INFO, "");
        if (!TextUtils.isEmpty(data)) {
            Gson gson = new Gson();
            UserInfoEntity user = gson.fromJson(data, UserInfoEntity.class);
            SessionManager sessionManager = SessionManager.getInstance();
            if (user != null) {
                sessionManager.setUser(user);
            }
        }
    }
    /**
     * 保存登录用户的信息
     *
     * @param user
     */
    public void saveLoginUser(UserInfoEntity user) {
        if (user == null) {
            return;
        }
        Gson gson = new Gson();
        String data = gson.toJson(user);
        SharePreferencesUtils.saveString(this, SharePrefKeys.XML_PREFERENCES, SharePrefKeys.XML_NAME_USER_INFO, data);
        this.initUserInfo();
    }
    /**
     * 清除用户登录信息
     */
    public void clearLoginUser() {
        SharePreferencesUtils.clearAll(this, SharePrefKeys.XML_PREFERENCES);
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.setUser(null);
    }

    /**
     * 保存绑定的银行卡信息
     * @param info
     */
    public void saveBankinfo(BankInfoEntity info) {
        if (info == null) {
            return;
        }
        Gson gson = new Gson();
        String data = gson.toJson(info);
        SharePreferencesUtils.saveString(this, SharePrefKeys.XML_BANKINFO, SharePrefKeys.XML_BANKINFO, data);
    }
    public BankInfoEntity getBankinfo(){
        String data = SharePreferencesUtils.getString(this, SharePrefKeys.XML_BANKINFO, SharePrefKeys.XML_BANKINFO, "");
        if (!TextUtils.isEmpty(data)) {
            Gson gson = new Gson();
            BankInfoEntity info = gson.fromJson(data, BankInfoEntity.class);
            return info;
        }
        return null;
    }

    private void Location() {
        MapUtil.getInstance(this).Location(new AMapLocationListener() {
            @SuppressLint("NewApi")
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    lat=aMapLocation.getLatitude();
                    lng=aMapLocation.getLongitude();
                    adCode=aMapLocation.getAdCode();
                }
            }
        },false);
    }

}
