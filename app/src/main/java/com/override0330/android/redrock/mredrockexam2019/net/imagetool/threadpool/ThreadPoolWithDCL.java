package com.override0330.android.redrock.mredrockexam2019.net.imagetool.threadpool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.override0330.android.redrock.mredrockexam2019.net.imagetool.factory.ThreadPoolFactory;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.Callback;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface.ImageCacheUtil;
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.other.MD5Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;


public class ThreadPoolWithDCL {
    private static ThreadPoolWithDCL threadPoolWithDCL;
    private Executor executor = ThreadPoolFactory.createThreadPool();
    private ThreadPoolWithDCL(){}

    public static ThreadPoolWithDCL getInstance(){
        if (threadPoolWithDCL==null){
            synchronized (ThreadPoolWithDCL.class){
                if (threadPoolWithDCL==null){
                    return new ThreadPoolWithDCL();
                }
            }
        }
        return threadPoolWithDCL;
    }

    public void execute(final String url, final Callback callback, final ImageCacheUtil imageCacheUtil){
        final HttpURLConnection[] connection = {null};
        final URL[] mUrl = new URL[1];

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    mUrl[0] = new URL(url);
                    connection[0] = (HttpURLConnection) mUrl[0].openConnection();
                    //设置图片加载时长为15s
                    connection[0].setConnectTimeout(15000);
                    int responseCode = connection[0].getResponseCode();
                    if (responseCode == 200){
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        InputStream inputStream = connection[0].getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                        inputStream.close();
                        callback.succeed(bitmap);
                        String key = MD5Util.crypt(url);
                        imageCacheUtil.setBitmapToCache(key,bitmap);
                    }
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                    callback.fail(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.fail(e);
                }finally {
                    if (connection[0] != null){
                        connection[0].disconnect();
                    }
                }
            }
        });

    }
}
