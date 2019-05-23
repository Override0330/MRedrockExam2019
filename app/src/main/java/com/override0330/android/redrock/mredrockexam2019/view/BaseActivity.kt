package com.override0330.android.redrock.mredrockexam2019.view

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.override0330.android.redrock.mredrockexam2019.presenter.BasePresenter
import com.override0330.android.redrock.mredrockexam2019.service.MyMusicService

abstract class BaseActivity<V,T:BasePresenter<V>>:Activity(){

    private val connection: ServiceConnection = (object : ServiceConnection {
        //成功连接服务的回调
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("DetailActivity","绑定成功")
            //绑定成功之后的操作
            onService(name,service)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("DetailActivity","绑定失败")
        }
    })

    val TAG:String = javaClass.simpleName

    protected lateinit var mPresenter: T

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        //初始化Presenter
        mPresenter = createPresenter()
        //将Presenter和View绑定
        mPresenter.attachView(this as V)
        //初始化布局
        initView(savedInstanceState)
        //绑定服务
        val intent = Intent(this, MyMusicService::class.java)
        bindService(intent,connection, Context.BIND_AUTO_CREATE)
    }

    /**
     * 应该由子类进行实现的初始化view的方法
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 应该由子类进行实现的绑定成功回调
     */
    abstract fun onService(name: ComponentName?, service: IBinder?)
    /**
     * 创建对应的Presenter
     */
    abstract fun createPresenter():T

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MyMusicService::class.java)
        bindService(intent,connection, Context.BIND_AUTO_CREATE)
    }

    //解除绑定
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}