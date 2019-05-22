package com.override0330.android.redrock.demoscrollimageview

import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup



class MyViewPagerAdapter(private var viewList:ArrayList<View>): PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val p1 = position%viewList.size
//        Log.d("index","$p1")
//        Log.d("position","$position")
        container.removeView(viewList[p1])
        container.addView(viewList[p1])
        return viewList[p1]
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //不用删除啦~~~删除的话会报错嗷
        container.removeView(viewList[position%viewList.size])
    }
}