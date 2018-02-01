package com.babuwyt.siji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.HistoryOrderItemEntity;
import com.babuwyt.siji.ui.activity.HistoryOrderDetailsActivity;
import com.babuwyt.siji.ui.activity.LookAddressListActivity;
import com.babuwyt.siji.ui.activity.LookPicActivity;
import com.babuwyt.siji.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/22.
 */

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<HistoryOrderItemEntity> mList;
    public HistoryOrderAdapter(Context context,ArrayList<HistoryOrderItemEntity> list){
        this.mContext=context;
        this.mList=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_historyorder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HistoryOrderItemEntity entity=mList.get(position);
        holder.time.setText(DateUtils.timedate2(entity.getFshipmenttime())+" -- "+ DateUtils.timedate2(entity.getFunloadtime()));
        holder.tv_yundanhao.setText(entity.getFsendcarno());
        holder.tv_luxian.setText(entity.getFshipmentarea()+" -- "+entity.getFunloadarea());
        holder.tv_zengsong.setText(entity.getFgiving()==null || entity.getFgiving().equalsIgnoreCase("0.0")?"0":entity.getFgiving());
        holder.tv_shouru.setText(entity.getFtotal()==null || entity.getFtotal().equalsIgnoreCase("0.0")?"0":entity.getFtotal());
        holder.tv_lookpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, LookPicActivity.class);
                intent.putExtra("fownsendcarid",entity.getFownersendcarid());
                mContext.startActivity(intent);
            }
        });
        holder.tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, LookAddressListActivity.class);
                intent.putExtra("fownsendcarid",entity.getFownersendcarid());
                mContext.startActivity(intent);
            }
        });
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, HistoryOrderDetailsActivity.class);
                intent.putExtra("fsendcarid",entity.getFsendcarid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView tv_yundanhao;
        private TextView tv_luxian;
        private TextView tv_zengsong;
        private TextView tv_shouru;
        private TextView tv_lookpic;
        private TextView tv_address;
        private LinearLayout layout_item;
        public ViewHolder(View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time);
            tv_yundanhao=itemView.findViewById(R.id.tv_yundanhao);
            tv_luxian=itemView.findViewById(R.id.tv_luxian);
            tv_zengsong=itemView.findViewById(R.id.tv_zengsong);
            tv_shouru=itemView.findViewById(R.id.tv_shouru);
            tv_lookpic=itemView.findViewById(R.id.tv_lookpic);
            tv_address=itemView.findViewById(R.id.tv_address);
            layout_item=itemView.findViewById(R.id.layout_item);
        }
    }
}
