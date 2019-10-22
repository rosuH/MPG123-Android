package me.rosuh.decoder

import android.content.Context
import android.provider.MediaStore
import java.util.*

/**
 * 本地音乐获取
 */
object LocalMusicUtils {

    /**
     * 查询本地的音乐文件
     */
    @JvmStatic
    fun loadFileData(context: Context): String {
        val resolver = context.contentResolver
        val cursor = resolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null
        )
        cursor!!.moveToFirst()
        val musicList = ArrayList<String>()
        if (cursor.moveToFirst()) {
            do {
                val url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                if (url.endsWith("mp3")){
                    musicList.add(url)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return musicList.first()
    }
}