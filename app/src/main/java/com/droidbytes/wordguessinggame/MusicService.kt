package com.droidbytes.wordguessinggame

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var assetManager : AssetManager

    override fun onCreate() {
        super.onCreate()
        assetManager = applicationContext.assets
        val musicFileName = "bgMusic.mp3"
        val descriptor: AssetFileDescriptor = assetManager.openFd(musicFileName)
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
        mediaPlayer.isLooping=true
        mediaPlayer.prepare()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        mediaPlayer.stop()
        super.onTaskRemoved(rootIntent)

    }
}