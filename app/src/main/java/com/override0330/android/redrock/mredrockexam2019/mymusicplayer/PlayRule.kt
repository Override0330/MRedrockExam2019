package com.override0330.android.redrock.mredrockexam2019.mymusicplayer

import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic
//播放器的不同播放方式
interface PlayRule {
    var nowPosition:Int
    var musicList:ArrayList<MyMusic>
    var nowMusic: MyMusic
    fun getNext(): MyMusic {
        nowPosition++
        return musicList[nowPosition%musicList.size]
    }
    fun getPrevious(): MyMusic {
        nowPosition--
        return musicList[nowPosition%musicList.size]
    }
    fun changeList(musicList:ArrayList<MyMusic>, from:Int){
        this.musicList = musicList
        nowPosition = from
    }
}