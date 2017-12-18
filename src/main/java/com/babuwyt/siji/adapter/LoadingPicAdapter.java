package com.babuwyt.siji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.ui.activity.LookBigPictureActivity;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.utils.request.ImageOptions;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */

public class LoadingPicAdapter extends BaseAdapter {
    private Context mCntext;
    private ArrayList<PicEntity> mList;

    public LoadingPicAdapter(Context context) {
        this.mCntext = context;
        this.mList =new ArrayList<PicEntity>();
    }
    public void setmList(ArrayList<PicEntity> list){
        if (list!=null){
            mList=list;
//            notifyDataSetChanged();
        }
    }

//    @Override
//    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mCntext).inflate(R.layout.adapter_loadpic_item, parent, false);
////
//        return new ViewHoder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHoder holder, final int position) {
//        holder.layout_pic.getLayoutParams().width = (DensityUtils.deviceWidthPX(mCntext) - DensityUtils.dip2px(mCntext, 40)) / 3;
//        holder.layout_pic.getLayoutParams().height = (DensityUtils.deviceWidthPX(mCntext) - DensityUtils.dip2px(mCntext, 40)) / 3;
////        Log.d("=data=",mList.get(position).getPicture());
//
//        holder.img_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (deleteListener != null) {
//                    deleteListener.onDelete(position);
//                }
//            }
//        });
//        holder.img_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(mCntext,LookBigPictureActivity.class);
//                intent.putExtra("index",position);
//                intent.putExtra("list",mList);
//                mCntext.startActivity(intent);
//            }
//        });
//        x.image().bind(holder.img_img, BaseURL.BASE_IMAGE_URI + mList.get(position).getPicture(), ImageOptions.options(ImageView.ScaleType.CENTER_CROP));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view =LayoutInflater.from(mCntext).inflate(R.layout.adapter_loadpic_item, null);
            x.view().inject(holder,view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.layout_pic.getLayoutParams().width = (DensityUtils.deviceWidthPX(mCntext) - DensityUtils.dip2px(mCntext, 40)) / 3;
        holder.layout_pic.getLayoutParams().height = (DensityUtils.deviceWidthPX(mCntext) - DensityUtils.dip2px(mCntext, 40)) / 3;
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener != null) {
                    deleteListener.onDelete(i);
                }
            }
        });
        holder.img_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mCntext,LookBigPictureActivity.class);
                intent.putExtra("index",i);
                intent.putExtra("list",mList);
                mCntext.startActivity(intent);
            }
        });
        x.image().bind(holder.img_img, BaseURL.BASE_IMAGE_URI + mList.get(i).getFpicture(), ImageOptions.options(ImageView.ScaleType.CENTER_CROP));
        return view;
    }

    class ViewHolder{
        @ViewInject(R.id.img_img)
        ImageView img_img;
        @ViewInject(R.id.img_delete)
        ImageView img_delete;
        @ViewInject(R.id.layout_pic)
        RelativeLayout layout_pic;
    }
//    class ViewHoder extends RecyclerView.ViewHolder {
//        ImageView img_img, img_delete;
//        RelativeLayout layout_pic;
//
//        public ViewHoder(View itemView) {
//            super(itemView);
//            img_img = itemView.findViewById(R.id.img_img);
//            img_delete = itemView.findViewById(R.id.img_delete);
//            layout_pic = itemView.findViewById(R.id.layout_pic);
//        }
//    }

    public interface DeleteListener {
        void onDelete(int position);
    }

    private DeleteListener deleteListener;

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }
}
