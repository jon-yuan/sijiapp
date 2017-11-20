package com.babuwyt.siji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.entity.BankEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/10/20.
 */

public class SubbranchAdapter extends BaseAdapter {
    private List<BankEntity> mList;
    private LayoutInflater mLayoutInflater;

    public SubbranchAdapter(Context context) {
        mList = new ArrayList<BankEntity>();
        mLayoutInflater = LayoutInflater.from(context);
    }
    public void setmList(List<BankEntity> arrayList){
        if (arrayList!=null){
            mList=arrayList;
        }
    }

    @Override
    public int getCount() {
        return mList.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.listviewitem, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText((mList.get(position).getBankname()));

        return convertView;
    }
    private class ViewHolder {
        TextView textView;
    }
}
