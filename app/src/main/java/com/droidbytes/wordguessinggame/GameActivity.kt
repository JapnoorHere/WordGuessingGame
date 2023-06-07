package com.droidbytes.wordguessinggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import com.droidbytes.wordguessinggame.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    var questionCount: Int = 0
    var answer: String = ""
    var solved: Int = -1
    var currentAnswer: String = ""
    lateinit var hintList: ArrayList<String>
    lateinit var answerList: ArrayList<String>
    lateinit var countDownTimer: CountDownTimer
    lateinit var hashMap: HashMap<String, String>
    var remaining: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hintList = ArrayList()
        answerList = ArrayList()

        hashMap = HashMap<String, String>()
//        hashMap.put("A rhythmic movement to music", "Dance")
//        hashMap.put("An expression of happiness or joy", "Smile")
//        hashMap.put("A sandy shore by the sea or a lake", "Beach")
//        hashMap.put("The state of resting or slumbering", "sleep")
//        hashMap.put(" A written or printed work of literature or reference", "Book")
        val entries = hashMap.entries.shuffled()

        timer()

        for (each in entries) {
            hintList.add(each.key)
            answerList.add(each.value)
        }
        remaining = hintList.size
        updateAnswerAndQuestion()


        binding.nextbtn.setOnClickListener {
            if (remaining == 1) {
                var intent= Intent(this@GameActivity,ResultActivity::class.java)
                intent.putExtra("solved",solved.toString())
                startActivity(intent)
            } else {
                answer = binding.et1.text.toString() +
                        binding.et2.text.toString() +
                        binding.et3.text.toString() +
                        binding.et4.text.toString() +
                        binding.et5.text.toString() +
                        binding.et6.text.toString()
                println(hashMap.get(binding.hintTv.text.toString()))
                println(
                    hashMap.get(binding.hintTv.text.toString()).equals(answer, ignoreCase = true)
                )
                println(answer)
                if (hashMap.get(binding.hintTv.text.toString()).equals(answer, ignoreCase = true)) {
                    remaining -= 1
                    solved++
                    binding.solvedtv.setText(solved.toString())
                    countDownTimer.cancel()
                    updateAnswerAndQuestion()
                    timer()
                } else {
                    remaining -= 1
                    countDownTimer.cancel()
                    timer()
                    updateAnswerAndQuestion()
                }
            }
        }

    }

    fun updateAnswerAndQuestion() {
        binding.remainingTv.text = remaining.toString()
        binding.hintTv.text = hintList[questionCount]
        currentAnswer = answerList[questionCount]
        questionCount += 1
        if (questionCount >= hintList.size) {
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
                updateAnswerAndQuestion()
                timer()
            }
        }
        countDownTimer.start()

    }
}