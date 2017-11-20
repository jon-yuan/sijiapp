package com.babuwyt.siji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.LookAddressEntity;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */

public class LookAddressListAdapter extends RecyclerView.Adapter<LookAddressListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<LookAddressEntity> mList;
    public LookAddressListAdapter(Context context,ArrayList<LookAddressEntity> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_lookaddresslist_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LookAddressEntity entity=mList.get(position);
        if (entity.getFromto().equalsIgnoreCase("FROM")){
            holder.tv_address_type.setText(R.string.tihuodi);
        }else {
            holder.tv_address_type.setText(R.string.xiehuodi);
        }
        holder.tv_address.setText(entity.getSsq()+entity.getFaddress());
        holder.tv_link.setText(entity.getFlinkman()+"\t\t"+entity.getFphone());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address;
        TextView tv_address_type;
        TextView tv_name;
        TextView tv_link;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_address=itemView.findViewById(R.id.tv_address);
            tv_address_type=itemView.findViewById(R.id.tv_address_type);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_link=itemView.findViewById(R.id.tv_link);
        }
    }
}
