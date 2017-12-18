package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.ImageOptions;
import com.babuwyt.siji.views.BannerView;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/11/29.
 */
@ContentView(R.layout.activity_loolbigpicture)
public class LookBigPictureActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.bannerview)
    BannerView bannerview;

    private ArrayList<View> vList;
    private ArrayList<PicEntity> mList=new ArrayList<PicEntity>();
    private int index=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        index=getIntent().getIntExtra("index",0);
        Log.d("=======--",new Gson().toJson((ArrayList<PicEntity>) getIntent().getSerializableExtra("list"))+"");
        mList.addAll((ArrayList<PicEntity>) getIntent().getSerializableExtra("list"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        vList=new ArrayList<View>();
        for (int i=0;i<mList.size();i++){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            x.image().bind(imageView, BaseURL.BASE_IMAGE_URI+mList.get(i).getFpicture(), ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
            vList.add(imageView);
            Log.d("巨鲸===",BaseURL.BASE_IMAGE_URI+mList.get(i).getFpicture());
        }

        bannerview.setViewList(vList);
        bannerview.setCurrentItem(index);

    }
}