package com.babuwyt.siji.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.utils.DensityUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @describe 自定义居中弹出dialog
 */
public class ImgCheckDialog extends Dialog{

    private Context mContext;
    private TextView tv_quxiao,tv_xiangce,tv_paizhao;
    public ImgCheckDialog(Context context) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAttributes();
    }

    private void setAttributes() {
//        setContentView(R.layout.loading_dialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_imagecheck, null);
        setContentView(view);
        tv_paizhao=view.findViewById(R.id.tv_paizhao);
        tv_xiangce=view.findViewById(R.id.tv_xiangce);
        tv_quxiao=view.findViewById(R.id.tv_quxiao);


        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                callBackPaizhao.callbackPaizhao();
            }
        });
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                callBackXiangce.callbackXiangce();
            }
        });
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        layoutParams.alpha = 1;//alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明，自身不可见  (dialog自身的透明度）
//        layoutParams.dimAmount = 0;//dialog所在窗体的背景  dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗
        getWindow().setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        getWindow().setWindowAnimations(R.style.bottom_menu_animation);//设置dialog所在窗体的动画(即show和dismiss的动画效果）

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = DensityUtils.deviceWidthPX(mContext); // 设置dialog宽度为屏幕的4/5
//        lp.height = DensityUtils.deviceHeightPX(mContext); // 设置dialog宽度为屏幕的4/5
        setCanceledOnTouchOutside(true);
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
    public interface CallBackPaizhao{
        void callbackPaizhao();
    }
    public interface CallBackXiangce{
        void callbackXiangce();
    }

    private CallBackPaizhao callBackPaizhao;
    private CallBackXiangce callBackXiangce;

    public void setCallBackPaizhao(CallBackPaizhao callBackPaizhao){
        this.callBackPaizhao=callBackPaizhao;
    }
    public void setCallBackXiangce(CallBackXiangce callBackXiangce){
        this.callBackXiangce=callBackXiangce;
    }
}


