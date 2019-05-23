package com.override0330.android.redrock.mredrockexam2019.bean.music

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class PrePareMusicFromLocal(private val path:String,private val context: Context): MusicPrepare {
    override fun prepare(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context,Uri.parse(path))
        mediaPlayer.prepare()
    }

}