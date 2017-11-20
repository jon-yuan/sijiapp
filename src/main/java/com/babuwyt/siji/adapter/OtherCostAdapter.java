package com.babuwyt.siji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.OtherCostEntity;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/21.
 */

public class OtherCostAdapter extends RecyclerView.Adapter<OtherCostAdapter.ViewHolder>{
private Context mContext;
private ArrayList<OtherCostEntity> mList;
    public OtherCostAdapter (Context context,ArrayList<OtherCostEntity> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.adapter_item_othercost,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_cost.setText(mList.get(position).getFmoney()+"");
        holder.tv_yuanyin.setText(mList.get(position).getFname());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_cost,tv_yuanyin;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_cost=itemView.findViewById(R.id.tv_cost);
            tv_yuanyin=itemView.findViewById(R.id.tv_yuanyin);
        }
    }
}
