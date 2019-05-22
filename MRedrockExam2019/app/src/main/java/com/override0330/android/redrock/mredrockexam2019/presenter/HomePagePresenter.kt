package com.override0330.android.redrock.mredrockexam2019.presenter

import com.override0330.android.redrock.mredrockexam2019.contract.HomePageContract
import com.override0330.android.redrock.mredrockexam2019.model.HomePageModel
import com.override0330.android.redrock.mredrockexam2019.model.music.MyMusic

//这里应该用来处理所有的逻辑逻辑处理，Model只做为数据读取和存储
class HomePagePresenter: BasePresenter<HomePageContract.HomePageView>(){
    private val model = HomePageModel()
    fun showNowMusic(){
        model.getNowMusic(object:HomePageContract.GetNowMusicCallBack{
            override fun onSuccess(music: MyMusic) {
                mViewRef.get()!!.showMusic(music.name,music.author,music.imageUrl)
            }

            override fun onFail(info: String) {
                mViewRef.get()!!.showToast(info)
            }
        })
    }
}