package com.babuwyt.siji.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.BindBankCarkBean;
import com.babuwyt.siji.entity.BankEntity;
import com.babuwyt.siji.entity.bank.AreaEntity;
import com.babuwyt.siji.entity.bank.CityEntity;
import com.babuwyt.siji.entity.bank.ProvinceEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.GetJsonDataUtil;
import com.babuwyt.siji.utils.UHelper;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.XUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2017/9/22.
 */
@ContentView(R.layout.activity_bingingbankcar)
public class BindingBankCarkActivity extends BaseActivity {
    protected static final int REQUEST_CODE = 0x1111;
    public static final int RESULT_CODE = 0x1112;
    public static final int RESULT_CODE_SUBB = 0x1113;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.et_banknum)
    EditText et_banknum;
    @ViewInject(R.id.tv_bankname)
    TextView tv_bankname;
    @ViewInject(R.id.et_bankphone)
    EditText et_bankphone;
    @ViewInject(R.id.et_authCode)
    EditText et_authCode;
//    @ViewInject(R.id.tv_province)
//    TextView tv_province;
//    @ViewInject(R.id.tv_city)
//    TextView tv_city;
//    @ViewInject(R.id.layout_address)
//    LinearLayout layout_address;
//    @ViewInject(R.id.tv_bankname_1)
//    TextView tv_bankname_1;


    @ViewInject(R.id.tv_authCode)
    TextView tv_authCode;
    private String bankclscode;
    private String citycode;
    private String areacitycode;

    private String bankCode;
    private String bankName;
    private ArrayList<BankEntity> mList;
    private int time = 60;
    private Timer timer;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                tv_authCode.setText("(" + time + "s)" + getString(R.string.after_get));
                tv_authCode.setTextColor(R.color.black_98);
                tv_authCode.setEnabled(false);
            } else {
                timer.cancel();
                timer = null;
                time = 60;
                tv_authCode.setText(getString(R.string.getAuthCode));
                tv_authCode.setTextColor(R.color.black_333);
                tv_authCode.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        initJsonData();
    }

    private void init() {
        mList = new ArrayList<BankEntity>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Event(value = {R.id.tv_bankname, R.id.tv_authCode, R.id.tv_commit})
    private void getE(View v) {
        switch (v.getId()) {
            case R.id.tv_bankname:
                selectBank();
                break;
            case R.id.tv_authCode:
                isEmpty(false);
                break;
            case R.id.tv_commit:
                isEmpty(true);
                break;
//            case R.id.layout_address:
//                showAddress();
//                break;
//            case R.id.tv_bankname_1:
//                if (TextUtils.isEmpty(bankclscode)) {
//                    UHelper.showToast(this, getString(R.string.please_select_bank));
//                    break;
//                }
//                if (citycode == null || areacitycode == null) {
//                    UHelper.showToast(this, getString(R.string.please_select_p_c_a));
//                    break;
//                }
//                Intent intent = new Intent(this, SubbranchListActivity.class);
//                intent.putExtra("bankclscode", bankclscode);
//                intent.putExtra("citycode", citycode);
//                intent.putExtra("areacitycode", areacitycode);
//                startActivityForResult(intent, REQUEST_CODE);
//                break;
        }
    }

    private void isEmpty(boolean b) {
        if (TextUtils.isEmpty(et_banknum.getText().toString().trim())) {
            UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.please_input_bankcar));
            return;
        }
//        if (!UHelper.isBankNumbuter(et_banknum.getText().toString())) {
//            UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.bankcar_is_format_fail));
//            return;
//        }
        if (TextUtils.isEmpty(bankclscode)) {
            UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.please_select_bank));
            return;
        }
//        if (TextUtils.isEmpty(bankCode)) {
//            UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.please_select_subb_bank));
//            return;
//        }
        if (TextUtils.isEmpty(et_bankphone.getText().toString().trim()) || !UHelper.isPhone(et_bankphone.getText().toString().trim())) {
            UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.phone_numbuter_is_fail));
            return;
        }

        if (b) {
            if (TextUtils.isEmpty(et_authCode.getText().toString().trim())) {
                UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.please_input_auth_code));
                return;
            }
            getBind();
        } else {
            getCode();
        }
    }


    /**
     * 获取银行验证码
     */

    private void getCode() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
        map.put("acctId", et_banknum.getText().toString().trim());
