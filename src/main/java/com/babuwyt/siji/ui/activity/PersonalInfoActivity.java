package com.babuwyt.siji.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babuwyt.siji.R;
import com.babuwyt.siji.base.BaseActivity;
import com.babuwyt.siji.base.SessionManager;
import com.babuwyt.siji.bean.UserInfoBean;
import com.babuwyt.siji.entity.UserInfoEntity;
import com.babuwyt.siji.finals.BaseURL;
import com.babuwyt.siji.utils.request.CommonCallback.ResponseCallBack;
import com.babuwyt.siji.utils.request.ImageOptions;
import com.babuwyt.siji.utils.request.XUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/22.
 * 个人信息页面
 */
@ContentView(R.layout.activity_peisonalinfo)
public class PersonalInfoActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_state)
    TextView tv_state;//状态
    @ViewInject(R.id.tv_name)
    TextView tv_name;//姓名
    @ViewInject(R.id.tv_idcard)
    TextView tv_idcard;//身份证
    @ViewInject(R.id.tv_phonenum)
    TextView tv_phonenum;//手机号
    @ViewInject(R.id.img_idcardimg1)
    ImageView img_idcardimg1;
    @ViewInject(R.id.img_idcardimg2)
    ImageView img_idcardimg2;
    @ViewInject(R.id.img_idcardimg3)
    ImageView img_idcardimg3;
    @ViewInject(R.id.tv_carnum)
    TextView tv_carnum;
    @ViewInject(R.id.cartype)
    TextView cartype;
    @ViewInject(R.id.tv_xingshizheng)
    TextView tv_xingshizheng;
    @ViewInject(R.id.tv_jiashizheng)
    TextView tv_jiashizheng;
    @ViewInject(R.id.tv_carsuoyouren)
    TextView tv_carsuoyouren;
    @ViewInject(R.id.img_jiashiimg1)
    ImageView img_jiashiimg1;
    @ViewInject(R.id.img_xingshizhengimg1)
    ImageView img_xingshizhengimg1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getPersonal();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getPersonal(){
        dialog.showDialog();
        XUtil.GetPing(BaseURL.GETPERSONAL_INFO, new ArrayList<String>(), SessionManager.getInstance().getUser().getWebtoken(), new ResponseCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                dialog.dissDialog();
                if (result.isSuccess()){
                    UserInfoEntity entity=result.getObj();
                    setData(entity);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                dialog.dissDialog();
            }
        });
    }
    private void setData(UserInfoEntity entity){
        tv_phonenum.setText(SessionManager.getInstance().getUser().getFphone());
        setState();
        tv_name.setText(SessionManager.getInstance().getUser().getFdrivername());
        tv_idcard.setText(entity.getFdrivingcard());
        x.image().bind(img_idcardimg1,BaseURL.BASE_IMAGE_URI+entity.getFcardpicture(), ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        x.image().bind(img_idcardimg2,BaseURL.BASE_IMAGE_URI+entity.getFcardpicturerear(),ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        x.image().bind(img_idcardimg3,BaseURL.BASE_IMAGE_URI+entity.getFhandcardpicture(),ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        tv_carnum.setText(entity.getFplateno());
        cartype.setText(entity.getCartype());
        tv_xingshizheng.setText(entity.getFdrivenumber());
        tv_jiashizheng.setText(entity.getFdrivingcard());
        tv_carsuoyouren.setText(entity.getFattribution());

        x.image().bind(img_xingshizhengimg1,BaseURL.BASE_IMAGE_URI+entity.getFdrivecard(),ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
        x.image().bind(img_jiashiimg1,BaseURL.BASE_IMAGE_URI+entity.getFdrivingpicture(),ImageOptions.options(ImageView.ScaleType.FIT_CENTER));
    }
    private void setState(){
        String Fdelet="";
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
}
