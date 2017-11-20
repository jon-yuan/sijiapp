package com.babuwyt.siji.utils.request;

import android.widget.ImageView;

import com.babuwyt.siji.R;


/**
 * Created by lenovo on 2017/10/18.
 */

public class ImageOptions {

    /**
     * 图片未加载出来之前显示的默认图片
     */
    public static org.xutils.image.ImageOptions options(boolean Circular){
        org.xutils.image.ImageOptions options=new org.xutils.image.ImageOptions.Builder().
                setLoadingDrawableId(R.mipmap.ic_launcher)
                .setUseMemCache(true).setCircular(Circular)
                .build();
        return options;
    }
    public static org.xutils.image.ImageOptions options(ImageView.ScaleType imageScaleType){
        org.xutils.image.ImageOptions options=new org.xutils.image.ImageOptions.Builder().
                setLoadingDrawableId(R.color.colorAccent)
                .setUseMemCache(true)
                .setImageScaleType(imageScaleType)
                .build();
        return options;
    }
}
