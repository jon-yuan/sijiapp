package com.babuwyt.siji.ui.activity.activity2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.MainAdapter2;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.ui.activity.MainActivity;
import com.babuwyt.siji.ui.fragment.MainLeftFragment;
import com.babuwyt.siji.ui.fragment.fragment2.MainLeftFragment2;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/1/29.
 */
@ContentView(R.layout.activity_main2)
public class MainActivity2 extends BaseActivity {
    @ViewInject(R.id.drawer_layout2)
    DrawerLayout mDrawerlayout;
    @ViewInject(R.id.leftTopBtn)
    LinearLayout leftTopBtn;
    @ViewInject(R.id.fl_content2)
    FrameLayout fl_content2;
    @ViewInject(R.id.springview)
    SpringView springview;
    @ViewInject(R.id.listview)
    ListView listview;
    private MainAdapter2 mainAdapter2;
    private ArrayList<String> mList;
    private MainLeftFragment2 mainLeftFragment2;
    private FragmentManager manager;
    private FragmentTransaction leftAction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //初始化侧边栏
        manager = getSupportFragmentManager();
        mainLeftFragment2 = new MainLeftFragment2();
        leftAction = manager.beginTransaction();
        leftAction.replace(R.id.fl_content2, mainLeftFragment2);
        leftAction.commit();

        //侧滑动画效果
        mDrawerlayout.setScrimColor(Color.TRANSPARENT);
        mDrawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = mDrawerlayout.getChildAt(0);
                View content1 = mDrawerlayout.getChildAt(1);
                View menu = drawerView;
                float scale = 1 - slideOffset;//1~0
                float rightScale = 0.8f + scale * 0.2f; //把1.0~0.0转化为1.0~0.8
                content.setTranslationX(menu.getMeasuredWidth() * (1 - scale));//0~width
                content.setScaleY(rightScale);
                content.setScaleX(rightScale);
                float t=1-scale;
                content1.setAlpha(t);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        springview.setHeader(new DefaultHeader(this));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {

            }
        });
        /**
         * listview
         */
        mainAdapter2=new MainAdapter2(this);
        mList=new ArrayList<String>();
        for (int i=0;i<10;i++){
            mList.add(i+"");
        }
        mainAdapter2.setmList(mList);
        listview.setAdapter(mainAdapter2);



        listview.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if(event.getAction() == MotionEvent.ACTION_UP){
                    springview.requestDisallowInterceptTouchEvent(false);
                }else{
                    springview.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }


    /**
     * 让Listview 滑动到某个位置
     * @param position
     */

    private void setSelection(final int position){
        listview.post(new Runnable() {
            @Override
            public void run() {
                listview.smoothScrollToPosition(position);
            }
        });
    }
    private int position=0;
    @Event(value = {R.id.leftTopBtn,R.id.btn_qiang})
    private void getE(View v){
        switch (v.getId()){
            case R.id.leftTopBtn:
                if (!mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerlayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.btn_qiang:
                position++;
                setSelection(position);
                break;
        }
    }



    /**
     * 点击两次退出应用
     * 通过记录按键时间计算时间差实现
     */
    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerlayout.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Toast.makeText(MainActivity2.this, getString(R.string.onclickisexit), Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
            }
        }
        return false;
    }
}
