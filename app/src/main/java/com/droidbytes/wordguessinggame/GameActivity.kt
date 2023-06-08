package com.droidbytes.wordguessinggame

import android.content.Intent
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
    lateinit var binding: ActivityGameBinding
    var questionCount: Int = 0
    var answer: String = ""
    var correct: Int = 0
    var currentAnswer: String = ""
    lateinit var questionList: ArrayList<String>
    lateinit var answerList: ArrayList<String>

    lateinit var questionListOld: ArrayList<String>
    lateinit var answerListOld: ArrayList<String>


    lateinit var countDownTimer: CountDownTimer
    lateinit var hashMap: HashMap<String, String>
    var remaining: Int = 0
    lateinit var questionRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionRef = FirebaseDatabase.getInstance().reference.child("question")
        questionList = ArrayList()
        answerList = ArrayList()
        questionListOld = ArrayList()
        answerListOld = ArrayList()
        hashMap= HashMap()
        questionListOld=intent.getStringArrayListExtra("questions") as ArrayList
        answerListOld=intent.getStringArrayListExtra("answers") as ArrayList

//
        

        for(each in 0..questionListOld.size-1){
            hashMap[questionListOld[each]]=answerListOld[each]
        }

        val entries = hashMap.entries.shuffled()

        timer()

        for (each in entries) {
            questionList.add(each.key)
            answerList.add(each.value)
        }
        remaining = 15
        updateAnswerAndQuestion()

        binding.et1.doOnTextChanged { text, start, before, count ->
            if(!binding.et1.text.toString().isEmpty()){
                binding.et2.requestFocus()
            }
            if(!binding.et2.text.toString().isEmpty()){
                binding.et2.requestFocus()
            }
        }

        binding.et2.doOnTextChanged { text, start, before, count ->
            if(!binding.et2.text.toString().isEmpty()){
                binding.et3.requestFocus()
            }
            else{
                binding.et1.requestFocus()
            }

        }
        binding.et3.doOnTextChanged { text, start, before, count ->
            if(!binding.et3.text.toString().isEmpty()){
                binding.et4.requestFocus()
            }
            else{
                binding.et2.requestFocus()
            }

        }
        binding.et4.doOnTextChanged { text, start, before, count ->
            if(!binding.et4.text.toString().isEmpty()){
                binding.et5.requestFocus()
            }
            else{
                binding.et3.requestFocus()
            }

        }
        binding.et5.doOnTextChanged { text, start, before, count ->
            if(!binding.et5.text.toString().isEmpty()){
                binding.et6.requestFocus()
            }
            else{
                binding.et4.requestFocus()
            }

        }
        binding.et6.doOnTextChanged { text, start, before, count ->
            if (binding.et6.text.toString().isEmpty()) {
                binding.et5.requestFocus()
            }
        }

        binding.nextbtn.setOnClickListener {
            answer = binding.et1.text.toString() +
                    binding.et2.text.toString() +
                    binding.et3.text.toString() +
                    binding.et4.text.toString() +
                    binding.et5.text.toString() +
                    binding.et6.text.toString()
            if (remaining == 1) {
                if(currentAnswer.equals(answer, ignoreCase = true)) {
                    correct++
                    binding.solvedtv.setText(correct.toString())
                    var intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("solved", correct.toString())
                    startActivity(intent)
                }
                else{
                    var intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("solved", correct.toString())
                    startActivity(intent)
                }
            } else {
                println(currentAnswer.equals(answer, ignoreCase = true))
                println(answer)
                if (currentAnswer.equals(answer, ignoreCase = true)) {
                    countDownTimer.cancel()
                    binding.lottie.visibility=View.VISIBLE
                    binding.lottie.setAnimation("correct.json")
                    binding.lottie.playAnimation()
                    var handler = Handler()
                    handler.postDelayed({
                        binding.lottie.visibility=View.GONE
                        remaining -= 1
                        correct++
                        binding.solvedtv.setText(correct.toString())
                        countDownTimer.cancel()
                        updateAnswerAndQuestion()
                        timer()
                    },2000)

                } else {
                    countDownTimer.cancel()
                    binding.lottie.visibility=View.VISIBLE
                    binding.lottie.setAnimation("wrong.json")
                    binding.lottie.playAnimation()
                    var handler=Handler()
                    handler.postDelayed({
                        // Hide the Lottie animation view
                        binding.lottie.visibility=View.GONE
                        remaining -= 1
                        countDownTimer.cancel()
                        timer()
                        updateAnswerAndQuestion()
                    }, 2000)

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

        println(currentAnswer.length)
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
                if(remaining==1){
                    var intent = Intent(this@GameActivity, ResultActivity::class.java)
                    intent.putExtra("solved", correct.toString())
                    startActivity(intent)
                }
                else {
                    binding.lottie.visibility=View.VISIBLE
                    binding.lottie.setAnimation("wrong.json")
                    binding.lottie.playAnimation()
                    var handler=Handler()
                    handler.postDelayed({
                        // Hide the Lottie animation view
                        binding.lottie.visibility=View.GONE
                        remaining -= 1
                        timer()
                        updateAnswerAndQuestion()
                    }, 2000)
                }
            }
        }
        countDownTimer.start()

    }
}