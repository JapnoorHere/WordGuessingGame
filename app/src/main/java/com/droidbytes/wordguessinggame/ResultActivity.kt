package com.droidbytes.wordguessinggame

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droidbytes.wordguessinggame.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var clickMediaPlayer : MediaPlayer
    private lateinit var assetManager : AssetManager
    lateinit var binding : ActivityResultBinding
    var result : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assetManager=applicationContext.assets

        result=intent.getStringExtra("result") as String

        binding.results.text= "$result/15"
        binding.playAgain.setOnClickListener {
            val musicFileName = "clickButton.mp3"
            val descriptor: AssetFileDescriptor = assetManager.openFd(musicFileName)
            clickMediaPlayer = MediaPlayer()
            clickMediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            clickMediaPlayer.prepare()
            clickMediaPlayer.start()
            var intent=Intent(this@ResultActivity,GameActivity::class.java)
            intent.putExtra("questions",  getIntent().getStringArrayListExtra("questions") as ArrayList)
            intent.putExtra("answers", getIntent().getStringArrayListExtra("answers") as ArrayList)
            startActivity(intent)
            finish()
        }
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