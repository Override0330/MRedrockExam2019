package com.override0330.android.redrock.mredrockexam2019.net.imagetool.cache;

import android.graphics.Bitmap;

import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.ImageCacheUtil;


/**
 * 综合了本地缓存和内存缓存的双缓存
 */
public class DoubleCacheUtils implements ImageCacheUtil {

    private LocalCacheUtils localCacheUtils = LocalCacheUtils.getInstance();
    private MemoryCacheUtils memoryCacheUtils = MemoryCacheUtils.getInstance();
    private static DoubleCacheUtils doubleCacheUtils;

    private DoubleCacheUtils() {}

    public static DoubleCacheUtils getInstance (){
        if (doubleCacheUtils == null){
            synchronized (DoubleCacheUtils.class){
                if (doubleCacheUtils == null){
                    return new DoubleCacheUtils();
                }
            }
        }
        return doubleCacheUtils;
    }

    @Override
    public void setBitmapToCache(String key, Bitmap bitmap) {

        memoryCacheUtils.setBitmapToCache(key,bitmap);

        localCacheUtils.setBitmapToCache(key,bitmap);
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        //优先从内存中读取
        Bitmap bitmapFromMemory = memoryCacheUtils.getStringBitmapLruCache().get(key);
        if (bitmapFromMemory != null)return bitmapFromMemory;

        //如果内存中没有则从本地读取
        Bitmap bitmapFromLocal = localCacheUtils.getBitmapFromCache(key);
        if (bitmapFromLocal != null)return bitmapFromLocal;

        return null;
    }
}
