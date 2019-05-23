package com.override0330.android.redrock.mredrockexam2019.bean.music

import com.override0330.android.redrock.mredrockexam2019.bean.Lyric

class MyMusic(val name:String, val id:String, val author:String,val imageUrl:String,val musicPrepare: MusicPrepare){
    lateinit var lyric: Lyric
}