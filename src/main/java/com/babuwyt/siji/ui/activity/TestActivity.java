package com.babuwyt.siji.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.entity.PicEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.utils.TencentYunUtils;
import com.google.gson.Gson;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/1/30.
 */

public class TestActivity extends BaseActivity {
    private GridView gridview;
    private Button btn;
    private ArrayList<String> mList;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        gridview=findViewById(R.id.gridview);
        myAdapter=new MyAdapter();
        mList=new ArrayList<String>();
        myAdapter.setmList(mList);
        gridview.setAdapter(myAdapter);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, PaiZhaoActivity.class);
                startActivityForResult(intent, 3);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 4 && requestCode == 3) {
            final String path = data.getStringExtra("path");
            mList.add(path);
            //这里页面可以刷新出来
            myAdapter.notifyDataSetChanged();

            upload(path);//这个方法调用后就无法刷新出来

        }
    }
    public void upload(final String srcPath) {
        if (TextUtils.isEmpty(srcPath)) {
            return;
        }
        final String cosPath = "ceshi/wyt" + System.currentTimeMillis() / 1000 + ".jpg";
        TencentYunUtils.upload(this, srcPath, cosPath, new IUploadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long l, long l1) {
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
                Log.d("==取消==",cosPath);
            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                mList.add(BaseURL.BASE_IMAGE_URI+cosPath);
                Log.d("==前==",cosPath);
                Log.d("==成功==",new Gson().toJson(cosResult));
                myAdapter.notifyDataSetChanged();
                Log.d("==后==",cosPath);

            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                Log.d("==失败==",srcPath);
                Log.d("==失败==",new Gson().toJson(cosResult));

            }
        });
    }


    class MyAdapter extends BaseAdapter{
        private ArrayList<String> mList;
        public MyAdapter(){
            mList=new ArrayList<String>();
        }
        public void setmList(ArrayList<String>list){
            if (list!=null){
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
            Log.d("==getView==","");
            int width=DensityUtils.deviceWidthPX(TestActivity.this);



            ImageView imageView=new ImageView(TestActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width/3,width/3));
            x.image().bind(imageView,mList.get(i));

            return imageView;
        }
    }
}
