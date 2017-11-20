package com.babuwyt.siji.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.utils.RQcode;
import com.google.zxing.WriterException;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by lenovo on 2017/11/9.
 */
@ContentView(R.layout.activity_batmap)
public class RQcodeBatmapActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.img_rqcode)
    ImageView img_rqcode;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(true);
        init();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getRQCode();
    }

    private void getRQCode(){
        RQcode rQcode=new RQcode();
        try {
            int size= DensityUtils.dip2px(this,300);

            bitmap=rQcode.CreateTwoDCode("D17220171106001",size);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap!=null){
            img_rqcode.setImageBitmap(bitmap);
        }
    }
}
