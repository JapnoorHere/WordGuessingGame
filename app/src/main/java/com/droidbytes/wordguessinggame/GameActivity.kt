package com.droidbytes.wordguessinggame

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.droidbytes.wordguessinggame.databinding.ActivityGameBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var questionCount: Int = 0
    private var answer: String = ""
    private var correct: Int = 0
    private var currentAnswer: String = ""
    private lateinit var questionList: ArrayList<String>
    private lateinit var answerList: ArrayList<String>
    private var isButtonClickable: Boolean = true
    private lateinit var questionListOld: ArrayList<String>
    private lateinit var answerListOld: ArrayList<String>
    private lateinit var clickMediaPlayer: MediaPlayer
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var hashMap: HashMap<String, String>
    private var remaining: Int = 0
    private lateinit var questionRef: DatabaseReference
    private lateinit var assetManager: AssetManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionRef = FirebaseDatabase.getInstance().reference.child("question")

        questionList = ArrayList()
        answerList = ArrayList()
        questionListOld = ArrayList()
        answerListOld = ArrayList()
        hashMap = HashMap()

        questionListOld = intent.getStringArrayListExtra("questions") as ArrayList
        answerListOld = intent.getStringArrayListExtra("answers") as ArrayList
        assetManager = applicationContext.assets

        sharedPreferences = applicationContext.getSharedPreferences("score", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val bestScore = sharedPreferences.getString("bestScore", "0")
        binding.bestScore.text = "Your Best Score : $bestScore"

        for (each in 0 until questionListOld.size) {
            hashMap[questionListOld[each]] = answerListOld[each]
        }

        val entries = hashMap.entries.shuffled()

        timer()

        for (each in entries) {
            questionList.add(each.key)
            answerList.add(each.value)
        }

        remaining = 15

        updateAnswerAndQuestion()

        binding.et1.doOnTextChanged { _, _, _, _ ->
            if (binding.et1.text.toString().isNotEmpty()) {
                binding.et2.requestFocus()
            }
            if (binding.et2.text.toString().isNotEmpty()) {
                binding.et2.requestFocus()
            }
        }

        binding.et2.doOnTextChanged { _, _, _, _ ->
            if (binding.et2.text.toString().isNotEmpty()) {
                binding.et3.requestFocus()
            } else {
                binding.et1.requestFocus()
            }

        }
        binding.et3.doOnTextChanged { _, _, _, _ ->
            if (binding.et3.text.toString().isNotEmpty()) {
                binding.et4.requestFocus()
            } else {
                binding.et2.requestFocus()
            }

        }
        binding.et4.doOnTextChanged { _, _, _, _ ->
            if (binding.et4.text.toString().isNotEmpty()) {
                binding.et5.requestFocus()
            } else {
                binding.et3.requestFocus()
            }

        }
        binding.et5.doOnTextChanged { _, _, _, _ ->
            if (binding.et5.text.toString().isNotEmpty()) {
                binding.et6.requestFocus()
            } else {
                binding.et4.requestFocus()
            }

        }
        binding.et6.doOnTextChanged { _, _, _, _ ->
            if (binding.et6.text.toString().isEmpty()) {
                binding.et5.requestFocus()
            }
        }

        binding.nextbtn.setOnClickListener {
            val musicFileName = "clickButton.mp3"
            val descriptor: AssetFileDescriptor = assetManager.openFd(musicFileName)
            clickMediaPlayer = MediaPlayer()
            clickMediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )

            clickMediaPlayer.prepare()
            clickMediaPlayer.start()

            answer = binding.et1.text.toString() +
                    binding.et2.text.toString() +
                    binding.et3.text.toString() +
                    binding.et4.text.toString() +
                    binding.et5.text.toString() +
                    binding.et6.text.toString()

            if (remaining == 1) {
                if (currentAnswer.equals(answer, ignoreCase = true)) {
                    correct++
                    binding.solvedtv.text = correct.toString()
                    val intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("result", correct.toString())
                    intent.putExtra("questions", questionList)
                    intent.putExtra("answers", answerList)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("result", correct.toString())
                    intent.putExtra("questions", questionList)
                    intent.putExtra("answers", answerList)
                    startActivity(intent)
                    finish()
                }
            } else {

                if (isButtonClickable && currentAnswer.equals(answer, ignoreCase = true)) {
                    isButtonClickable = false

                    countDownTimer.cancel()

                    binding.lottie.visibility = View.VISIBLE
                    binding.lottie.setAnimation("correct.json")
                    binding.lottie.playAnimation()

                    val handler = Handler()
                    handler.postDelayed({

                        binding.lottie.visibility = View.GONE

                        remaining -= 1

                        correct++
                        binding.solvedtv.setText(correct.toString())

                        isButtonClickable = true

                        countDownTimer.cancel()

                        updateAnswerAndQuestion()

                        timer()
                    }, 2000)

                } else {
                    if (isButtonClickable) {

                        isButtonClickable = false

                        countDownTimer.cancel()
                        binding.lottie.visibility = View.VISIBLE
                        binding.lottie.setAnimation("wrong.json")
                        binding.lottie.playAnimation()

                        val handler = Handler()
                        handler.postDelayed({
                            isButtonClickable = true
                            binding.lottie.visibility = View.GONE

                            remaining -= 1

                            countDownTimer.cancel()

                            timer()

                            updateAnswerAndQuestion()
                        }, 2000)
                    }
                }
            }
        }
    }

    fun updateAnswerAndQuestion() {

        binding.remainingTv.text = remaining.toString()
        binding.hintTv.text = questionList[questionCount].uppercase(Locale.getDefault())

        currentAnswer = answerList[questionCount]

        questionCount += 1
        if (questionCount >= questionList.size) {
            questionCount = 0
        }

        when (currentAnswer.length) {
            1 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.GONE
                binding.et3.visibility = View.GONE
                binding.et4.visibility = View.GONE
                binding.et5.visibility = View.GONE
                binding.et6.visibility = View.GONE
            }

            2 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.VISIBLE
                binding.et3.visibility = View.GONE
                binding.et4.visibility = View.GONE
                binding.et5.visibility = View.GONE
                binding.et6.visibility = View.GONE
            }

            3 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.VISIBLE
                binding.et3.visibility = View.VISIBLE
                binding.et4.visibility = View.GONE
                binding.et5.visibility = View.GONE
                binding.et6.visibility = View.GONE
            }

            4 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.VISIBLE
                binding.et3.visibility = View.VISIBLE
                binding.et4.visibility = View.VISIBLE
                binding.et5.visibility = View.GONE
                binding.et6.visibility = View.GONE
            }

            5 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.VISIBLE
                binding.et3.visibility = View.VISIBLE
                binding.et4.visibility = View.VISIBLE
                binding.et5.visibility = View.VISIBLE
                binding.et6.visibility = View.GONE
            }

            6 -> {
                binding.et1.visibility = View.VISIBLE
                binding.et2.visibility = View.VISIBLE
                binding.et3.visibility = View.VISIBLE
                binding.et4.visibility = View.VISIBLE
                binding.et5.visibility = View.VISIBLE
                binding.et6.visibility = View.VISIBLE
            }
        }
        binding.et1.text.clear()
        binding.et2.text.clear()
        binding.et3.text.clear()
        binding.et4.text.clear()
        binding.et5.text.clear()
        binding.et6.text.clear()
        binding.et1.requestFocus()
    }

    fun timer() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                binding.timerTv.text = secondsLeft.toString()
            }

            override fun onFinish() {
                if (remaining == 1) {
                    val intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("result", correct.toString())
                    intent.putExtra("questions", questionList)
                    intent.putExtra("answers", answerList)
                    startActivity(intent)
                    finish()
                } else {
                    binding.lottie.visibility = View.VISIBLE
                    binding.lottie.setAnimation("wrong.json")
                    binding.lottie.playAnimation()

                    val handler = Handler()
                    handler.postDelayed({
                        // Hide the Lottie animation view
                        binding.lottie.visibility = View.GONE
                        remaining -= 1
                        timer()
                        updateAnswerAndQuestion()
                    }, 2000)
                }
            }
        }
        countDownTimer.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        val musicFileName = "clickButton.mp3"
        val descriptor: AssetFileDescriptor = assetManager.openFd(musicFileName)

        clickMediaPlayer = MediaPlayer()
        clickMediaPlayer.setDataSource(
            descriptor.fileDescriptor,
            descriptor.startOffset,
            descriptor.length
        )
        clickMediaPlayer.prepare()
        clickMediaPlayer.start()
    }
}