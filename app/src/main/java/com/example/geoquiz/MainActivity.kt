package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"on create")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG,"Got a QuizViewModel: $quizViewModel")

        // Attach the listener to the true button
        binding.trueButton.setOnClickListener {
            disableButtons()
            checkAnswer(true)
        }

        // Attach the listener to the false button
        binding.falseButton.setOnClickListener {
            disableButtons()
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            enableButtons()
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.prevButton.setOnClickListener{
            enableButtons()
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun displayResult() {
        val result = quizViewModel.computeResult()
        val message = getString(R.string.result_string, result)
        Snackbar.make(binding.questionTextView,message,Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Disable the true or false button to avoid multiple clicks
     */
    private fun disableButtons() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    /**
     * Enable the true or false button when prev or next is clicked
     */
    private fun enableButtons() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    /**
     * Update the question on the basis of index
     */
    private fun updateQuestion() {
        val questionId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionId)
    }

    /**
     * Check if user answer is correct or not.
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val messageResID = if (userAnswer == quizViewModel.currentQuestionAnswer) {
            quizViewModel.increaseCorrectAnswersCount()
            R.string.correct_toast
        }
        else
            R.string.incorrect_toast

        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
        if (quizViewModel.isLastQuestion) displayResult()
    }

}



















