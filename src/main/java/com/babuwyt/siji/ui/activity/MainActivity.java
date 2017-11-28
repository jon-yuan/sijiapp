package com.babuwyt.siji.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.ClientApp;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.bean.NewOrderInfoBean;
import com.babuwyt.siji.bean.OrderNumBean;
import com.babuwyt.siji.bean.UserInfoBean;
import com.babuwyt.siji.bean.VersionBean;
import com.babuwyt.siji.entity.NewOrderInfoEntity;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.entity.VersionEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.Constants;
import com.babuwyt.siji.finals.DataSynEvent;
import com.babuwyt.siji.ui.fragment.MainLeftFragment;
import com.babuwyt.siji.utils.MapUtil;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.jpush.LocalBroadcastManager;
import com.babuwyt.siji.utils.jpush.TagAliasOperatorHelper;
import com.babuwyt.siji.utils.jpush.Util;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseProgressCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.PromptDialog;
import com.babuwyt.siji.views.SignInDialog;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.linear_layout)
    LinearLayout linear_layout;
    @ViewInject(R.id.num_msg)
    ImageView num_msg;
    @ViewInject(R.id.layout_msg)
    ConstraintLayout layout_msg;
    @ViewInject(R.id.tv_no_dataview)
    TextView tv_no_dataview;
    @ViewInject(R.id.tv_orderNum)
    TextView tv_orderNum;
    @ViewInject(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @ViewInject(R.id.layout_topBtn)
    LinearLayout layout_topBtn;
    @ViewInject(R.id.tv_state)
    TextView tv_state;
    @ViewInject(R.id.tv_othershouru)
    TextView tv_othershouru;
    @ViewInject(R.id.tv_othershourudetails)
    TextView tv_othershourudetails;
    @ViewInject(R.id.tv_otherkouchu)
    TextView tv_otherkouchu;
    @ViewInject(R.id.tv_otherkouchudetails)
    TextView tv_otherkouchudetails;
    @ViewInject(R.id.tv_Num)
    TextView tv_Num;
    @ViewInject(R.id.tv_yunfei)
    TextView tv_yunfei;
    @ViewInject(R.id.tv_xianjin)
    TextView tv_xianjin;
    @ViewInject(R.id.tv_youka)
    TextView tv_youka;
    @ViewInject(R.id.tv_zengsong)
    TextView tv_zengsong;
    @ViewInject(R.id.tv_shouru)
    TextView tv_shouru;
    @ViewInject(R.id.tv_remark)
    TextView tv_remark;
    @ViewInject(R.id.tv_start)
    TextView tv_start;
    @ViewInject(R.id.tv_end)
    TextView tv_end;
    @ViewInject(R.id.tv_qiandao)
    TextView tv_qiandao;
    @ViewInject(R.id.tv_binding)
    TextView tv_binding;
    @ViewInject(R.id.springview)
    SpringView springview;

    private MainLeftFragment fragment;
    private FragmentManager manager;
    private FragmentTransaction leftAction;
    private Intent intent;
    private NewOrderInfoEntity entity;
    private double latitude;
    private double longitude;
    private String addressno;
    private UserInfoEntity mEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        init();
        initRefresh();
        getPersonal();
        getNum();
        getNewOrder();
        getVersion();
        registerMessageReceiver();
//        getIntents();
    }

    private void getIntents(){
        String type=getIntent().getStringExtra(Constants.EXTRA_BUNDLE);
        Intent i=new Intent();
        if (type!=null){
            switch (type) {
                case "2":
                case "6":
                    i.setClass(this, MyWalletActivity.class);
                    startActivity(i);
                    break;
                default:
                    i.setClass(this, MsgActivity.class);
                    startActivity(i);
                    break;
            }


        }
    }

    private void initRefresh() {
//        jpush_appid.setText(JPushInterface.getRegistrationID(this));
//        springview.setType(SpringView.Type.FOLLOW);
        springview.setHeader(new DefaultHeader(this));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getNum();
                getNewOrder();
                getPersonal();
            }

            @Override
            public void onLoadmore() {

            }
        });
    }


    //这里判断有没有数据  有数据显示 没数据提示
    private void isHasData(boolean hasData) {
        if (hasData) {
            linear_layout.setVisibility(View.VISIBLE);
            tv_orderNum.setVisibility(View.VISIBLE);
            tv_no_dataview.setVisibility(View.GONE);
            setData();
        } else {
            linear_layout.setVisibility(View.GONE);
            tv_orderNum.setVisibility(View.INVISIBLE);
            tv_no_dataview.setVisibility(View.VISIBLE);
        }
    }

    private void setData() {
        if (entity == null) {
            return;
        }
        tv_orderNum.setText(getString(R.string.orderno) + entity.getFsendcarno());
        tv_yunfei.setText(entity.getFshouldpay() + "");//
        tv_youka.setText(entity.getFshouldpayOilcard() + "");//
        tv_othershouru.setText(entity.getFotherin() + "");//
        tv_otherkouchu.setText(entity.getFotherout() + "");//
        tv_xianjin.setText(entity.getCash() + "");//
        tv_zengsong.setText(entity.getFshouldreturnmoney() + "");//
        tv_shouru.setText(entity.getFshouldpay() + entity.getFshouldreturnmoney()
                - entity.getFotherin() - entity.getFotherout() + "");//
        tv_remark.setText(entity.getPickcount() + getString(R.string.ti) + entity.getUnloadcount() + getString(R.string.xie));//运费
        tv_start.setText(entity.getStart());//
        tv_end.setText(entity.getEnd());//
        //fvicecard
        if (TextUtils.isEmpty(entity.getFvicecard())) {
            tv_binding.setEnabled(true);
            tv_binding.setBackgroundResource(R.drawable.button_shape);
        } else {
            tv_binding.setEnabled(false);
            tv_binding.setBackgroundResource(R.drawable.button_shape_gray);
        }

    }


    private void init() {
        intent = new Intent();
        manager = getSupportFragmentManager();
        //初始化侧边栏
        fragment = new MainLeftFragment();
        leftAction = manager.beginTransaction();
        leftAction.replace(R.id.fl_content, fragment);
        leftAction.commit();
    }

    private void setState() {
        String Fdelet = "";
        switch (SessionManager.getInstance().getUser().getFisdelete()) {
            case 0:
                Fdelet = "不可用";
                break;
            case 1:
                Fdelet = "已认证";
                break;
            case 2:
                Fdelet = "未认证";
                break;
            case 3:
                Fdelet = "审核未通过";
                break;
            case 4:
                Fdelet = "待审核";
                break;
            default:
                Fdelet = "未登录";
                break;
        }
        tv_state.setText(Fdelet);
    }

    @Event(value = {R.id.layout_msg, R.id.tv_looksignno, R.id.tv_qiandao, R.id.tv_signpic, R.id.tv_zhuanghuopic, R.id.look_pic, R.id.layout_topBtn, R.id.tv_othershourudetails, R.id.tv_otherkouchudetails, R.id.layout_qiang, R.id.tv_binding, R.id.tv_lookaddress})
    private void getE(View v) {
        if (state()) {
            switch (v.getId()) {
                case R.id.layout_topBtn:
                    if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        drawer_layout.openDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.tv_othershourudetails:
                    intent.setClass(this, OtherCostActivity.class);
                    intent.putExtra("fid", entity.getFid());
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    break;
                case R.id.tv_otherkouchudetails:
                    intent.setClass(this, OtherCostActivity.class);
                    intent.putExtra("fid", entity.getFid());
                    intent.putExtra("type", 2);
                    startActivity(intent);
                    break;
                case R.id.layout_qiang:
                    intent.setClass(this, GrabOrderListActivity.class);
//                    intent.setClass(this, SignNoListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_binding:
                    intent.setClass(this, BindingYoukaActivity.class);
                    intent.putExtra("fid", entity.getFid());
                    startActivityForResult(intent, 1);
                    break;
                case R.id.tv_lookaddress:
                    intent.setClass(this, LookAddressListActivity.class);
                    intent.putExtra("fownsendcarid", entity.getOwnsendcarid());
                    startActivity(intent);
                    break;
                case R.id.look_pic:
                    intent.setClass(this, LookPicActivity.class);
                    intent.putExtra("fownsendcarid", entity.getOwnsendcarid());
                    startActivity(intent);
                    break;
                case R.id.tv_looksignno:
                    intent.setClass(this, SignNoListActivity.class);
                    intent.putExtra("fownsendcarid", entity.getOwnsendcarid());
                    startActivity(intent);
                    break;
                case R.id.layout_msg:
                    intent.setClass(this, MsgActivity.class);
                    startActivity(intent);
                    num_msg.setVisibility(View.GONE);
                    break;
                case R.id.tv_zhuanghuopic:

                    Location(2);
                    break;
                case R.id.tv_signpic:

                    Location(3);
                    break;
                case R.id.tv_qiandao:
                    Location(1);
                    break;
            }
        }
    }

    //h
    private void getNum() {
        XUtil.GetPing(BaseURL.SELECT_SEND_CARORDERNUM, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<OrderNumBean>() {
            @Override
            public void onSuccess(OrderNumBean result) {
                super.onSuccess(result);
                springview.onFinishFreshAndLoad();
                if (result.isSuccess()) {
                    tv_Num.setText(result.getObj() + "");
                    if (result.getObj() > 0) {
                        tv_Num.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                springview.onFinishFreshAndLoad();
            }
        });
    }

    private void getNewOrder() {
        dialog.showDialog();
        XUtil.Post(BaseURL.SENCARORDERINFO, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<NewOrderInfoBean>() {
            @Override
            public void onSuccess(NewOrderInfoBean result) {
                super.onSuccess(result);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
                if (result.isSuccess() && result.getObj().size() >= 1) {
                    entity = result.getObj().get(0);
                    isHasData(true);
                } else {
                    isHasData(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2 && data != null) {
            //绑定油卡成功
            String fvicecard = data.getStringExtra("fvicecard");
            entity.setFvicecard(fvicecard);
            setData();
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

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Toast.makeText(MainActivity.this, getString(R.string.onclickisexit), Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
            }
        }
        return false;
    }

    //1签到 2装货拍照3签收拍照
    private void getLocation(final int type) {
        MapUtil.getInstance(this).Location(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (type == 1) {
                        qiandao(aMapLocation.getAdCode(), aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                    if (type == 2) {
                        Task(1, aMapLocation.getAdCode(), aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                    if (type == 3) {
                        Task(2, aMapLocation.getAdCode(), aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                } else {

                    UHelper.showToast(MainActivity.this, "定位失败！请检查网络。");
                }
            }
        });
    }

    /**
     * 签到
     */
    private void qiandao(String addressno, double latitude, double longitude) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerid", entity.getOwnsendcarid());
        map.put("fid", entity.getFid());
        map.put("fdriverid", entity.getFdriverid());
        map.put("addressno", addressno);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.QIANDAO, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();

                showSignInDialog(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();

            }
        });
    }

    //签到提示

    @SuppressLint("NewApi")
    private void showSignInDialog(BaseBean result) {
        SignInDialog dialog = new SignInDialog(this);
        dialog.setMsg(result.getMsg());
        dialog.setImg(result.isSuccess() ? R.drawable.icon_sign_success : R.drawable.icon_sign_fail);
        dialog.create();
        dialog.showDialog();
    }

    /**
     * @param type 1装货拍照 2 签收拍照
     */
    private void Task(final int type, final String addressno, final double latitude, final double longitude) {
        //TASKSTATE
        ArrayList<String> list = new ArrayList<String>();
        list.add(entity.getFid() + "&" + type);
        dialog.showDialog();
        XUtil.Post(BaseURL.TASKSTATE, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    if (type == 1) {
                        intent.setClass(MainActivity.this, LoadingPicActivity.class);
                        intent.putExtra("ownsendcarid", entity.getOwnsendcarid());
                        intent.putExtra("addressno", addressno);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }
                    if (type == 2) {
                        intent.setClass(MainActivity.this, SignPicActivity.class);
                        intent.putExtra("ownsendcarid", entity.getOwnsendcarid());
                        intent.putExtra("addressno", addressno);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }

                } else {
                    UHelper.showToast(MainActivity.this, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取个人信息
     */
    private void getPersonal() {
        XUtil.GetPing(BaseURL.GETPERSONAL_INFO, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                if (result.isSuccess()) {
                    mEntity = result.getObj();
                    UserInfoEntity user = SessionManager.getInstance().getUser();
                    UserInfoEntity entity = result.getObj();
                    if (entity != null) {
                        entity.setWebtoken(user.getWebtoken());
                        entity.setFdriverid(user.getFdriverid());
                        entity.setFdrivername(user.getFdrivername());
                        entity.setFphone(user.getFphone());
                        entity.setCarid(user.getCarid());
                        entity.setFplateno(user.getFplateno());
                        ((ClientApp) getApplication()).saveLoginUser(entity);
                        setState();
                        sendEven();
                        state();
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }


    /**
     * 发送消息给侧滑页面，设置数据
     */
    private void sendEven() {
        DataSynEvent event = new DataSynEvent();
        event.setType(event.DATA_SYNEVENT_CODE1);
        EventBus.getDefault().post(event);
    }

    //版本检测
    private void getVersion() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(1 + "");
        XUtil.GetPing(BaseURL.CHECKVERSION, list, new ResponseCallBack<VersionBean>() {
            @Override
            public void onSuccess(VersionBean o) {
                if (o.isSuccess()) {
                    setVersion(o.getObj());
                    SessionManager.getInstance().setServicephone(o.getObj().getFservicephone());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }

    private void setVersion(final VersionEntity entity) {
        String vsersionCode = UHelper.getAppVersionInfo(this, UHelper.TYPE_VERSION_CODE);
        if (entity.getFversion() > Integer.parseInt(vsersionCode)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("发现新版本");
            builder.setMessage(entity.getFupdateinfo());
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    DownLoadFile(entity.getFurl());
                }
            });
            if (entity.getFisforceupdate()) {
                builder.setCancelable(false);
            } else {
                builder.setCancelable(true);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
            builder.create().show();
        }
    }

    /**
     * 下载现版本APP
     */
    private File filepath;

    private void DownLoadFile(String url) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取SD卡的目录
            String path = Environment.getExternalStorageDirectory().getPath();
            filepath = new File(path + File.separator + "apk" + File.separator + "siji.apk");//仅创建路径的File对象
            if (!filepath.exists()) {
                filepath.mkdir();//如果路径不存在就先创建路径
            }
        }
        // 准备进度条Progress弹窗
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        dialog.setTitle("下载中...");
        //Progress弹窗设置为水平进度条
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条

        //"http://acj3.pc6.com/pc6_soure/2017-8/me.ele_190.apk"
        XUtil.DownLoadFile(url, filepath.getPath().toString(), new ResponseProgressCallBack<File>() {
            @Override
            public void Started() {
                dialog.show();
            }

            @Override
            public void Success(File o) {
                dialog.dismiss();
                installAPK();
            }

            @Override
            public void Loading(long total, long current, boolean isDownloading) {
                dialog.setMax((int) total);
                dialog.setProgress((int) current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dismiss();
            }
        });

    }

    private void installAPK() {
        //系统应用界面，安装apk入口，看源码
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setData(Uri.fromFile(file));
//        intent.setType("application/vnd.android.package-archive");

        //切记当要同时配Data和Type时一定要用这个方法，否则会出错
        intent.setDataAndType(Uri.fromFile(filepath), "application/vnd.android.package-archive");

        startActivityForResult(intent, 0);
    }

    //定位权限
    private void Location(int type) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_CALL_LOCATION);
        } else {
            getLocation(type);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.MY_PERMISSIONS_REQUEST_CALL_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UHelper.showToast(this, "已授权定位服务。");
            } else {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage("您没有授权定位权限，程序将无法使用！\n 该权限是为了获取当前位置信息。");
                builder.setTitle("授权失败");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 判断是否已经认证通过
     *
     * @return
     */
    private boolean state() {

        if (SessionManager.getInstance().getUser().getFisdelete() == 2 || SessionManager.getInstance().getUser().getFisdelete() == 3) {
            showDialog(2, getString(SessionManager.getInstance().getUser().getFisdelete() == 3 ? R.string.auth_fail : R.string.no_auth));
            return false;
        }
        if (SessionManager.getInstance().getUser().getFisdelete() == 4) {
            showDialog(4, getString(R.string.auth_ing));
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void showDialog(final int authCode, String msg) {
        PromptDialog dialog = new PromptDialog(this);
        dialog.setTitle(getString(R.string.prompt));
        dialog.setMsg(msg);
        dialog.setCanceledTouchOutside(true);
        dialog.setOnClick1(getString(authCode == 2 ? R.string.go_auth : R.string.ok), new PromptDialog.Btn1OnClick() {
            @Override
            public void onClick() {
                if (authCode == 2) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PersonalInfoAuthActivity.class);
                    startActivity(intent);
                }
            }
        });
        dialog.setOnClick2(authCode == 2 ? getString(R.string.cancal) : null, new PromptDialog.Btn2OnClick() {
            @Override
            public void onClick() {

            }
        });
        dialog.create();
        dialog.showDialog();
    }

    /**
     * 极光推送
     */
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String ACTION_NOTIFICATION_RECEIVED = "com.example.jpushdemo.ACTION_NOTIFICATION_RECEIVED";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        filter.addAction(ACTION_NOTIFICATION_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    UHelper.showToast(MainActivity.this, messge + "");
                    if (!Util.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
                if (ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
                    num_msg.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
