package com.override0330.android.redrock.mredrockexam2019.mymusic

import android.media.MediaPlayer
import com.override0330.android.redrock.mredrockexam2019.mymusic.MusicPrepare

class PrePareMusicFromUrl(private val url:String): MusicPrepare {
    override fun prepare(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }
}