package com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface;

import android.graphics.Bitmap;

public interface ImageCacheUtil {
    void setBitmapToCache(String key, Bitmap bitmap);
    Bitmap getBitmapFromCache(String url);
}
