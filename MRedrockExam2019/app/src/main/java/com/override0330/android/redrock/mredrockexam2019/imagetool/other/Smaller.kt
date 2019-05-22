package com.override0330.android.redrock.mredrockexam2019.imagetool.other

import android.graphics.Bitmap
import android.graphics.Matrix

class Smaller:ImageDispose{
    override fun Dispose(bitmap: Bitmap): Bitmap {
        val matrix =Matrix()
        matrix.setScale(0.7F,0.7F)
        return Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
    }
}