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
 * Created by lenovo on 2017/9/28.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private ArrayList<LookAddressEntity> mList;
    private Context mContext;
    private View mHeaderView;

    public OrderDetailsAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<LookAddressEntity>();
    }

    public void setmList(ArrayList<LookAddressEntity> list) {
        if (list != null) {
            mList = list;
        }
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolderHeader(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_orderdetails_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            ViewHolder viewHolder= (ViewHolder) holder;
            if (mList.get(position-1).getFromto().equalsIgnoreCase("FROM")) {
                viewHolder.tv_address_type.setText(mContext.getString(R.string.tihuodi));
            } else {
                viewHolder.tv_address_type.setText(mContext.getString(R.string.xiehuodi));
            }
            viewHolder.tv_address.setText(mList.get(position-1).getSsq());
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mHeaderView == null) {
            size = mList.size();
        }else {
            size = mList.size() + 1;
        }
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address_type, tv_address;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_address = itemView.findViewById(R.id.tv_address);
            tv_address_type = itemView.findViewById(R.id.tv_address_type);
        }
    }
    class ViewHolderHeader extends RecyclerView.ViewHolder {
        TextView tv_address_type, tv_address;

        public ViewHolderHeader(View itemView) {
            super(itemView);

            tv_address = itemView.findViewById(R.id.tv_address);
            tv_address_type = itemView.findViewById(R.id.tv_address_type);
        }
    }
}
