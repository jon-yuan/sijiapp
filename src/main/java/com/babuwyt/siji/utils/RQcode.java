package com.babuwyt.siji.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.UnsupportedEncodingException;

/**
 * Created by lenovo on 2017/11/9.
 */

public class RQcode {
    /**
     * 生成二维码
     * @param content
     * @param newsize
     * @return
     * @throws WriterException
     */
    public Bitmap CreateTwoDCode(String content, int newsize) throws WriterException {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, newsize, newsize);//这种方法不支持生成中文二维码
            matrix = new MultiFormatWriter().encode(new String(content.getBytes("UTF-8"), "ISO-8859-1"),
                    BarcodeFormat.QR_CODE, newsize, newsize);
        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;//黑色
                } else {
                    pixels[y * width + x] = 0xffffffff;//白色
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
