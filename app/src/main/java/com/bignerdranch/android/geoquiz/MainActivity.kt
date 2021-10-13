@file:Suppress("unused")
@file:SuppressLint("NewApi")

package com.bignerdranch.android.geoquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var questionTextView: TextView
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)

        val questionTextResId = questionBank[currentIndex].textResID
        questionTextView.setText(questionTextResId)

        backButton.setOnClickListener { view: View ->
            if (currentIndex == 0) {
                Snackbar.make(
                    view, "You are At the start Of the quiz",
                    BaseTransientBottomBar.LENGTH_SHORT
                ).setTextColor(resources.getColor(R.color.childtext, this.theme)).setBackgroundTint(
                    resources.getColor(R.color.darkbg, this.theme)
                ).show()
            } else {
                currentIndex = (currentIndex - 1)
                if (currentIndex == 0) {
                    backButton.visibility = View.GONE
                }
                updateQuestion()
            }
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            if (currentIndex == 0) {
                backButton.visibility = View.GONE
            }else {
                backButton.visibility = View.VISIBLE
            }
        }

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true, view)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false, view)
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() called")
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResID
        questionTextView.setText(questionTextResId)
        enableAnswering()
    }
    private fun disableAnswering(){
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }
    private fun enableAnswering(){
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }
    private fun checkAnswer(userAns: Boolean, view: View) {
        disableAnswering()
        val correctAnswer = questionBank[currentIndex].answer
        val backgroundColor = resources.getColor(R.color.darkgreback,theme) // background color of buttons
        val greenColor = resources.getColor(R.color.greenlight,theme) // green color for true answer
        val redColor = resources.getColor(R.color.lightred,theme) // red color for false answer

        if(userAns == correctAnswer){
            if(userAns){trueButton.backgroundTintList = null}
        }


        val messageResId = if (userAns == correctAnswer) {R.string.correct_tst} else {R.string.incorrect_tst}
        val colorId = if (userAns == correctAnswer) {resources.getColor(R.color.greenlight, this.theme)} else {resources.getColor(R.color.lightred, this.theme)}
        Snackbar.make(view, messageResId,BaseTransientBottomBar.LENGTH_SHORT).setTextColor(colorId).setBackgroundTint(resources.getColor(R.color.darkbg, this.theme)).show()
    }
}