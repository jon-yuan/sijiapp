package com.babuwyt.siji.views;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.babuwyt.siji.R;


/**
 * @describe 自定义居中弹出dialog
 */
public class LoadingDialog extends Dialog{

    private Context mContext;
    private ImageView loadingPic;
    private AnimationDrawable animationDrawable;
    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAttributes();
    }
    private void setAttributes(){
//        setContentView(R.layout.loading_dialog);
        View view= LayoutInflater.from(mContext).inflate(R.layout.loading_dialog,null);
        setContentView(view);
        loadingPic=view.findViewById(R.id.loadingPic);
        animationDrawable=(AnimationDrawable) loadingPic.getBackground();
        if(!animationDrawable.isRunning()){
            animationDrawable.start();
        }



        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        layoutParams.alpha = 1;//alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明，自身不可见  (dialog自身的透明度）
//        layoutParams.dimAmount = 0;//dialog所在窗体的背景  dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗
        getWindow().setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        getWindow().setWindowAnimations(R.style.loading_dialog1);//设置dialog所在窗体的动画(即show和dismiss的动画效果）

//        WindowManager windowManager = ((Activity) mContext).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        lp.width = DensityUtils.deviceWidthPX(mContext); // 设置dialog宽度为屏幕的4/5
//        lp.height = DensityUtils.deviceHeightPX(mContext); // 设置dialog宽度为屏幕的4/5
        setCanceledOnTouchOutside(false);
    }

    public void showDialog(){
        if (!isShowing()){
            show();
        }
    }
    public void dissDialog(){
        if (isShowing()){
            dismiss();
        }
    }
}

