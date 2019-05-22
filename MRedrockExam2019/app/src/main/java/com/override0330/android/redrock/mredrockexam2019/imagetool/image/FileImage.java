package com.override0330.android.redrock.mredrockexam2019.imagetool.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.Callback;
import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.ImageCacheUtil;
import com.override0330.android.redrock.mredrockexam2019.imagetool.imageinterface.ImageLoad;

import java.io.InputStream;


//从文件加载图片
public class FileImage implements ImageLoad {
    private int imageId;
    private Context context;

    public FileImage(int imageId, Context context) {
        this.imageId = imageId;
        this.context = context;
    }

    @Override
    public boolean setImage(ImageCacheUtil imageCacheUtil, Callback callback) {
        Resources r = context.getResources();
        InputStream is = r.openRawResource(imageId);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        if (bitmap!=null){
            callback.succeed(bitmap);
        }
        return false;
    }
}
