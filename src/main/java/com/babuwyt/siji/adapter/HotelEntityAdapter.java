package com.babuwyt.siji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.ActionPicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.utils.request.ImageOptions;

import org.xutils.x;

import java.util.ArrayList;


/**
 * Created by lenovo on 2018/1/24.
 */

public class HotelEntityAdapter extends SectionedRecyclerViewAdapter<HotelEntityAdapter.HeaderHolder, HotelEntityAdapter.DescHolder, RecyclerView.ViewHolder> {
    public ArrayList<ActionPicEntity> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private SparseBooleanArray mBooleanMap;//记录下哪个section是被打开的

    public HotelEntityAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<ActionPicEntity>();
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(ArrayList<ActionPicEntity> mList) {
        if (mList!=null){
            this.mList = mList;
        }
    }

    @Override
    protected int getSectionCount() {
        return mList==null ? 0 : mList.size();

    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = mList.get(section).getPicEntities().size();
//        if (count >= 8 && !mBooleanMap.get(section)) {
//            count = 8;
//        }
        return mBooleanMap.get(section)==false ? count : 0;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.hotel_title_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.adapter_lookpic_item, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                mBooleanMap.put(section, !isOpen);
                holder.openView.setImageResource(R.drawable.icon_next_right_gray);
                notifyDataSetChanged();
            }
        });

        holder.titleView.setText(mList.get(section).getName());
        holder.openView.setImageResource(mBooleanMap.get(section) ? R.drawable.icon_next_right_gray : R.drawable.icon_close_right_gray);

    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(DescHolder holder, int section, int position) {
//        holder.descView.setText(mList.get(section).tagInfoList.get(position).tagName);
        holder.layout_pic.getLayoutParams().width= (DensityUtils.deviceWidthPX(mContext)-DensityUtils.dip2px(mContext,20))/3;
        holder.layout_pic.getLayoutParams().height= (DensityUtils.deviceWidthPX(mContext)-DensityUtils.dip2px(mContext,20))/3;
        x.image().bind(holder.img_img, BaseURL.BASE_IMAGE_URI+mList.get(section).getPicEntities().get(position).getFpicture(), ImageOptions.options(ImageView.ScaleType.CENTER_CROP));

    }

    public class DescHolder extends RecyclerView.ViewHolder {
        public ImageView img_img;
        public LinearLayout layout_pic;
        public DescHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            img_img = (ImageView) itemView.findViewById(R.id.img_img);
            layout_pic = (LinearLayout) itemView.findViewById(R.id.layout_pic);
        }
    }
    public class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public ImageView openView;
        public HeaderHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            titleView = (TextView) itemView.findViewById(R.id.tv_title);
            openView = (ImageView) itemView.findViewById(R.id.tv_open);
        }
    }
}
