package com.override0330.android.redrock.mredrockexam2019.httprequsethelper;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetUtil {

    //使用静态内部类的单例模式来创建网络请求专用的线程池,线程安全,同时达到了懒加载效果

    private static class Holder{
        private final static NetUtil INSTANCE = new NetUtil();

    }

    public static NetUtil getInstance(){
        return Holder.INSTANCE;
    }

    /**
     * ThreadPoolExecutor的构造方法中的参数含义
     */
    //核心线程数量,同时能够执行的线程数量
    private int corePoolSize;
    //最大线程的数量,能够容纳的最大排队数
    private int maxPoolSize;
    //正在排队的任务的最长的排队时间
    private long keepAliveTime = 30;
    //正在排队的任务的最长排队时间的单位
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    //线程池对象
    private ThreadPoolExecutor executor;

    //将构造方法设为私有,故无法从其他方式创建对象,构成单例
    private NetUtil() {
        //获取当前设备可用的核心处理器核心数*2+1,赋值给核心线程的数量,能使性能达到极致
        corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
        //30应该够......
        maxPoolSize = 30;
        //初始化线程池
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                //缓冲队列,存放排队的任务,类似栈结构
                new LinkedBlockingDeque<Runnable>(),
                //创建线程的工厂(工厂模式?
                Executors.defaultThreadFactory(),
                //处理那些超出最大线程数量的策略
                new ThreadPoolExecutor.AbortPolicy()
                );
    }

    public void execute(Request request, final Callback callback) {
        final String url = request.getUrl();
        final String method = request.getMethod();
        final int readTimeout = request.getReadTimeout();
        final int connectTimeout = request.getConnectTimeout();
        final String key[] = request.getKey();
        final String value[] = request.getValue();
        final String contentType = request.getContentType();

        //开始网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String response = null;
                HttpURLConnection connection = null;
                try {
                    URL mUrl = new URL(url);
                    String data = "";
                    connection = (HttpURLConnection) mUrl.openConnection();
                    connection.setRequestMethod(method);
                    connection.setReadTimeout(readTimeout);
                    connection.setConnectTimeout(connectTimeout);
                    if (method.equals("POST")) {
                        for (int i = 0; i < key.length; i++) {
                            data = data + key[i] + "=" + value[i];
                            if (i != key.length - 1) {
                                data = data + "&";
                            }
                        }
                        byte[] sendData = data.getBytes();
                        int length = sendData.length;
                        connection.setRequestProperty("Content-Type", contentType);
                        connection.setRequestProperty("Content-Length", length + "");
                        connection.setDoOutput(true);
                        OutputStream out = connection.getOutputStream();
                        out.write(data.getBytes());
                    }
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = connection.getInputStream();
                        response = new JSONObject(getStringFromInputStream(is)).toString();
                        //回调
                        Log.d("网络请求url"+data,"返回值："+response);
                        Log.d("网络请求成功","开始回调");
                        callback.onResponse(response);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    callback.onFailed(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailed(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailed(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }
    private String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }

}
