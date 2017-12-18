package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.utils.DensityUtils;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/19.
 */
@ContentView(R.layout.activity_photo)
public class PhotoActivity extends BaseActivity {
    protected final static int PHOTO_OK=11;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.gridview)
    GridView gridview;
    private ArrayList<String> photos;
    private ProgressDialog mProgressDialog;
    private PhotoAdapter mAdapter;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PHOTO_OK:
                    //关闭进度条
                    mAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        storageCard();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        photos=new ArrayList<String>();
        mAdapter=new PhotoAdapter();
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.putExtra("PHOTO",photos.get(i));
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    //授权读写权限
    private void storageCard(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.MY_PERMISSIONS_REQUEST_READ);
        }else {
            getImages();
        }
    }
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode== Constants.MY_PERMISSIONS_REQUEST_READ){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImages();
            }else {
                PromptDialog dialog = new PromptDialog(this);
                dialog.setTitle(getString(R.string.prompt));
                dialog.setMsg(getString(R.string.plsase_shouquan_sdcard));
                dialog.setCanceledTouchOutside(false);
                dialog.setOnClick1(getString(R.string.queding), new PromptDialog.Btn1OnClick() {
                    @Override
                    public void onClick() {
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                        finish();

                    }
                });
                dialog.setOnClick2(getString(R.string.cancal), new PromptDialog.Btn2OnClick() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
                dialog.create();
                dialog.showDialog();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */

    private void getImages() {
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();
                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
//                    String parentName = new File(path).getParentFile().getName();
                    photos.add(path);
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(PHOTO_OK);
                mCursor.close();
            }
        }).start();

    }

    class PhotoAdapter extends BaseAdapter{
        public PhotoAdapter(){

        }
        @Override
        public int getCount() {
            return photos.size();
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
            ImageView imageView=new ImageView(PhotoActivity.this);
            int width= DensityUtils.deviceWidthPX(PhotoActivity.this);
            int picWidth=(width-DensityUtils.dip2px(PhotoActivity.this,4))/3;
            imageView.setLayoutParams(new ViewGroup.LayoutParams(picWidth,picWidth));
            x.image().bind(imageView,photos.get(i));
            return imageView;
        }
    }
}
