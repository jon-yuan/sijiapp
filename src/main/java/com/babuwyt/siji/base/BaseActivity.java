package com.babuwyt.siji.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.babuwyt.siji.R;
import com.babuwyt.siji.utils.SystemUtils;
import com.babuwyt.siji.views.LoadingDialog;

import org.xutils.x;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lenovo on 2017/9/21.
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean useThemestatusBarColor = false;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useStatusBarColor = true;//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(true);
        x.view().inject(this);
        dialog=new LoadingDialog(this);
    }

    protected void setStatusBar(boolean b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setStatusBarTextColor(b);
    }

    /**
     * 设置状态栏字体颜色
     * @param b
     */
    protected void setStatusBarTextColor(boolean b){
        if (SystemUtils.isMIUI()){
            setMiuiStatusBarDarkMode(this,b);
        }else if (SystemUtils.isFlyme()){
            //魅族
            setMeizuStatusBarDarkIcon(this,b);
        }else {
            //其他6.0以上手机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && b==true) {
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 小米 修改状态栏字体颜色  dark和light两种
     * @param activity
     * @param darkmode
     * @return
     */
    public boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 魅族 修改状态栏字体颜色  dark和light两种
     * @param activity
     * @param dark
     * @return
     */
    public boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
