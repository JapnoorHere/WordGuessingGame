package com.droidbytes.wordguessinggame

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
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
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assetManager=applicationContext.assets
        result=intent.getStringExtra("result") as String

        sharedPreferences=applicationContext.getSharedPreferences("score", MODE_PRIVATE)
        editor=sharedPreferences.edit()

        binding.results.text= "$result/15"
        if(result.toInt() > sharedPreferences.getString("bestScore","0")!!.toInt()) {
            editor.putString("bestScore", result)
            editor.commit()
            editor.apply()
        }

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