package com.babuwyt.siji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.ui.activity.LookBigPictureActivity;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.utils.request.ImageOptions;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */

public class LookPicAdapter extends RecyclerView.Adapter<LookPicAdapter.ViewHoder> {
    private Context mCntext;
    private ArrayList<PicEntity> mList;
    private SparseBooleanArray mBooleanMap;
    private LayoutInflater mInflater;
    public LookPicAdapter(Context context,ArrayList<PicEntity> list){
        this.mCntext=context;
        this.mList=list;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }
    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mCntext).inflate(R.layout.adapter_lookpic_item,parent,false);
//
        return new ViewHoder(v);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, final int position) {
        holder.layout_pic.getLayoutParams().width= (DensityUtils.deviceWidthPX(mCntext)-DensityUtils.dip2px(mCntext,20))/3;
        holder.layout_pic.getLayoutParams().height= (DensityUtils.deviceWidthPX(mCntext)-DensityUtils.dip2px(mCntext,20))/3;
        x.image().bind(holder.img_img, BaseURL.BASE_IMAGE_URI+mList.get(position).getFpicture(), ImageOptions.options(ImageView.ScaleType.CENTER_CROP));

        holder.img_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mCntext,LookBigPictureActivity.class);
                intent.putExtra("index",position);
                intent.putExtra("list",mList);
                mCntext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        ImageView img_img;
        LinearLayout layout_pic;
        public ViewHoder(View itemView) {
            super(itemView);
            img_img=itemView.findViewById(R.id.img_img);
            layout_pic=itemView.findViewById(R.id.layout_pic);
        }
    }
}
