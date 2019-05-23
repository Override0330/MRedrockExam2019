package com.override0330.android.redrock.mredrockexam2019.net.imagetool.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.override0330.android.redrock.mredrockexam2019.net.imagetool.image.FileImage;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.image.UrlImage;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.Callback;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.ImageCacheUtil;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.ImageLoad;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.other.ImageDispose;


/**
 * 图片核心加载类
 */
public class ImageLoader {
    private Activity context;
    private ImageLoad imageLoad;
    private ImageCacheUtil imageCacheUtil;
    private ImageDispose imageDispose;

    //占位图默认值
    private static int placeHolder = android.support.compat.R.drawable.notification_icon_background;
    //错误图默认值
    private static int error = android.support.compat.R.drawable.notification_action_background;

    private ImageLoader(Activity context){
        this.context = context;
    }

    /**
     * with方法来创建新的ImageLoader对象并初始化context
     * @param MainActivityContext
     * @return
     */
    public static ImageLoader with (Activity MainActivityContext){
        return new ImageLoader(MainActivityContext);
    }

    /**
     * from方法进行依赖注入
     * @param url
     * @return
     */
    public ImageLoader from (String url){
        this.imageLoad = new UrlImage(url);
        return this;
    }
    public ImageLoader from (int path){
        this.imageLoad = new FileImage(path,context);
        return this;
    }

    public ImageLoader cacheWith(ImageCacheUtil imageCacheUtil){
        this.imageCacheUtil = imageCacheUtil;
        return this;
    }

    public ImageLoader disposeWith(ImageDispose imageDispose){
        this.imageDispose = imageDispose;
        return this;
    }

    public ImageLoader placeHolder (int resource){
        placeHolder = resource;
        return this;
    }

    public ImageLoader error (int resource){
        error = resource;
        return this;
    }

    public void into (final ImageView imageView){
        //加载占位图
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(placeHolder);
            }
        });
        //到这里才开始正式加载图片
        imageLoad.setImage(imageCacheUtil,new Callback() {
            @Override
            public void succeed(final Bitmap bitmap) {
                //成功
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageDispose!=null){
                            Bitmap newBitmap = imageDispose.Dispose(bitmap);
                            imageView.setImageBitmap(newBitmap);
                        }else imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void fail(Throwable throwable) {
                //失败
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(error);
                    }
                });
            }
        });
    }
}
