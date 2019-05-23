package com.override0330.android.redrock.mredrockexam2019.presenter

import android.content.Context
import android.view.View
import com.override0330.android.redrock.mredrockexam2019.contract.DetailMusicContract
import com.override0330.android.redrock.mredrockexam2019.model.DetailMusicModel
import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager
import java.util.*
import kotlin.collections.ArrayList

class DetailMusicPresenter : BasePresenter<DetailMusicContract.DetailView>(){
    private var lyricTimer:Timer = Timer()
    private var textViewTimer:Timer = Timer()
    private var seekBarTimer:Timer = Timer()
    private val detailMusicModel = DetailMusicModel()
    //获取目前播放的音乐的回调
    fun getNowMusic(){
        detailMusicModel.getNowMusic(object :DetailMusicContract.GetNowMusicCallBack{
            override fun onSuccess(music: MyMusic) {
                mViewRef.get()!!.showDetailMusic(music.name,music.author,music.imageUrl)
            }

            override fun onFail(info: String) {
                mViewRef.get()!!.showToast(info)
            }
        })
    }

    fun getLyric(context: Context){
        detailMusicModel.getLyric(context,object :DetailMusicContract.GetLyricCallBack{
            override fun onSuccess(viewList:ArrayList<View>) {
                mViewRef.get()!!.showLyric(viewList)
            }

            override fun onFail(info: String) {
                mViewRef.get()!!.showToast(info)
            }
        })
    }

    fun startToChangeLyric(){
        lyricTimer = Timer()
        lyricTimer.schedule(object : TimerTask() {
            override fun run() {
                mViewRef.get()!!.changeLyricPosition(MyMusicPlayerManager.instance.getNowLyricPosition())
            }
        }
        ,0,100)
    }

    fun startToChangeTextView(){
        textViewTimer = Timer()
        textViewTimer.schedule(object : TimerTask(){
            override fun run() {
                mViewRef.get()!!.changeNowTimeTextView(MyMusicPlayerManager.instance.nowTimeInMin())
            }
        },0,100)
    }

    fun startToChangeSeekBar(){
        seekBarTimer = Timer()
        seekBarTimer.schedule(object :TimerTask(){
            override fun run() {
                mViewRef.get()!!.changeSeekBarPosition(MyMusicPlayerManager.instance.musicCurrent())
            }
        },0,100)
    }

    //音乐控制
    fun play(){
        MyMusicPlayerManager.instance.play()
    }
    fun pause(){
        MyMusicPlayerManager.instance.pause()
    }
    fun playPrevious(){
        cancelTimer()
        MyMusicPlayerManager.instance.playPrevious()
    }
    fun playNext(){
        cancelTimer()
        MyMusicPlayerManager.instance.playNext()
    }
    fun cancelTimer(){
        lyricTimer.cancel()
        textViewTimer.cancel()
        seekBarTimer.cancel()
    }
}