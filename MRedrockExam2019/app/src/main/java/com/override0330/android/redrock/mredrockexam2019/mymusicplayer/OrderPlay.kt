package com.override0330.android.redrock.mredrockexam2019.mymusicplayer

import com.override0330.android.redrock.mredrockexam2019.model.music.MyMusic


class OrderPlay: PlayRule {
    override lateinit var musicList: ArrayList<MyMusic>
    override var nowMusic: MyMusic
        get() = musicList[nowPosition]
        set(value) {}
    override var nowPosition = 0
}