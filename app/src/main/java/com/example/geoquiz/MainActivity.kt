package com.example.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Attach the listener to the true button
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        // Attach the listener to the false button
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    /**
     * Update the question on the basis of index
     */
    private fun updateQuestion() {
        val questionId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionId)
    }

    /**
     * Check if user answer is correct or not.
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val messageResID = if (userAnswer == questionBank[currentIndex].answer)
            R.string.correct_toast
        else
            R.string.incorrect_toast

        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }


}



















