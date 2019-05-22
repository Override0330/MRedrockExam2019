package com.override0330.android.redrock.mredrockexam2019.imagetool.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.ImageCacheUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

/**
 * 本地的图片缓存,ByDLC
 */
public class LocalCacheUtils implements ImageCacheUtil {
    private static LocalCacheUtils localCacheUtils;

    //设置本地缓存的地址为相册下的ImageCache目录
    private static final String localCachePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImageCache";

    private LocalCacheUtils(){}

    public static LocalCacheUtils getInstance(){
        if (localCacheUtils==null){
            synchronized (LocalCacheUtils.class){
                if (localCacheUtils== null){
                    return new LocalCacheUtils();
                }
            }
        }
        return localCacheUtils;
    }

    //将图片缓存
    @Override
    public void setBitmapToCache(String key, Bitmap bitmap) {
        File file = new File(localCachePath,key);

        //判断目录是否存在
        File directory = file.getParentFile();
        if (!directory.exists()){
            directory.mkdirs();
        }

        //开始保存图片到本地
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //将图片从本地提取出来
    @Override
    public Bitmap getBitmapFromCache(String key) {
        File file = new File(localCachePath,key);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            Log.d(TAG, "getBitmapFromCache: 从本地读取图片");
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
