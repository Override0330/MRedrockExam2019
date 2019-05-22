package com.override0330.android.redrock.mredrockexam2019.view

import android.content.ComponentName
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import com.override0330.android.redrock.demoscrollimageview.CustomTransformer
import com.override0330.android.redrock.demoscrollimageview.MyViewPagerAdapter
import com.override0330.android.redrock.mredrockexam2019.R
import com.override0330.android.redrock.mredrockexam2019.contract.DetailMusicContract
import com.override0330.android.redrock.mredrockexam2019.imagetool.cache.DoubleCacheUtils
import com.override0330.android.redrock.mredrockexam2019.imagetool.core.ImageLoader
import com.override0330.android.redrock.mredrockexam2019.imagetool.other.CutToCircle
import com.override0330.android.redrock.mredrockexam2019.presenter.DetailMusicPresenter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMusicActivity : BaseActivity<DetailMusicContract.DetailView, DetailMusicPresenter>(),DetailMusicContract.DetailView,View.OnClickListener{
    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
    }

    override fun onService(name: ComponentName?, service: IBinder?) {
        Toast.makeText(this,"绑定成功",Toast.LENGTH_SHORT).show()
    }

    override fun createPresenter(): DetailMusicPresenter {
        return DetailMusicPresenter()
    }

    override fun showDetailMusic(name: String, author: String, imageUrl: String) {
        tv_detail_name.text = name
        tv_detail_author.text = author
        ImageLoader.with(this)
                .disposeWith(CutToCircle())
                .cacheWith(DoubleCacheUtils.getInstance())
                .into(iv_detail_music)
    }

    //显示歌词
    override fun showLyric(viewList:ArrayList<View>) {
        val adapter = MyViewPagerAdapter(viewList)
        mb_lyric.setAdapter(adapter,this)
        mb_lyric.setTransformer(CustomTransformer())
    }

    override fun showToast(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    //点击事件的集中处理
    override fun onClick(v: View?) {
        when{

        }
    }
}