package com.override0330.android.redrock.demoscrollimageview

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

class CustomTransformer:ViewPager.PageTransformer {
    private val SET_SCALE = 0.8F
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun transformPage(p0: View, p1: Float) {
        when {
            p1 <= -1 -> {
                p0.scaleY = SET_SCALE
                p0.scaleX = SET_SCALE
            }
            p1 <= 1 -> {
                val scale = Math.max(SET_SCALE,1-Math.abs(p1))
                p0.scaleY = scale
                p0.scaleX = scale

                p0.translationX = p0.width*-p1
//                Log.d("x","${p0.width*-p1}")
                p0.translationY= p1*p0.height
//                Log.d("y","${p1*p0.height}")
                //使位于中间的view为最顶层的无比巧妙的方法
//                if (scale==0.8F){
//                    p0.z = 0F
//                }else{
//                    p0.z = 1F
//                }
            }
            else -> {
                p0.scaleY = SET_SCALE
                p0.scaleX = SET_SCALE
            }
        }
    }
}