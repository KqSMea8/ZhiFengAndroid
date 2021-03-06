package com.zf.mart

import android.app.Application
import android.content.Context
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import com.zf.mart.utils.MediaLoader
import kotlin.properties.Delegates

class MyApplication : Application() {


    companion object {

        var context: Context by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //初始化album图像选择
        Album.initialize(AlbumConfig.newBuilder((this)).setAlbumLoader(MediaLoader()).build())

    }


}
