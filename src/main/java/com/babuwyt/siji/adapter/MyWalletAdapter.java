package com.babuwyt.siji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.TranListEntity;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/22.
 */

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHolder> {
    protected static int HEADER_INSTALL=1;
    protected static int HEADER_NORMAL=-1;
    private View header;
    private Context mContext;
    private ArrayList<TranListEntity> mList;

    public MyWalletAdapter(Context context, ArrayList<TranListEntity> list) {
        this.mContext = context;
        this.mList = list;
    }
    public void setHeader(View view){
        header=view;
        notifyItemInserted(0);
    }
    @Override
    public int getItemCount() {
        if (header==null){
            return mList.size();
        }
        return mList.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if (header==null){
            return  HEADER_NORMAL;
        }
        if (position == 0){
            return HEADER_INSTALL;
        }
        return HEADER_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (header!=null && viewType==HEADER_INSTALL){
            return new ViewHolder(header);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_mywallet_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position)==HEADER_INSTALL){
            return;
        }else {
            switch (mList.get(position).getDealType()) {
                case 1:
                    holder.img_icon.setImageResource(R.drawable.icon_chong);
                    break;
                case 2:
                    holder.img_icon.setImageResource(R.drawable.icon_ti);
                    break;
                case 3:
                    holder.img_icon.setImageResource(R.drawable.icon_zhi);
                    break;
            }
            holder.tv_money.setText(mList.get(position).getDealMoney() + "");
            holder.tv_ordernum.setText(mList.get(position).getTransportNo());
            holder.tv_time.setText(mList.get(position).getDealTime());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_icon;
        private TextView tv_time;
        private TextView tv_ordernum;
        private TextView tv_money;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView == header) return;
                img_icon = itemView.findViewById(R.id.img_icon);
                tv_time = itemView.findViewById(R.id.tv_time);
                tv_ordernum = itemView.findViewById(R.id.tv_ordernum);
                tv_money = itemView.findViewById(R.id.tv_money);
        }
    }
}
