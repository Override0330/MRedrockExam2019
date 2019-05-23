package com.override0330.android.redrock.mredrockexam2019.presenter

import com.override0330.android.redrock.mredrockexam2019.contract.HomePageContract
import com.override0330.android.redrock.mredrockexam2019.model.HomePageModel
import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager

//这里应该用来处理所有的逻辑逻辑处理，Model只做为数据读取和存储
class HomePagePresenter: BasePresenter<HomePageContract.HomePageView>(){
    private val model = HomePageModel()
    fun showNowMusic(){
        if (MyMusicPlayerManager.instance.getMusicList().size==0){
            //添加播放列表
            model.initMusicList(object :HomePageContract.GetNowMusicCallBack{
                override fun onSuccess(music: MyMusic) {
                    mViewRef.get()!!.showMusic(music.name,music.author,music.imageUrl)
                }
                override fun onFail(info: String) {
                    mViewRef.get()!!.showToast(info)
                }
            })

        }else{
            model.getNowMusic(object :HomePageContract.GetNowMusicCallBack{
                override fun onSuccess(music: MyMusic) {
                    mViewRef.get()!!.showMusic(music.name,music.author,music.imageUrl)
                }

                override fun onFail(info: String) {
                    mViewRef.get()!!.showToast(info)
                }
            })
        }

    }
}