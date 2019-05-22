package com.override0330.android.redrock.mredrockexam2019.presenter

import android.content.Context
import android.view.View
import com.override0330.android.redrock.mredrockexam2019.contract.DetailMusicContract
import com.override0330.android.redrock.mredrockexam2019.model.DetailMusicModel
import com.override0330.android.redrock.mredrockexam2019.model.music.MyMusic

class DetailMusicPresenter : BasePresenter<DetailMusicContract.DetailView>(){
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
}