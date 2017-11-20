package com.babuwyt.siji.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.utils.DensityUtils;


/**
 * @describe 自定义居中弹出dialog
 */
public class SignInDialog extends Dialog{

    private Context mContext;
    private TextView tv_msg;
    private ImageView img_icon;


    private int imgID;
    private String msg,btn1,btn2;
    private boolean Cancel;
    public SignInDialog(Context context) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAttributes();
    }
    private void setAttributes() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sign, null);
        setContentView(view);
        img_icon=view.findViewById(R.id.img_icon);
        tv_msg=view.findViewById(R.id.tv_msg);

        img_icon.setImageResource(imgID);
        tv_msg.setText(msg);


        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        layoutParams.alpha = 1;//alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明，自身不可见  (dialog自身的透明度）
//        layoutParams.dimAmount = 0;//dialog所在窗体的背景  dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗
        getWindow().setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        getWindow().setWindowAnimations(R.style.bottom_menu_animation);//设置dialog所在窗体的动画(即show和dismiss的动画效果）

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = DensityUtils.deviceWidthPX(mContext); // 设置dialog宽度为屏幕的4/5
//        lp.height = DensityUtils.deviceHeightPX(mContext); // 设置dialog宽度为屏幕的4/5
        setCanceledOnTouchOutside(Cancel);
        setCancelable(Cancel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isShowing()){
                    dismiss();
                }
            }
        }, 2000);

    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public void setImg(int imgRid){
        this.imgID=imgRid;
    }
    public void setCanceledTouchOutside(boolean b){
        this.Cancel=b;
    }

    public void showDialog() {
        if (!isShowing()) {
            show();
        }
    }
    public void dissDialog() {
        if (isShowing()) {
            dismiss();
        }
    }
}


