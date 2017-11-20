package com.babuwyt.siji.utils;

import android.content.Context;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.DeleteObjectRequest;
import com.tencent.cos.model.DeleteObjectResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;
import com.tencent.cos.task.listener.IUploadTaskListener;

/**
 * Created by lenovo on 2017/9/27.
 */

public class TencentYunUtils {


    public static void upload(Context context,String srcPath,String cosPath,IUploadTaskListener listener){
        String appid = TentSign.appId;
        String bucket = TentSign.bucket;
        String peristenceId = null;
        //创建COSClientConfig对象，根据需要修改默认的配置参数
        final COSClientConfig config = new COSClientConfig();
        //如设置园区
        config.setEndPoint(COSEndPoint.COS_GZ);
        COSClient cos = new COSClient(context, appid, config, peristenceId);
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        putObjectRequest.setBucket(bucket);
        putObjectRequest.setCosPath(cosPath);
        putObjectRequest.setSrcPath(srcPath);
        putObjectRequest.setSign(TentSign.getSingImg(null));
        putObjectRequest.setListener(listener);
//        cos.putObject(putObjectRequest);
        cos.putObjectAsyn(putObjectRequest);
    }
    public static void Del(Context context,String path){
        String appid = TentSign.appId;
        String bucket = TentSign.bucket;
        String peristenceId = null;
        final COSClientConfig config = new COSClientConfig();
        //如设置园区
        config.setEndPoint(COSEndPoint.COS_GZ);
        COSClient cos = new COSClient(context, appid, config, peristenceId);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest();
        deleteObjectRequest.setBucket(bucket);
        deleteObjectRequest.setCosPath(path);
        deleteObjectRequest.setSign(TentSign.getSingImg(path));
        deleteObjectRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
            }
            @Override
            public void onFailed(COSRequest COSRequest, COSResult cosResult) {
            }
        });
        DeleteObjectResult result = cos.deleteObject(deleteObjectRequest);
    }




}
