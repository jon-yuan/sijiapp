package com.babuwyt.siji.base;

import android.text.TextUtils;

import com.babuwyt.siji.entity.UserInfoEntity;

/**
 * Created by lenovo on 2017/6/30.
 */

public class SessionManager {
    private String servicephone;
    private static SessionManager manager;
    /**
     * 文件保存的路径，文件夹
     */
    private String appFileDirPath;
    /**
     * 保存的当前登录的用户对象*
     */
    private UserInfoEntity mUser;

    /**
     * 定位信息
     */
//    private LocationEntity entity;
    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (manager == null) {
            manager = new SessionManager();
        }
        return manager;

    }

    /**
     * 更新当前登录的用户对象
     *
     * @param user
     */
    public void setUser(UserInfoEntity user) {
        this.mUser = user;
    }

//    public LocationEntity getEntity() {
//        return entity;
//    }

//    public void setEntity(LocationEntity entity) {
//        this.entity = entity;
//    }

    /**
     * 获取当前登录的用户对象
     *
     * @return
     */
    public UserInfoEntity getUser() {
        return mUser == null ? new UserInfoEntity(): mUser;
    }

    /**
     * 是否已经登陆
     */
    public boolean isLogin() {
        boolean b = false;
        if (mUser == null) {
            b = false;
        } else {
            if (TextUtils.isEmpty(mUser.getFdriverid())) {
                b = false;
            } else {
                b = true;
            }
        }
        return b;
    }

    /**
     * 设置保存路径
     *
     * @param path
     */
    public void setAppFileDirPath(String path) {
        this.appFileDirPath = path;
    }

    public String getAppFileDirPath() {
        return appFileDirPath;
    }

    public String getServicephone() {
        return servicephone;
    }

    public void setServicephone(String servicephone) {
        this.servicephone = servicephone;
    }
}
