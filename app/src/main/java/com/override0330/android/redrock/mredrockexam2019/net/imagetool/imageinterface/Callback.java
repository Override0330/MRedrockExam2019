package com.override0330.android.redrock.mredrockexam2019.net.imagetool.imageinterface;

import android.graphics.Bitmap;

/**
 * callback接口
 */
public interface Callback {
    void succeed(Bitmap bitmap);
    void fail(Throwable throwable);
}
