package com.babuwyt.siji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.babuwyt.siji.R;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/1/31.
 */

public class MainAdapter2 extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mList;
    public MainAdapter2(Context context){
        mContext=context;
    }
    public void setmList(ArrayList<String> list){
        if (list!=null && list.size()>0){
            mList=list;
        }
    }
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.adapter_main2,null);
            x.view().inject(holder,view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        return view;
    }

    class ViewHolder{

    }
}
