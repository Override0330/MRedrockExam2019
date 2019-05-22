package com.override0330.android.redrock.mredrockexam2019.presenter

import java.lang.ref.Reference
import java.lang.ref.SoftReference

abstract class BasePresenter<T> {
    //View接口类型的弱引用,防止所持有的view已经被销毁，但是该presenter仍然持有，导致内存的泄露
    protected lateinit var mViewRef:Reference<T>

    //绑定View引用
    fun attachView(view:T){
        mViewRef = SoftReference<T>(view)
    }

    //获取当前绑定的View引用
    protected fun getView(): T? {
        return mViewRef.get()
    }

    //是否已绑定View
    fun isViewAttached(): Boolean {
        return mViewRef != null&&mViewRef.get()!=null
    }

    //解除引用
    fun detachView(){
        if (mViewRef != null){
            mViewRef.clear()
        }
    }
}