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

import uk.co.senab.photoview.PhotoView;

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
    private boolean isLoading=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLoading=getIntent().getBooleanExtra("isload",false);
        init();
    }

    private void init() {
        index=getIntent().getIntExtra("index",0);
        mList.addAll((ArrayList<PicEntity>) getIntent().getSerializableExtra("list"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        vList=new ArrayList<View>();
        for (int i=0;i<mList.size();i++){
            PhotoView photoView=new PhotoView(this);
            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (isLoading){
                x.image().bind(photoView, BaseURL.BASE_IMAGE_URI+mList.get(i).getPicture(), ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
            }else {
                x.image().bind(photoView, BaseURL.BASE_IMAGE_URI+mList.get(i).getFpicture(), ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
            }
            vList.add(photoView);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        bannerview.setViewList(vList);
        bannerview.setCurrentItem(index);

    }
}
