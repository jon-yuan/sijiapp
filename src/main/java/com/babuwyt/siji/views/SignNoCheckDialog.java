package com.babuwyt.siji.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.utils.DensityUtils;

/**
 * Created by lenovo on 2018/1/3.
 */

public class SignNoCheckDialog extends Dialog {


    private Context mContext;
    private TextView tv_select,tv_saoyisao,tv_input;
    public SignNoCheckDialog(Context context) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_signnocheck, null);
        setContentView(view);
        tv_select=view.findViewById(R.id.tv_select);
        tv_saoyisao=view.findViewById(R.id.tv_saoyisao);
        tv_input=view.findViewById(R.id.tv_input);


        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mSelectPosition.position(1);
            }
        });
        tv_saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mSelectPosition.position(2);
            }
        });
        tv_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mSelectPosition.position(3);
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
    public interface SelectPosition{
        void position(int position);
    }

    private SelectPosition mSelectPosition;
    public void setSelectPosition(SelectPosition selectPosition){
        this.mSelectPosition=selectPosition;
    }
}
