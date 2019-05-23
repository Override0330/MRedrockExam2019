package com.override0330.android.redrock.mredrockexam2019.bean.music

import android.media.MediaPlayer
    //每个音乐的不同准备方式，其实只有异步加载和同步加载的区别
interface MusicPrepare {
    fun prepare(mediaPlayer: MediaPlayer)
}