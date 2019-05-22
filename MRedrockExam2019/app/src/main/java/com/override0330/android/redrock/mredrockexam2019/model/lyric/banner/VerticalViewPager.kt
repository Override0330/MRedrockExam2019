package com.override0330.android.redrock.mredrockexam2019.lyric.banner

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class VerticalViewPager: ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun verticalTouchEvent(event:MotionEvent):MotionEvent{
        val moveX = (event.y/height) *width
        val moveY = (event.x/width)*height
        event.setLocation(moveX,moveY)
        return event
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { verticalTouchEvent(it) }
        return super.onInterceptTouchEvent(ev?.let { verticalTouchEvent(it) })
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(ev?.let { verticalTouchEvent(it) })
    }
}