package com.override0330.android.redrock.mredrockexam2019.model

import com.override0330.android.redrock.mredrockexam2019.contract.HomePageContract
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.Callback
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.NetUtil
import com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper.Request
import com.override0330.android.redrock.mredrockexam2019.bean.music.MyMusic
import com.override0330.android.redrock.mredrockexam2019.bean.music.PrePareMusicFromUrl
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager
import org.json.JSONObject

class HomePageModel: HomePageContract.HomePageModel{
    override fun getNowMusic(callBack: HomePageContract.GetNowMusicCallBack) {
        val music = MyMusicPlayerManager.instance.nowMusic()
        callBack.onSuccess(music)
    }

    override fun initMusicList(callBack: HomePageContract.GetNowMusicCallBack) {
        val request = Request.Builder("http://elf.egos.hosigus.com/music/playlist/detail?id=442265915").build()
        NetUtil.getInstance().execute(request,object :Callback{
            override fun onResponse(response: String?) {
                MyMusicPlayerManager.instance.setMusicList(analyze(response),0)
                callBack.onSuccess(MyMusicPlayerManager.instance.nowMusic())

            }

            override fun onFailed(t: Throwable?) {
                t!!.printStackTrace()
            }
        })
    }

    private fun analyze(json:String?):ArrayList<MyMusic> {
        val list: ArrayList<MyMusic> = ArrayList()
        val musicArray = JSONObject(json)
                .getJSONObject("playlist")
                .getJSONArray("tracks")
        for (i in 0 until musicArray.length()) {
            val musicJson = musicArray.getJSONObject(i)
            val name = musicJson.getString("name")
            val id = musicJson.getString("id")
            val authorArray = musicJson.getJSONArray("ar")
            var author = ""
            for (j in 0 until authorArray.length()) {
                author += authorArray.getJSONObject(j).getString("name")
                if (j != authorArray.length() - 1) author += ","
            }
            val al = musicJson.getJSONObject("al")
            val alName = al.getString("name")
            val imageUrl = al.getString("picUrl")
            val music = MyMusic(name, id, author, imageUrl, PrePareMusicFromUrl("http://music.163.com/song/media/outer/url?id=$id.mp3"))
            list.add(music)
        }
        return list
    }
}