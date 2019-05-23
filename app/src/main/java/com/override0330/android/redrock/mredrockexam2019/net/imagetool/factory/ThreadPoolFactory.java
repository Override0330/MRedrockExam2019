package com.override0330.android.redrock.mredrockexam2019.net.imagetool.factory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 工厂类,用于创建一个线程池来供图片加载使用
 */
public class ThreadPoolFactory {
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
    private static final int maxPoolSize = 30;
    private static final long keepAliveTime = 30;
    private static final TimeUnit timeUnit = TimeUnit.MINUTES;

    public static Executor createThreadPool(){
        return  new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingDeque<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
