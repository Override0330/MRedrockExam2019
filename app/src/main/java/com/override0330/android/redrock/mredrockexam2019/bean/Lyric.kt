package com.override0330.android.redrock.mredrockexam2019.bean

import java.io.BufferedReader
import java.io.StringReader
import java.util.*
import kotlin.collections.ArrayList

/**
 * 歌词类
 * 需求：将一长串字符串解析成一个数据结构，使得可以通过时间来获得当前时间应该显示的歌词
 */
class Lyric(json:String) {
    val list:TreeMap<Int,String> = TreeMap()
    val arrayList = ArrayList<String>()
    init{
        val bufferedReader = BufferedReader(StringReader(json))
        bufferedReader.forEachLine {
            val time=it.substringBeforeLast(']',"").substringAfter('[',"")
            val min = time.substringBefore(':',"00")
            val second = time.substringAfter(':',"").substringBefore('.',"")
            val millisecond = time.substringAfter('.',"")
            try{
                val key = min.toInt()*60000+second.toInt()*1000+millisecond.toInt()
                val value = it.substringAfterLast(']',"")
                list[key] = value
                arrayList.add(value)
            }catch (e:Exception){
                e.stackTrace
            }
        }
    }
    fun getLyric(time:Int):Int{
        val keySet = list.keys
        val it= keySet.iterator()
        var count = 0
        while (it.hasNext()){
            if (it.next()>time){
                return count-1
            }
            count++
        }
        return arrayList.size-1
    }
}