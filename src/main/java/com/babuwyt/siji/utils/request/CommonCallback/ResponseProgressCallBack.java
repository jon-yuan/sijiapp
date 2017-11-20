package com.babuwyt.siji.utils.request.CommonCallback;

import org.xutils.common.Callback;

/**
 * Created by lenovo on 2017/6/28.
 */

public abstract class ResponseProgressCallBack<ResultType> implements Callback.ProgressCallback<ResultType>{

    @Override
    public void onSuccess(ResultType result) {
        //根据公司业务需求，处理相应业务逻辑
        Success(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //根据公司业务需求，处理相应业务逻辑
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {
        Started();
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        Loading(total,current,isDownloading);
    }


    public abstract void Started();
    public abstract void Success(ResultType resultType);
    public abstract void Loading(long total, long current, boolean isDownloading);

}
