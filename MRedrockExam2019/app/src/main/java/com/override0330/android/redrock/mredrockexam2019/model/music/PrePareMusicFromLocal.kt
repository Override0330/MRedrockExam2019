package com.override0330.android.redrock.mredrockexam2019.mymusic

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.override0330.android.redrock.mredrockexam2019.mymusic.MusicPrepare

class PrePareMusicFromLocal(private val path:String,private val context: Context): MusicPrepare {
    override fun prepare(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context,Uri.parse(path))
        mediaPlayer.prepare()
    }

}