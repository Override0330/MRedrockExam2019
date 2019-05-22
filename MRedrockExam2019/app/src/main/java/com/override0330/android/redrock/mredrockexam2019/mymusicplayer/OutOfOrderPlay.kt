package com.override0330.android.redrock.mredrockexam2019.mymusicplayer

import com.override0330.android.redrock.mredrockexam2019.model.music.MyMusic
import kotlin.collections.ArrayList
class OutOfOrderPlay :PlayRule {
    override lateinit var musicList: ArrayList<MyMusic>
    override var nowMusic: MyMusic
        get() = musicList[nowPosition]
        set(value) {}
    override var nowPosition: Int = 0
    init {
        //使用随机列表时只随机接下来的歌曲顺序
        val first=musicList[0]
        musicList.remove(first)
        musicList.shuffle()
        musicList.add(0,first)
    }
}