//        map.put("bankCode", bankCode);
//        map.put("bankName", bankName);
        map.put("bankId", bankclscode);
        map.put("mobilePhone", et_bankphone.getText().toString().trim());
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.BINDINGBANKCAR, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BindBankCarkBean>() {
            @Override
            public void onSuccess(BindBankCarkBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    timeDown();
                    UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.has_send_to_this_phone));
                } else {
                    UHelper.showToast(BindingBankCarkActivity.this, TextUtils.isEmpty(result.getObj().getRspMsg()) ? result.getMsg() : result.getObj().getRspMsg());
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
     * 绑定银行卡
     */
    private void getBind() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memId", SessionManager.getInstance().getUser().getFdriverid());
        map.put("acctId", et_banknum.getText().toString().trim());
//        map.put("bId", bankclscode);
        map.put("bankId", bankclscode);
        map.put("mobilePhone", et_bankphone.getText().toString().trim());
        map.put("messageCode", et_authCode.getText().toString().trim());
//        map.put("tranAmount", et_authCode.getText().toString().trim());
        dialog.showDialog();
        XUtil.PostJsonObj(BaseURL.BINDMESBACKFILL, map, SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<BindBankCarkBean>() {
            @Override
            public void onSuccess(BindBankCarkBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()) {
                    finish();
                    UHelper.showToast(BindingBankCarkActivity.this, getString(R.string.binding_success));
                } else {
                    UHelper.showToast(BindingBankCarkActivity.this, result.getObj().getRspMsg());
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
     * 倒计时
     */
    private void timeDown() {

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * select bank
     */

    private void selectBank() {
        Intent intent = new Intent();
        intent.setClass(this, BankCarListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            bankclscode = data.getStringExtra("ZID");
            tv_bankname.setText(data.getStringExtra("ZBANKSNAME"));
        }
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE_SUBB) {
//            bankCode = data.getStringExtra("bankCode");
//            bankName = data.getStringExtra("bankName");
////            tv_bankname_1.setText(bankName);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

//    /**
//     * 选择地址
//     */
//    private void showAddress() {
//        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                tv_province.setText(options1Items.get(options1).getPickerViewText());
//                tv_city.setText(options2Items.get(options1).get(option2).getPickerViewText());
//                citycode = options2Items.get(options1).get(option2).getCity_oraareacode();
//                areacitycode = options3Items.get(options1).get(option2).get(options3).getCity_oraareacode();
//                areacitycode = TextUtils.isEmpty(areacitycode) ? citycode : areacitycode;
//            }
//        }).build();
//        pvOptions.setPicker(options1Items, options2Items, options3Items);
//        pvOptions.show();
//    }

    /**
     * 解析省市区json数据
     */
    private ArrayList<ProvinceEntity> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityEntity>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<AreaEntity>>> options3Items = new ArrayList<>();

    private void initJsonData() {
        /**
         * 解析资源文件省市区json数据
         */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceEntity> jsonBean = parseData(JsonData);//用Gson 转成实体
        options1Items = jsonBean;
        for (int i = 0; i < options1Items.size(); i++) {//遍历省份
            ArrayList<CityEntity> CityList = new ArrayList<CityEntity>();//该省的城市列表（第二级）
            ArrayList<ArrayList<AreaEntity>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < options1Items.get(i).getListShi().size(); c++) {//遍历该省份的所有城市
                CityEntity entity = options1Items.get(i).getListShi().get(c);
                CityList.add(entity);//添加城市
                ArrayList<AreaEntity> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (options1Items.get(i).getListShi().get(c).getQulist() == null
                        || options1Items.get(i).getListShi().get(c).getQulist().size() == 0) {
                    AreaEntity entity1 = new AreaEntity();
//                    entity1.setCity_areaname(options1Items.get(i).getListShi().get(c).getCity_areaname());
//                    entity1.setCity_areacode(options1Items.get(i).getListShi().get(c).getCity_areacode());
//                    entity1.setCity_areatype(options1Items.get(i).getListShi().get(c).getCity_areatype());
//                    entity1.setCity_nodecode(options1Items.get(i).getListShi().get(c).getCity_nodecode());
//                    entity1.setCity_topareacode2(options1Items.get(i).getListShi().get(c).getCity_topareacode2());
//                    entity1.setCity_oraareacode(options1Items.get(i).getListShi().get(c).getCity_oraareacode());
                    entity1.setCity_areaname("");
                    entity1.setCity_areacode("");
                    entity1.setCity_areatype("");
                    entity1.setCity_nodecode("");
                    entity1.setCity_topareacode2("");
                    entity1.setCity_oraareacode("");
                    City_AreaList.add(entity1);
                } else {
                    for (int d = 0; d < options1Items.get(i).getListShi().get(c).getQulist().size(); d++) {//该城市对应地区所有数据
                        AreaEntity areaEntity = options1Items.get(i).getListShi().get(c).getQulist().get(d);
                        City_AreaList.add(areaEntity);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<ProvinceEntity> parseData(String result) {//Gson 解析
        ArrayList<ProvinceEntity> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceEntity entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceEntity.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
