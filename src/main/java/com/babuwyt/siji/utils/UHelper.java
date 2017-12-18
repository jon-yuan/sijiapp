package com.babuwyt.siji.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * 设备及系统相关工具类
 * Created by wutong on 2014/10/5.
 */
public class UHelper {
    public static final int TYPE_VERSION_NAME = 0X01;
    public static final int TYPE_VERSION_CODE = 0X02;

    /**
     * 获取设备唯一id
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static final String getUniqueID(Context context) {
        if (context == null) {
            return null;
        }
        Context appContent = context.getApplicationContext();
        final TelephonyManager tm = (TelephonyManager) appContent.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(appContent.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }
    /**
     * 判断APP是否已经启动
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * 获取version_name或者version_code
     *
     * @param context
     * @param version_type TYPE_VERSION_NAME,TYPE_VERSION_CODE
     * @return
     */
    public static final String getAppVersionInfo(Context context, int version_type) {
        if (context == null) {
            return null;
        }
        String versionCode = null;
        String versionName = null;
        Context appContent = context.getApplicationContext();

        PackageManager pm = appContent.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(appContent.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            versionName = null;
            versionCode = null;
        }
        if (version_type == TYPE_VERSION_CODE) {
            return versionCode;
        } else {
            return versionName;
        }

    }

    /**
     * 获取android 系统版本
     *
     * @return
     */
    public static final String getSystemVersion() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        String sysVersion = "Android " + currentapiVersion;
        return sysVersion;
    }

    /**
     * show Toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 正则表达式验证手机
     */
    public static boolean isPhone(String phone) {
        String s = "[1][3578]\\d{9}";
        return phone.matches(s);
    }

    /**
     * 校验身份证
     *
     * @param idcard
     * @return
     */
    public static boolean isIdCard(String idcard) {
        String s = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        return idcard.matches(s);
    }

    public static boolean isCar(String carnum) {
        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        return carnum.matches(carnumRegex);
    }

    /**
     * 验证密码
     */
    public static boolean isPsd(String psd) {
        String s = "^[0-9a-zA-Z-_]{6,20}$";
        return psd.matches(s);
    }

    public static String setState(String state) {
        String str = "";
        switch (state) {
            case "0":
                str="待接单";
                break;
            case "1":
                str = "装货已签到";
                break;
            case "2":
                str = "装货已拍照";
                break;
            case "3":
                str = "已装货（装货照片已审核）";
                break;
            case "4":
                str = "卸货已签到";
                break;
            case "5":
                str = "已卸货（签收单照片已审核）";
                break;
            case "6":
                str = "签收单已交回";
                break;
            case "7":
                str = "签收单已确认";
                break;
        }
        return str;
    }

    /**
     * 匹配Luhn算法：可用于检测银行卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankNumbuter(String cardNo) {
        int[] cardNoArr = new int[cardNo.length()];
        for (int i = 0; i < cardNo.length(); i++) {
            cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i)));
        }
        for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
        }
        int sum = 0;
        for (int i = 0; i < cardNoArr.length; i++) {
            sum += cardNoArr[i];
        }
        return sum % 10 == 0;


    }
}
