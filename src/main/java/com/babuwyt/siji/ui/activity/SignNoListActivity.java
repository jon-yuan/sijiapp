package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.SignNoAdapter;
import com.babuwyt.siji.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/11/13.
 */
@ContentView(R.layout.activity_signnolist)
public class SignNoListActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.listview)
    ListView listview;
    private String fownsendcarid;
    private SignNoAdapter mAdapter;
    private ArrayList<String> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        fownsendcarid=getIntent().getStringExtra("fownsendcarid");
        mAdapter=new SignNoAdapter(this);
        mList=new ArrayList<String>();
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mAdapter.setmList(mList);
        listview.setAdapter(mAdapter);
    }
}
