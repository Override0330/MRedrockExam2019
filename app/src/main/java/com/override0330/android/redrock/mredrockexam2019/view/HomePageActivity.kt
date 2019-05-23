package com.override0330.android.redrock.mredrockexam2019.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.override0330.android.redrock.mredrockexam2019.R
import com.override0330.android.redrock.mredrockexam2019.contract.HomePageContract
import com.override0330.android.redrock.mredrockexam2019.mymusicplayer.MyMusicPlayerManager
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.cache.DoubleCacheUtils
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.core.ImageLoader
import com.override0330.android.redrock.mredrockexam2019.net.imagetool.other.CutToCircle
import com.override0330.android.redrock.mredrockexam2019.presenter.HomePagePresenter
import com.override0330.android.redrock.mredrockexam2019.service.MyMusicService
import kotlinx.android.synthetic.main.activity_home_page.*

/**
 * MVP中的View层，只负责展示UI和响应时间
 * 主页activity 主要功能：
 * 1.显示当前歌曲的名字、作者、专辑图片
 * 2.切换心情歌单
 * 3.切换到歌曲详情页
 */
class HomePageActivity : BaseActivity<HomePageContract.HomePageView, HomePagePresenter>(), HomePageContract.HomePageView,MyMusicPlayerManager.OnStartPlay, View.OnClickListener{

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home_page)
        iv_homepage_detail.setOnClickListener(this)
        MyMusicPlayerManager.instance.setOnStartPlay(this)
        mPresenter.showNowMusic()
    }

    override fun onService(name: ComponentName?, service: IBinder?) {
        Toast.makeText(this,"绑定成功",Toast.LENGTH_SHORT).show()
    }

    override fun createPresenter(): HomePagePresenter {
        return HomePagePresenter()
    }


    //展示UI,只能由HomepagePresenter的成功回调来触发
    override fun showMusic(name: String, author: String, imageUrl: String) {
        Log.d("回调成功","$name $author $imageUrl")
        this.runOnUiThread { tv_homepage_music_name.text = name
            tv_homepage_music_author.text = author
            ImageLoader.with(this)
                    .from(imageUrl)
                    .cacheWith(DoubleCacheUtils.getInstance())
                    .disposeWith(CutToCircle())
                    .into(iv_homepage_music) }
    }

    //给用户显示提示
    override fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun changeNowMusic() {
        mPresenter.showNowMusic()
    }

    //点击监听
    override fun onClick(v: View?) {
        when{
            v!!.id == iv_homepage_detail.id -> {
               //启动Detail页面
                val intent = Intent(this,DetailMusicActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.showNowMusic()
    }

}
