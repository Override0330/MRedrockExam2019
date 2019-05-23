package com.override0330.android.redrock.mredrockexam2019.contract

import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic

/**
 * 契约类，规范定义，定义功能和模板
 * 在契约类中定义view的接口，Model的接口。因为Model将数据返回给Presenter是使用的回调方式
 * 所以还需要在契约类中定义对应的回调接口
 */
class HomePageContract {

    //HomepageActivity的View接口层
    interface HomePageView{
        //展示歌曲的名字，作者，图片URL
        fun showMusic(name:String,author:String,imageUrl:String)
        fun showToast(message:String)
    }

    interface HomePageModel{
        fun getNowMusic(callBack: GetNowMusicCallBack)
        fun initMusicList(callBack: GetNowMusicCallBack)
    }
    interface GetNowMusicCallBack{
        fun onSuccess(music: MyMusic)
        fun onFail(info:String)
    }

}