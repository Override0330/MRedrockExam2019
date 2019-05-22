package com.override0330.android.redrock.mredrockexam2019.mymusicplayer

import android.app.Activity
import android.media.MediaPlayer
import android.os.Binder
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import com.override0330.android.redrock.mredrockexam2019.model.music.MyMusic
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

/**
 * 音乐播放器的管理类
 * 需求：
 * 仅开放给服务下列api：
 * 1.播放控制，播放，暂停，滑动条
 * 2.更改播放方式，下次生效
 * 3.更改播放列表，立即生效
 */
class MyMusicPlayerManager private constructor(): Binder() {
    //Kotlin的DLC单例模式,台酷了
    companion object{
        val instance:MyMusicPlayerManager by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ MyMusicPlayerManager() }
    }
    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }//懒加载
    private var timer = Timer()
    private var timer2 = Timer()
    //心情管理，下标代表位置，
    val faceList = arrayListOf(Face.EXCITING,Face.CLAM,Face.UNHAPPY,Face.HAPPY)
    enum class Face(val face:String){
        HAPPY("HAPPY"),
        UNHAPPY("UNHAPPY"),
        CLAM("CLAM"),
        EXCITING("EXCITING")
    }
    init {
        mediaPlayer.setOnCompletionListener { playNext() }
    }

    fun nowPosition() = playRule.nowPosition
    fun nowMusic() = playRule.nowMusic
    fun isPlaying()=  mediaPlayer.isPlaying

    private var playRule: PlayRule = OrderPlay() //播放方式
    private var onStartPlay: OnStartPlay? = null

    var nowTime: TextView? = null
    var activity: Activity? = null
    var seekBar: SeekBar? = null
    set(value) {
        field = value
        changeSeekBar()
    }
    //因为界面可能随时更换，seekBar也会随时更换，所以暴露setter、getter,并触发更换seekBar的回调

    /**
     * 对外暴露的接口
     */
    //设置、更新播放规则
    fun setPlayRule(playRule: PlayRule){
        this.playRule = playRule
        //播放方式应该拥有播放列表，在此处传入当前播放列表
    }
    //设置、更新播放列表
    fun setMusicList(musicList:ArrayList<MyMusic>, from:Int){
        playRule.changeList(musicList,from)
        playMusic(playRule.nowMusic)
    }
    //常规操作
    fun start(){//开始播放
        prepareMusic(playRule.musicList[playRule.nowPosition])
    }
    fun play(){//播放
        mediaPlayer.start()
    }
    fun pause(){
        mediaPlayer.pause()
    }
    fun stop(){
        mediaPlayer.stop()
    }
    fun playNext(){
        playMusic(playRule.getNext())
    }
    fun playPrevious(){
        playMusic(playRule.getPrevious())
    }
    fun setOnStartPlay(onStartPlay: OnStartPlay){
        this.onStartPlay = onStartPlay
    }
    fun getCurrentPosition():Int{
        return mediaPlayer.currentPosition
    }
    fun getLong():String{
        return changToMin(mediaPlayer.duration)
    }

    /**
     * 不公开的方法
     */
    //有用户需要点击准备音乐的方法🐎
    private fun prepareMusic(music: MyMusic){
        music.musicPrepare.prepare(mediaPlayer)
        mediaPlayer.setOnPreparedListener {
            onStartPlay!!.show()
            if (seekBar!=null){
                changeSeekBar()
            }
        }
    }
    //虽然是一个能够做到音乐点击就(放)送的方法，但是依然将其对外不公开，而是通过改变播放列表来间接调用该方法，或许以后还可以提供更改选项√
    private fun playMusic(music: MyMusic){
        mediaPlayer.setOnPreparedListener {
            play()
            //在这里回调奥
            onStartPlay!!.show()
            if (seekBar!=null){
                changeSeekBar()
            }
        }
        music.musicPrepare.prepare(mediaPlayer)
    }


    /**
     * seekBar相关方法
     */
    //依赖于kotlin的可为null特性，我们不需要进行判空的操作，使得即使seekBar为null也只是不执行方法√
    //只有两种情况会调用该方法，1.换歌了 2.换了界面（需要提前显示调用setSeekBar）
    private fun changeSeekBar(){
        seekBar!!.max = mediaPlayer.duration
        Log.d("总长度","${seekBar!!.max}")
        //初始化seekBar监听
        seekBar!!.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            var isTouch = false
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (isTouch){
                    val position = seekBar!!.progress
                    mediaPlayer.seekTo(position)
                    mediaPlayer.pause()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTouch = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isTouch = false
                mediaPlayer.start()
            }

        })
        //开始实时刷新SeekBar
        startRefreshSeekBar()
    }
    private fun startRefreshSeekBar() {
        timer.cancel()
        timer = Timer()
        timer.schedule(timerTask {
            val nowPosition = mediaPlayer.currentPosition
//            Log.d("","now: $nowPosition")
            seekBar!!.progress = nowPosition
        }, 0, 10)
    }

    fun setTextView(textView: TextView,activity: Activity){
        this.nowTime = textView
        this.activity = activity
        changeNowTimeView()

    }
    private fun changeNowTimeView(){
        timer2.cancel()
        timer2 = Timer()
        timer2.schedule(timerTask {
            activity?.runOnUiThread {
                if (nowTime!=null)nowTime?.text = changToMin(mediaPlayer.currentPosition) }
        }, 0, 10)
    }

    private fun changToMin(time:Int):String{
        val min = time/60000
        val second = Math.round(time/1000%60F)
        var minStr = min.toString()
        var secondStr = second.toString()
        if (min<10) minStr = "0"+minStr
        if (second<10) secondStr = "0"+secondStr
        return "$minStr:$secondStr"
    }
    //开始播放的回调。每当恢复播放，下一首，上一首时通知界面更新
    interface OnStartPlay{
        fun show()
    }
}