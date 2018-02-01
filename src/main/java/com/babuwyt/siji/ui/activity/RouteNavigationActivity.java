package com.babuwyt.siji.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.AimLessMode;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.DirectionView;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.TrafficButtonView;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.utils.MapUtil;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.maputils.ErrorInfo;
import com.babuwyt.siji.utils.maputils.TTSController;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/1/18.
 */
@ContentView(R.layout.activity_routenavigation)
public class RouteNavigationActivity extends BaseActivity implements AMapNaviListener, AMapNaviViewListener {
    @ViewInject(R.id.navi_view)
    AMapNaviView mAMapNaviView;
    @ViewInject(R.id.navi_info)
    TextView navi_info;
    @ViewInject(R.id.tv_distance)
    TextView tv_distance;
    @ViewInject(R.id.tv_retaindistance)
    TextView tv_retaindistance;
    @ViewInject(R.id.img_location)
    ImageView img_location;
    @ViewInject(R.id.directionView)
    DirectionView directionView;
    @ViewInject(R.id.trafficbtn)
    TrafficButtonView trafficbtn;
    protected AMapNavi mAMapNavi;
    protected TTSController mTtsManager;
//    protected NaviLatLng mEndLatlng=new NaviLatLng(34.2595310000,108.9470060000);
    protected NaviLatLng mEndLatlng=new NaviLatLng(34.3778800000,109.0725300000);
    protected NaviLatLng mStartLatlng;
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList;
    private int[] customIconTypes = {R.drawable.sou2, R.drawable.sou3,
            R.drawable.sou4, R.drawable.sou5, R.drawable.sou6, R.drawable.sou7,
            R.drawable.sou8, R.drawable.sou9, R.drawable.sou10,
            R.drawable.sou11, R.drawable.sou12, R.drawable.sou13,
            R.drawable.sou14, R.drawable.sou15, R.drawable.sou16,
            R.drawable.sou17, R.drawable.sou18, R.drawable.sou19,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        NextTurnTipView myNextTurnView = (NextTurnTipView) findViewById(R.id.myDirectionView);
        //设置自定义UI样式，这个是修改样式后的图片ID数组
        myNextTurnView.setCustomIconTypes(getResources(),customIconTypes);
        //设置自定义的NextTurnTipView到AMapNaviView中，使其生效
        mAMapNaviView.setLazyNextTurnTipView(myNextTurnView);
        mAMapNaviView.onCreate(savedInstanceState);
        init();
    }
    private void getLocaation() {
        MapUtil.getInstance(this).Location(new AMapLocationListener() {
            @SuppressLint("NewApi")
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    mStartLatlng=new NaviLatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                    NaviLatLng  mStartLatlng1=new NaviLatLng(34.2696390000,108.9724550000);
                    sList.add(mStartLatlng);
                    eList.add(mEndLatlng);
//                    mWayPointList.add(mStartLatlng1);
                    int strategy = 0;
                    try {
                        //再次强调，最后一个参数为true时代表多路径，否则代表单路径
                        strategy = mAMapNavi.strategyConvert(true, false, false, false, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
                }
            }
        }, true);
    }

    private void init(){
        mAMapNaviView.setAMapNaviViewListener(this);
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();

        //
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());

        mAMapNavi.setUseInnerVoice(true);
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        //设置模拟导航的行车速度
//        mAMapNavi.setEmulatorNaviSpeed(75);

        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
////        options.setCarBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_blue_car));
////        options.setFourCornersBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.lane00));
//        options.setStartPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_start));
////        options.setWayPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.lane00));
//        options.setEndPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_end));
        mAMapNaviView.setViewOptions(options);
//        Resources res=getResources();
//        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.icon_zhinanzhen);
//        directionView.setDirectionBitmap(bmp);
        mAMapNaviView.setLazyDirectionView(directionView);//设置指南针
        mAMapNaviView.setLazyTrafficButtonView(trafficbtn);//设置路况按钮

        mWayPointList = new ArrayList();
//        mAMapNavi.startAimlessMode(AimLessMode.CAMERA_AND_SPECIALROAD_DETECTED);
    }

    @Event(value = {R.id.img_location,R.id.img_stopnavi,R.id.img_allmap,R.id.tv_startNavi,R.id.img_callback})
    private void getE(View v){
        switch (v.getId()){
            case R.id.img_location:
                mAMapNaviView.recoverLockMode();
                break;
            case R.id.img_allmap:
                mAMapNaviView.displayOverview();
                break;
            case R.id.tv_startNavi:
//                getLocaation();
                break;
            case R.id.img_callback:
            case R.id.img_stopnavi:
                PromptDialog dialog = new PromptDialog(this);
                dialog.setTitle(getString(R.string.prompt));
                dialog.setMsg("退出导航！");
                dialog.setCanceledTouchOutside(true);
                dialog.setOnClick1(getString(R.string.queding), new PromptDialog.Btn1OnClick() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
                dialog.setOnClick2(getString(R.string.cancal), new PromptDialog.Btn2OnClick() {
                    @Override
                    public void onClick() {

                    }
                });
                dialog.showDialog();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        getLocaation();

        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */

    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调

        UHelper.showToast(this,"开始导航！");
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //地图的模式，锁屏或锁车
        Log.d("==onNaviMapMode==",isLock+"");
//        navi_goon.setVisibility(View.VISIBLE);

    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
//        naviinfo.get
        navi_info.setText(naviinfo.getCurrentRoadName());
        tv_distance.setText(naviinfo.getCurStepRetainDistance()+"");
        BigDecimal b1 = new BigDecimal(naviinfo.getPathRetainDistance());
        BigDecimal b2 = new BigDecimal(1000);
        String s=b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).toString();
        tv_retaindistance.setText(s+"公里  "+getTime(naviinfo.getPathRetainTime()));
    }
    public String getTime(int time){
        int h=time/3600;
        int m=time%3600/60;
        return h+"小时"+m+"分";
    }
    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路径算路成功回调
//        mAMapNavi.startNavi(NaviType.EMULATOR);
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            Log.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
        img_location.setVisibility(isLock==true?View.GONE:View.VISIBLE);
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }


    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }
}
