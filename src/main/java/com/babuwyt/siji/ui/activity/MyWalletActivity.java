package com.babuwyt.siji.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.adapter.MyWalletAdapter;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BankCallBackBean;
import com.babuwyt.siji.bean.BankInfoBean;
import com.babuwyt.siji.bean.BaseBean;
import com.babuwyt.siji.bean.WalletBean;
import com.babuwyt.siji.entity.BankInfoEntity;
import com.babuwyt.siji.entity.TranListEntity;
import com.babuwyt.siji.entity.TransactionEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.babuwyt.siji.views.GetPriceDialog;
import com.babuwyt.siji.views.RechargeDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/22.
 */
@ContentView(R.layout.activity_mywallet)
public class MyWalletActivity extends BaseActivity {
    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerview;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_totalMoney)
    TextView tv_totalMoney;
    @ViewInject(R.id.tv_ketixian)
    TextView tv_ketixian;
    @ViewInject(R.id.tv_keyong)
    TextView tv_keyong;
    @ViewInject(R.id.tv_tixian)
    TextView tv_tixian;
    @ViewInject(R.id.tv_chongzhi)
    TextView tv_chongzhi;
    @ViewInject(R.id.springview)
    SpringView springview;
    @ViewInject(R.id.tv_type)
    TextView tv_type;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    private RecyclerView.LayoutManager manager;
    private ArrayList<TranListEntity> mList;
    private MyWalletAdapter mAdapter;
    private RechargeDialog chongzhidialog;
    private GetPriceDialog getPriceDialog;
    private String SerialNo;
    private int dealTypes = 0;
    private int dealTime = 0;
    private int currPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(false);
        init();
        getData();
    }

    private void init() {
        chongzhidialog = new RechargeDialog(this);
        getPriceDialog = new GetPriceDialog(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mList = new ArrayList<TranListEntity>();
        mAdapter = new MyWalletAdapter(this, mList);
        recyclerview.setAdapter(mAdapter);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setHeader(new DefaultHeader(this));
        springview.setFooter(new DefaultFooter(this));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadmore() {
                getDataMore();
            }
        });

    }
    @Event(value = {R.id.tv_tixian, R.id.tv_chongzhi, R.id.tv_type, R.id.tv_time})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.tv_tixian:
                getBankInfo(2);
                break;
            case R.id.tv_chongzhi:
                getBankInfo(1);
                break;
            case R.id.tv_time:
                showTime();
                break;
            case R.id.tv_type:
                showType();
                break;
        }
    }

    /**
     * 获取绑定银行卡信息
     */
    private void getBankInfo(final int type) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(SessionManager.getInstance().getUser().getFdriverid());
        XUtil.Post(BaseURL.BANKINFO, list, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankInfoBean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(BankInfoBean result) {
                super.onSuccess(result);
                if (result.isSuccess() && result.getObj() != null) {
                    Chongzhi(result.getObj(), type);
                } else {
                    startActivity(new Intent(MyWalletActivity.this, BindingBankCarkActivity.class));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Chongzhi(final BankInfoEntity entity, final int type) {
        int size = entity.getFbankCard().length();
        String str="";
        if (size>3){
            str= entity.getFbankCard().substring(size - 4, size);
        }else {
            str=entity.getFbankCard();
        }

        chongzhidialog.setCanceledTouchOutside(true);
        chongzhidialog.setBancname(entity.getZbankSname() + "(" + str + ")");
        chongzhidialog.setTitle(type == 1 ? getString(R.string.chongzhi) : getString(R.string.tixian));
        chongzhidialog.setMsg(type == 1 ? getString(R.string.chongzhijine) : getString(R.string.tixianjine));
        final String finalStr = str;
        chongzhidialog.setCommitCallBack(type == 1 ? getString(R.string.chongzhi) : getString(R.string.xiayibu), new RechargeDialog.CommitCallBack() {
            @Override
            public void onCommit(double price) {
                if (type == 1) {
                    Paymentdues(price, entity.getFbankCard());
                } else {
                    //提现验证码发送
                    costMsg(price, finalStr, entity.getZbankSname());
                }
            }
        });
        chongzhidialog.create();
        chongzhidialog.showDialog();
    }

    //充值
    private void Paymentdues(double price, String acctId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
//        map.put("memId",123);
        map.put("acctId", acctId);
//        map.put("acctId",10101);
        map.put("tranAmount", price);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.PAYMENTDUES, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    if (chongzhidialog.isShowing()) {
                        chongzhidialog.dissDialog();
                    }
                    getData();
                    UHelper.showToast(MyWalletActivity.this, getString(R.string.chongzhi_success));
                } else {
                    UHelper.showToast(MyWalletActivity.this, getString(R.string.chongzhi_fail));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    private void costMoney(double price, String code, String bankname) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
        map.put("tranAmount", price);
        map.put("messageCode", code);
        map.put("serialNo", SerialNo);
        map.put("zBankSname", bankname);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.CASHMONEY, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankCallBackBean>() {
            @Override
            public void onSuccess(BankCallBackBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    UHelper.showToast(MyWalletActivity.this, getString(R.string.tixian_cuccess));
                    getData();
                } else {
                    UHelper.showToast(MyWalletActivity.this, result.getMsg());
                }
                chongzhidialog.dissDialog();
                getPriceDialog.dissDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void tixian(final double price, final String banknum, final String bankname) {
        getPriceDialog.setMsg("已向尾号(" + banknum + ")的银行卡预留手机号发送验证码，请在下方输入验证码");
        getPriceDialog.setCanceledTouchOutside(true);
        getPriceDialog.setAuthCallBack(new GetPriceDialog.AuthCallBack() {
            @Override
            public void onAuth() {
                costMsg(price, banknum, bankname);
            }
        });
        getPriceDialog.setCommitCallBack(new GetPriceDialog.CommitCallBack() {
            @Override
            public void onCommit(String auth) {
                costMoney(price, auth, bankname);
            }
        });
        getPriceDialog.create();
        getPriceDialog.showDialog();
    }

    //MSG_DYNAMICCODE
    private void costMsg(final double price, final String backnum, final String backName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
        map.put("tranType", 1);
        map.put("tranAmount", price);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.MSG_DYNAMICCODE, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BankCallBackBean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(BankCallBackBean result) {
                super.onSuccess(result);
                dialog.dissDialog();

                if (result.isSuccess()) {
                    getPriceDialog.setDownTime();
                    SerialNo = result.getObj().getSerialNo();
                    tixian(price, backnum, backName);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }

    /**
     * 打开选择类型
     */
    private void showType() {

        Resources res = getResources();
        String[] transaction = res.getStringArray(R.array.transaction_type);
        final ArrayList<TransactionEntity> list = new ArrayList<TransactionEntity>();
        for (int i = 0; i < transaction.length; i++) {
            TransactionEntity entity = new TransactionEntity();
            entity.setType(transaction[i]);
            entity.setTypeId(i);
            list.add(entity);
        }
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_type.setText(list.get(options1).getPickerViewText());
                dealTypes = list.get(options1).getTypeId();
                getData();
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    private void showTime() {

        Resources res = getResources();
        String[] transaction = res.getStringArray(R.array.transaction_time);
        final ArrayList<TransactionEntity> list = new ArrayList<TransactionEntity>();
        for (int i = 0; i < transaction.length; i++) {
            TransactionEntity entity = new TransactionEntity();
            entity.setType(transaction[i]);
            entity.setTypeId(i);
            list.add(entity);
        }
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_time.setText(list.get(options1).getPickerViewText());
                dealTime = list.get(options1).getTypeId();
                getData();
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    private void getData() {
        currPage = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dealTypes", dealTypes);
        map.put("dealTime", dealTime);
        map.put("currPage", currPage);

        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.TRANSACTION_SELECT, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<WalletBean>() {
            @Override
            public void onSuccess(WalletBean result) {
                super.onSuccess(result);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
                if (result.isSuccess()) {
                    tv_totalMoney.setText(result.getObj().getTotalMoney1() + "");
                    tv_keyong.setText(result.getObj().getBalanceCashMoney() == null ? "0" : result.getObj().getBalanceCashMoney().getUse());
                    tv_ketixian.setText(result.getObj().getBalanceCashMoney() == null ? "0" : result.getObj().getBalanceCashMoney().getT());
                    mList.clear();
                    mList.addAll(result.getObj().getTranList() == null ? new ArrayList<TranListEntity>() : result.getObj().getTranList());
                    mAdapter.notifyDataSetChanged();
                }
                UHelper.showToast(MyWalletActivity.this, result.getMsg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
            }
        });

    }

    private void getDataMore() {
        currPage++;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dealTypes", dealTypes);
        map.put("dealTime", dealTime);
        map.put("currPage", currPage);
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.TRANSACTION_SELECT, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<WalletBean>() {
            @Override
            public void onSuccess(WalletBean result) {
                super.onSuccess(result);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
                if (result.isSuccess()) {
                    tv_totalMoney.setText(result.getObj().getTotalMoney() + "");
                    tv_keyong.setText(result.getObj().getBalanceCashMoney() == null ? "0" : result.getObj().getBalanceCashMoney().getFtotalBalance() + "");
                    tv_ketixian.setText(result.getObj().getBalanceCashMoney() == null ? "0" : result.getObj().getBalanceCashMoney().getFtotalTranOutAmount() + "");
                    mList.addAll(result.getObj().getTranList() == null ? new ArrayList<TranListEntity>() : result.getObj().getTranList());
                    mAdapter.notifyDataSetChanged();
                }
                UHelper.showToast(MyWalletActivity.this, result.getMsg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                springview.onFinishFreshAndLoad();
                dialog.dissDialog();
            }
        });

    }
}
