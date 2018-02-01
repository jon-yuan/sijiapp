package com.babuwyt.siji.ui.activity.activity2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.ui.activity.XieYiActivity;
import com.babuwyt.siji.utils.UHelper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by lenovo on 2018/1/26.
 */
@ContentView(R.layout.activity_login2)
public class LoginActivity2 extends BaseActivity {
    @ViewInject(R.id.tv_bottomxieyi)
    TextView tvXieyi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        ChangeColor();

    }

    /**
     * 底部协议字体变色
     * R.color.color_btn_blue
     */

    private void ChangeColor() {
        String str = tvXieyi.getText().toString();
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        tvXieyi.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new TextClick(),10,str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_btn_blue)), 10, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvXieyi.setText(spannable);
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) { //在此处理点击事件
            startClass(XieYiActivity.class);
        }

    }
    @Event(value = {R.id.btn_login})
    private void getE(View v){
        switch (v.getId()){
            case R.id.btn_login:
                startClass(MainActivity2.class);
                break;
        }
    }

}
