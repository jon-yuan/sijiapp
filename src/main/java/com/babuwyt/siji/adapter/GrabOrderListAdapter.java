package com.babuwyt.siji.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.GrabOrderItemEntity;
import com.babuwyt.siji.ui.activity.OrderDetailsActivity;
import com.babuwyt.siji.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/25.
 */

public class GrabOrderListAdapter extends RecyclerView.Adapter<GrabOrderListAdapter.ViewHolder>{
    protected static int NO_DATA_TYPE=-1;
    private Context mContext;
    private ArrayList<GrabOrderItemEntity> mList;
    public GrabOrderListAdapter(Context context,ArrayList<GrabOrderItemEntity> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size()>0){
            return super.getItemViewType(position);
        }
        return NO_DATA_TYPE;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==NO_DATA_TYPE){
            TextView emptyView = new TextView(mContext);
            emptyView.setTextColor(R.color.black_666);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            emptyView.setText("还没可以抢的订单哦，稍后再来！");
            emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            return new ViewHolder(emptyView);
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_graborderllist_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemViewType(position)==NO_DATA_TYPE){
            return;
        };
        holder.tv_time.setText(DateUtils.timedate(mList.get(position).getFdelivergoodtime()));
        holder.tv_goodname.setText(mList.get(position).getFgoodtype());
        holder.tv_xuqiu.setText(mList.get(position).getFtrucktype()+mContext.getString(R.string.mi)+mContext.getString(R.string.xiegang)+mList.get(position).getFweight()+mContext.getString(R.string.dun));
        holder.tv_start.setText(mList.get(position).getFscityname());
        holder.tv_end.setText(mList.get(position).getFucityname());
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onListener(position);
                }
            }
        });
        holder.layout_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, OrderDetailsActivity.class);
                intent.putExtra("fsendcarno",mList.get(position).getFsendcarno());
                mContext.startActivity(intent);
            }
        });
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_call;
        private TextView tv_time,tv_goodname,tv_xuqiu,tv_start,tv_end;
        private LinearLayout layout_details;
        public ViewHolder(View itemView) {
            super(itemView);
            img_call=itemView.findViewById(R.id.img_call);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_goodname=itemView.findViewById(R.id.tv_goodname);
            tv_xuqiu=itemView.findViewById(R.id.tv_xuqiu);
            tv_start=itemView.findViewById(R.id.tv_start);
            tv_end=itemView.findViewById(R.id.tv_end);
            layout_details=itemView.findViewById(R.id.layout_details);
        }
    }
    public interface CallPhoneListener{
        void onListener(int position);
    }
    private CallPhoneListener listener;
    public void setListener(CallPhoneListener listener){
        this.listener=listener;
    }
}
