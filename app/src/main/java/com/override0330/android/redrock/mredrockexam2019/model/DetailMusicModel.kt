package com.override0330.android.redrock.mredrockexam2019.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.override0330.android.redrock.mredrockexam2019.R
import com.override0330.android.redrock.mredrockexam2019.contract.DetailMusicContract
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.Callback
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.NetUtil
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.Request
import com.override0330.android.redrock.mredrockexam2019.bean.Lyric
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager
import org.json.JSONObject

class DetailMusicModel: DetailMusicContract.DetailModel {
    override fun getNowMusic(callBack: DetailMusicContract.GetNowMusicCallBack){
        val music = MyMusicPlayerManager.instance.nowMusic()
        callBack.onSuccess(music)
    }

    override fun getLyric(context:Context,callBack: DetailMusicContract.GetLyricCallBack) {
        val music = MyMusicPlayerManager.instance.nowMusic()
        val request = Request.Builder("http://elf.egos.hosigus.com/music/lyric?id=${music.id}")
                .setMethod("GET").build()
        NetUtil.getInstance().execute(request,object :Callback{
            override fun onResponse(response: String?) {
                val mainJson = JSONObject(response)
                val str = mainJson.getJSONObject("lrc").getString("lyric")
                val lyric = Lyric(str!!)
                MyMusicPlayerManager.instance.nowMusic().lyric = lyric
                val viewList = ArrayList<View>()
                for (i in 0 until lyric.arrayList.size){
                    val view = LayoutInflater.from(context).inflate(R.layout.item_lyric,null)
                    view.findViewById<TextView>(R.id.tv_item_lyric).text=lyric.arrayList[i]
                    viewList.add(view)
                }
                callBack.onSuccess(viewList)
            }
            override fun onFailed(t: Throwable?) {

            }
        })
    }
}