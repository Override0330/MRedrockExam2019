package com.override0330.android.redrock.mredrockexam2019.model

import com.override0330.android.redrock.mredrockexam2019.contract.HomePageContract
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager

class HomePageModel: HomePageContract.HomePageModel{
    override fun getNowMusic(callBack: HomePageContract.GetNowMusicCallBack) {
        val music = MyMusicPlayerManager.instance.nowMusic()
        callBack.onSuccess(music)
    }
}