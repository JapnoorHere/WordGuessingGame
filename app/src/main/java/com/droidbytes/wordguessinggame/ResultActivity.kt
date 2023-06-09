package com.droidbytes.wordguessinggame

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResultActivity : AppCompatActivity() {
    private lateinit var clickMediaPlayer : MediaPlayer
    private lateinit var assetManager : AssetManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        assetManager=applicationContext.assets
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val musicFileName = "clickButton.mp3"
        val descriptor: AssetFileDescriptor = assetManager.openFd(musicFileName)
        clickMediaPlayer = MediaPlayer()
        clickMediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
        clickMediaPlayer.prepare()
        clickMediaPlayer.start()
    }
}