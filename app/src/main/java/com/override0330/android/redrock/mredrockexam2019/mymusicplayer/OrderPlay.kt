package com.override0330.android.redrock.mredrockexam2019.mymusicplayer

import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic


class OrderPlay: PlayRule {
    override var musicList: ArrayList<MyMusic> = ArrayList()
    override var nowMusic: MyMusic
        get() = musicList[nowPosition]
        set(value) {}
    override var nowPosition = 0
}