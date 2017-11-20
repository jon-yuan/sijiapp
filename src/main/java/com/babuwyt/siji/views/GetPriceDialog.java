package com.babuwyt.siji.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Timer;
import java.util.TimerTask;


/**
 * @describe 自定义居中弹出dialog
 */
public class GetPriceDialog extends Dialog implements DialogInterface.OnDismissListener{
    private GetPriceDialog mDialog;
    private Context mContext;
    private ImageView img_close;
    private TextView tv_commit;
    private TextView tv_msg;
    private TextView tv_auth;
    private EditText et_auth;

    private String bancname, msg;
    private boolean Cancel;

    private int time = 60;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                tv_auth.setText("(" + time + "s)" + "后获取");
                tv_auth.setEnabled(false);
            } else {
                timer.cancel();
                timer = null;
                time = 60;
                tv_auth.setText("获取验证码");
                tv_auth.setEnabled(true);
            }
        }
    };
    public GetPriceDialog(Context context) {
        super(context, R.style.dialog_custom);
        this.mContext = context;
//        if (mDialog==null){
//            mDialog=new GetPriceDialog(context);
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAttributes();
    }
    private void setAttributes() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_getprice, null);
        setContentView(view);
        img_close = view.findViewById(R.id.img_close);
        tv_msg = view.findViewById(R.id.tv_msg);
        tv_commit = view.findViewById(R.id.tv_commit);
        tv_auth = view.findViewById(R.id.tv_auth);
        et_auth = view.findViewById(R.id.et_auth);
//        et_auth.setFilters(new InputFilter[]{new InputMoney()});
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_auth.getText().toString())){
                    return;
                }
                commitCallBack.onCommit(et_auth.getText().toString());
            }
        });
        tv_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        setOnDismissListener(this);
    }

    public void setCanceledTouchOutside(boolean b) {
        this.Cancel = b;
    }

    public void setMsg(String name) {
        this.msg = name;
    }

    //该方法必须在oncreat以后才能调用
    public void setDownTime() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 0, 1000);
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

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
        et_auth.setText("");
    }

    public interface CommitCallBack {
        void onCommit(String price);
    }

    private CommitCallBack commitCallBack;

    public void setCommitCallBack(CommitCallBack commitCallBack) {
        this.commitCallBack = commitCallBack;
    }

    public interface AuthCallBack {
        void onAuth();
    }

    private AuthCallBack callBack;

    public void setAuthCallBack(AuthCallBack callBack) {
        this.callBack = callBack;
    }


//    class InputMoney implements InputFilter {
//        @Override
//        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
//            if (charSequence.toString().equals(".") && i2 == 0 && i3 == 0) {//判断小数点是否在第一位
//                et_money.setText(0+""+charSequence+spanned);//给小数点前面加0
//                et_money.setSelection(2);//设置光标
//            }
//
//            if (spanned.toString().indexOf(".") != -1 && (spanned.length() - spanned.toString().indexOf(".")) > 2) {//
//                if ((spanned.length() - i2) < 3) {//判断现在输入的字符是不是在小数点后面
//                    return "";//过滤当前输入的字符
//                }
//            }
//            return null;
//        }
//    }

}


