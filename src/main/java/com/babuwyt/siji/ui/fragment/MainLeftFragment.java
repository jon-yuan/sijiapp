package com.babuwyt.siji.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseFragment;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BankInfoBean;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.finals.DataSynEvent;
import com.babuwyt.siji.ui.activity.BindingBankCarkActivity;
import com.babuwyt.siji.ui.activity.CashpledgeActivity;
import com.babuwyt.siji.ui.activity.HistoryOrderActivity;
import com.babuwyt.siji.ui.activity.MyWalletActivity;
import com.babuwyt.siji.ui.activity.PersonalInfoActivity;
import com.babuwyt.siji.ui.activity.PersonalInfoAuthActivity;
import com.babuwyt.siji.ui.activity.SettingActivity;
import com.babuwyt.siji.ui.activity.XieYiActivity;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.LoadingDialog;
import com.babuwyt.siji.views.PromptDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by lenovo on 2017/9/21.
 */
@ContentView(R.layout.fragment_left)
public class MainLeftFragment extends BaseFragment {
    @ViewInject(R.id.layout_personalinfo)
    LinearLayout layout_personalinfo;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_finishorder)
    TextView tv_finishorder;
    @ViewInject(R.id.tv_income)
    TextView tv_income;
    @ViewInject(R.id.tv_baozhengjin)
    TextView tv_baozhengjin;
    @ViewInject(R.id.btn_tixian)
    TextView btn_tixian;
    @ViewInject(R.id.btn_chongzhi)
    TextView btn_chongzhi;
    @ViewInject(R.id.layout_qianbao)
    LinearLayout layout_qianbao;
    @ViewInject(R.id.layout_setting)
    LinearLayout layout_setting;
    @ViewInject(R.id.line)
    View line;
    private LoadingDialog dialog;
    private Intent intent;
    private String fispay = "2";
    private UserInfoEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(DataSynEvent event) {
        if (event.getType() == event.DATA_SYNEVENT_CODE1) {
            setData();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        intent = new Intent();
        dialog = new LoadingDialog(getActivity());
    }

    @Event(value = {R.id.layout_setting, R.id.btn_chongzhi, R.id.btn_tixian, R.id.layout_personalinfo, R.id.layout_xieyi, R.id.layout_order, R.id.layout_qianbao})
    private void getE(View v) {

        switch (v.getId()) {
            case R.id.layout_personalinfo:
                if (state()) {
                    intent.setClass(getActivity(), PersonalInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_xieyi:
                intent.setClass(getActivity(), XieYiActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_order:
                if (state()) {
                    intent.setClass(getActivity(), HistoryOrderActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_qianbao:
                if (state()) {
                    if (fispay.equalsIgnoreCase("2")) {
                        intent.setClass(getActivity(), MyWalletActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.btn_chongzhi:
                if (state()) {
                    getBankInfo(1);
                }

                break;
            case R.id.btn_tixian:
                if (state()) {
                    getBankInfo(2);
                }
//                startActivity(new Intent(getActivity(), BindingBankCarkActivity.class));
                break;
            case R.id.layout_setting:
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setData() {
        entity = SessionManager.getInstance().getUser();
        tv_name.setText(entity.getFdrivername());
        tv_finishorder.setText(entity.getFfinishcount());
        tv_income.setText(entity.getIncome() > 0 ? entity.getIncome() + "" : "0");
        tv_baozhengjin.setText(entity.getFcautionmoney() + "");
        if (entity.getFcautionmoney() == 0) {
            btn_chongzhi.setEnabled(true);
            btn_tixian.setEnabled(false);
            btn_chongzhi.setBackgroundResource(R.drawable.button_shape);
            btn_tixian.setBackgroundResource(R.drawable.button_shape_gray);
        } else {
            btn_chongzhi.setEnabled(false);
            btn_tixian.setEnabled(true);
            btn_chongzhi.setBackgroundResource(R.drawable.button_shape_gray);
            btn_tixian.setBackgroundResource(R.drawable.button_shape);
        }
        if (entity.getFispay() == 2) {
            layout_qianbao.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        } else {
            btn_chongzhi.setVisibility(View.GONE);
            btn_tixian.setVisibility(View.GONE);
            layout_qianbao.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
    }

    /**
     * 判断是否已经认证通过
     *
     * @return
     */
    private boolean state() {

        if (SessionManager.getInstance().getUser().getFisdelete() == 2 || SessionManager.getInstance().getUser().getFisdelete() == 3) {
            showAuthDialog(2, getString(SessionManager.getInstance().getUser().getFisdelete() == 3 ? R.string.auth_fail : R.string.no_auth));
            return false;
        }
        if (SessionManager.getInstance().getUser().getFisdelete() == 4) {
            showAuthDialog(4, getString(R.string.auth_ing));
            return false;
        }
        return true;
    }

    /**
     * 提示是否认证
     *
     * @param authCode
     * @param msg
     */
    @SuppressLint("NewApi")
    private void showAuthDialog(final int authCode, String msg) {
        PromptDialog dialog = new PromptDialog(getActivity());
        dialog.setTitle(getString(R.string.prompt));
        dialog.setMsg(msg);
        dialog.setCanceledTouchOutside(true);
        dialog.setOnClick1(getString(authCode == 2 ? R.string.go_auth : R.string.ok), new PromptDialog.Btn1OnClick() {
            @Override
            public void onClick() {
                if (authCode == 2) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PersonalInfoAuthActivity.class);
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
     * 提示没有是否绑定银行卡
     *
     * @param
     * @param msg
     */
    @SuppressLint("NewApi")
    private void showBindCardDialog(String msg) {
        PromptDialog dialog = new PromptDialog(getActivity());
        dialog.setTitle(getString(R.string.prompt));
        dialog.setMsg(msg);
        dialog.setCanceledTouchOutside(true);
        dialog.setOnClick1(getString(R.string.go_bind), new PromptDialog.Btn1OnClick() {
            @Override
            public void onClick() {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BindingBankCarkActivity.class);
                startActivity(intent);
            }
        });
        dialog.setOnClick2(getString(R.string.cancal), new PromptDialog.Btn2OnClick() {
            @Override
            public void onClick() {

            }
        });
        dialog.create();
        dialog.showDialog();
    }

    /**
     * 获取绑定银行卡信息
     */
    private void getBankInfo(final int type) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(SessionManager.getInstance().getUser().getFdriverid());
        dialog.showDialog();
        XUtil.Post(BaseURL.BANKINFO, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankInfoBean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(BankInfoBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    if (result.getObj() == null) {
                        showBindCardDialog(getString(R.string.no_bind_bankcard));
                        return;
                    } else {
                        intent.setClass(getActivity(), CashpledgeActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("obj", result.getObj());
                        startActivity(intent);
                    }

                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
}
