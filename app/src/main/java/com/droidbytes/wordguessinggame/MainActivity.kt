package com.droidbytes.wordguessinggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droidbytes.wordguessinggame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGame.setOnClickListener {
            var intent = Intent(this@MainActivity,GameActivity::class.java)
            startActivity(intent)
        }

    }
}