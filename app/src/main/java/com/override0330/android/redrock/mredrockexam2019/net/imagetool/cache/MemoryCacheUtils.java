package com.override0330.android.redrock.mredrockexam2019.net.imagetool.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.ImageCacheUtil;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.other.Smaller;

import static android.content.ContentValues.TAG;

/**
 * 内存的图片缓存,By DLC
 */
public class MemoryCacheUtils implements ImageCacheUtil {
    private static MemoryCacheUtils memoryCacheUtils;
    private LruCache<String,Bitmap> stringBitmapLruCache;


    public static MemoryCacheUtils getInstance(){
        if (memoryCacheUtils == null){
            synchronized (MemoryCacheUtils.class){
                if (memoryCacheUtils == null){
                    return new MemoryCacheUtils();
                }
            }
        }
        return memoryCacheUtils;
    }

    public LruCache<String, Bitmap> getStringBitmapLruCache() {
        return stringBitmapLruCache;
    }

    //初始化
    private MemoryCacheUtils() {
        //将这个app能使用的内存的最大值的1/8设置为缓存的最大控件,超过这个空间后系统就开始回收
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        stringBitmapLruCache = new LruCache<>((int)maxMemory);
    }

    //将图片缓存到内存中
    @Override
    public void setBitmapToCache(String key, Bitmap bitmap) {
        //压缩图片质量
        Smaller smaller = new Smaller();
        bitmap = smaller.Dispose(bitmap);
        //强引用的方法,强弱引用的区别详情见博客
        stringBitmapLruCache.put(key,bitmap);
    }

    //将图片从内存中提取出来
    @Override
    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = stringBitmapLruCache.get(key);
        Log.d(TAG, "getBitmapFromCache: 从内存读取图片");
        return bitmap;
    }
}
