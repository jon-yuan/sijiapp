package com.babuwyt.siji.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
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


/**
 * @describe 自定义居中弹出dialog
 */
public class RechargeDialog extends Dialog implements DialogInterface.OnDismissListener{

    private Context mContext;
    private ImageView img_close;
    private TextView tv_backname;
    private TextView tv_commit;
    private TextView tv_title;
    private TextView tv_msg;
    private EditText et_money;

    private String bancname,title,msg,commit;
    private boolean Cancel;
    public RechargeDialog(Context context) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAttributes();
    }

    private void setAttributes() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_recharge, null);
        setContentView(view);
        img_close=view.findViewById(R.id.img_close);
        tv_backname=view.findViewById(R.id.tv_backname);
        tv_commit=view.findViewById(R.id.tv_commit);
        tv_title=view.findViewById(R.id.tv_title);
        tv_msg=view.findViewById(R.id.tv_msg);
        et_money=view.findViewById(R.id.et_money);
        et_money.setFilters(new InputFilter[]{new InputMoney()});
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_money.getText().toString())){
                    return;
                }
                commitCallBack.onCommit(Double.parseDouble(et_money.getText().toString()));
            }
        });
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
        setOnDismissListener(this);
    }
    public void setCanceledTouchOutside(boolean b){
        this.Cancel=b;
    }

    public void setBancname(String name){
        this.bancname=name;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public void setTitle(String s){
        this.title=s;
    }
    public void showDialog() {
        if (!isShowing()) {
            tv_backname.setText(bancname);
            tv_commit.setText(commit);
            tv_title.setText(title);
            tv_msg.setText(msg);
            show();
        }
    }

    public void dissDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        et_money.setText("");
    }

    public interface CommitCallBack{
        void onCommit(double price);
    }
    private CommitCallBack commitCallBack;
    public void setCommitCallBack(String s,CommitCallBack commitCallBack){
        this.commit=s;
        this.commitCallBack=commitCallBack;
    }

    class InputMoney implements InputFilter {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            if (charSequence.toString().equals(".") && i2 == 0 && i3 == 0) {//判断小数点是否在第一位
                et_money.setText(0+""+charSequence+spanned);//给小数点前面加0
                et_money.setSelection(2);//设置光标
            }

            if (spanned.toString().indexOf(".") != -1 && (spanned.length() - spanned.toString().indexOf(".")) > 2) {//
                if ((spanned.length() - i2) < 3) {//判断现在输入的字符是不是在小数点后面
                    return "";//过滤当前输入的字符
                }
            }
            return null;
        }
    }

}


