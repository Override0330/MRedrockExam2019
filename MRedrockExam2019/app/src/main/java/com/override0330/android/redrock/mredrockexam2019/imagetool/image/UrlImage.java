package com.override0330.android.redrock.mredrockexam2019.imagetool.image;

import android.graphics.Bitmap;

import com.override0330.android.redrock.mredrockexam2019.imagetool.threadpool.ThreadPoolWithDCL;
import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.Callback;
import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.ImageCacheUtil;
import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.ImageLoad;
import com.override0330.android.redrock.mredrockexam2019.imagetool.other.MD5Util;


//从URL加载图片
public class UrlImage implements ImageLoad {
    private String url;

    public UrlImage(String url) {
        this.url = url;
    }

    @Override
    public boolean setImage(ImageCacheUtil imageCacheUtil, Callback callback) {
        String key = MD5Util.crypt(url);
        final Bitmap[] image = {imageCacheUtil.getBitmapFromCache(key)};
        if (image[0] != null) {
            callback.succeed(image[0]);
            return true;
        }
        ThreadPoolWithDCL.getInstance().execute(url, callback, imageCacheUtil);
        return true;
    }

}
