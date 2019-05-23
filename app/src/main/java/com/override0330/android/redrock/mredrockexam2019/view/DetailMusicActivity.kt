package com.override0330.android.redrock.mredrockexam2019.view

import android.content.ComponentName
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.override0330.android.redrock.mredrockexam2019.customui.banner.CustomTransformer
import com.override0330.android.redrock.demoscrollimageview.MyViewPagerAdapter
import com.override0330.android.redrock.mredrockexam2019.R
import com.override0330.android.redrock.mredrockexam2019.contract.DetailMusicContract
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.cache.DoubleCacheUtils
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.core.ImageLoader
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.other.CutToCircle
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager
import com.override0330.android.redrock.mredrockexam2019.presenter.DetailMusicPresenter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMusicActivity : BaseActivity<DetailMusicContract.DetailView,
        DetailMusicPresenter>(),
        DetailMusicContract.DetailView,
        MyMusicPlayerManager.OnStartPlay,
        MyMusicPlayerManager.StartNextMusic,
        View.OnClickListener{
    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        iv_detail_play.setOnClickListener(this)
        iv_detail_previous.setOnClickListener(this)
        iv_detail_next.setOnClickListener(this)
        iv_detail_back.setOnClickListener(this)
        //音乐准备完毕的回调
        MyMusicPlayerManager.instance.setOnStartPlay(this)
        MyMusicPlayerManager.instance.setStartNextMusic(this)
    }

    //实现绑定成功后的音乐数据
    override fun onService(name: ComponentName?, service: IBinder?) {
        Toast.makeText(this,"绑定成功",Toast.LENGTH_SHORT).show()
        changeNowMusic()
    }

    override fun createPresenter(): DetailMusicPresenter {
        return DetailMusicPresenter()
    }

    override fun showDetailMusic(name: String, author: String, imageUrl: String) {
        tv_detail_name.text = name
        tv_detail_author.text = author
        ImageLoader.with(this)
                .from(imageUrl)
                .disposeWith(CutToCircle())
                .cacheWith(DoubleCacheUtils.getInstance())
                .into(iv_detail_music)
    }

    //改变音乐的时候必要操作，注意，这里可以进行一些歌词还没有获取但是已经可以进行的操作
    override fun changeNowMusic() {
        Log.d("刷新音乐","")
        mPresenter.getNowMusic()
        mPresenter.getLyric(this)
        mPresenter.startToChangeTextView()
        mPresenter.startToChangeSeekBar()
        sb_detail.max = MyMusicPlayerManager.instance.musicDuration()
        sb_detail.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            var isTouch = false
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (isTouch){
                    val position = seekBar!!.progress
                    MyMusicPlayerManager.instance.musicSeekTo(position)
                    mPresenter.pause()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTouch = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isTouch = false
                MyMusicPlayerManager.instance.play()
            }

        })
    }
    //触发显示歌词的回调，注意，这里应该放只有获取到了歌词之后才可以做出的ui操作
    override fun showLyric(viewList:ArrayList<View>) {
        Log.d("歌词显示回调","成功")
        runOnUiThread {
            val adapter = MyViewPagerAdapter(viewList)
            mb_lyric.init()
            mb_lyric.setScrollTime(1500)
            mb_lyric.setAdapter(adapter,this)
            mb_lyric.setTransformer(CustomTransformer())
            mPresenter.startToChangeLyric()
        }
    }

    override fun onNextMusic() {
        mPresenter.playNext()
    }

    override fun showToast(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun changeLyricPosition(position: Int) {
        runOnUiThread {
            mb_lyric.changeTo(position)
        }
    }

    override fun changeNowTimeTextView(time: String) {
        runOnUiThread{
            tv_detail_now.text = time
        }
    }

    override fun changeSeekBarPosition(position: Int) {
        runOnUiThread {
            sb_detail.progress = position
        }
    }

    //点击事件的集中处理
    override fun onClick(v: View?) {
        when{
            v!!.id == iv_detail_play.id -> {
                if (MyMusicPlayerManager.instance.isPlaying()){
                    mPresenter.pause()
                }else{
                    mPresenter.play()
                }
            }
            v.id == iv_detail_previous.id -> {
                mPresenter.playPrevious()
            }
            v.id == iv_detail_next.id -> {
                mPresenter.playNext()
            }
            v.id == iv_detail_back.id -> {
                this.finish()
            }
        }
    }

    /**
     * 生命周期相关
     */

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cancelTimer()
    }
}