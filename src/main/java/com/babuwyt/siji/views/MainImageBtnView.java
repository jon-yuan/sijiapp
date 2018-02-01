package com.babuwyt.siji.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.babuwyt.siji.R;

/**
 * Created by lenovo on 2018/1/29.
 */

public class MainImageBtnView extends LinearLayout {
    public MainImageBtnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_main_imagebtn, this);
        initView();
    }

    private void initView() {

    }
}
