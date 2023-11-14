package com.example.quizapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupQuestion)
        val checkBoxNullSafety = findViewById<CheckBox>(R.id.checkBoxNullSafety)
        val checkBoxExtensionFunctions = findViewById<CheckBox>(R.id.checkBoxExtensionFunctions)
        val checkBoxCoroutines = findViewById<CheckBox>(R.id.checkBoxCoroutines)
        val buttonReset = findViewById<Button>(R.id.buttonReset)

        buttonReset.setOnClickListener {
            resetSelections(radioGroup, checkBoxNullSafety, checkBoxExtensionFunctions, checkBoxCoroutines)
        }
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            showResults(
                radioGroup,
                checkBoxNullSafety,
                checkBoxExtensionFunctions,
                checkBoxCoroutines
            )
        }

    }

    private fun resetSelections(
        radioGroup: RadioGroup,
        checkBoxNullSafety: CheckBox,
        checkBoxExtensionFunctions: CheckBox,
        checkBoxCoroutines: CheckBox
    ) {
        radioGroup.clearCheck()
        checkBoxNullSafety.isChecked = false
        checkBoxExtensionFunctions.isChecked = false
        checkBoxCoroutines.isChecked = false
    }

    private fun showResults(
        radioGroup: RadioGroup,
        checkBoxNullSafety: CheckBox,
        checkBoxExtensionFunctions: CheckBox,
        checkBoxCoroutines: CheckBox
    ) {

        val isRadioAnswerCorrect = radioGroup.checkedRadioButtonId == R.id.radioButtonAndroid
        val isCheckBoxAnswerCorrect = checkBoxNullSafety.isChecked && !checkBoxExtensionFunctions.isChecked && checkBoxCoroutines.isChecked

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val currentDateAndTime = dateFormat.format(Date())

        val score = calculateScore(isRadioAnswerCorrect, isCheckBoxAnswerCorrect)

        val message = StringBuilder()
        message.append("Submitted on: $currentDateAndTime\n\n")
        message.append("Results:\n")
        message.append("Question 1: ${if (isRadioAnswerCorrect) "Correct" else "Incorrect"} (50%)\n")
        message.append("Question 2: ${if (isCheckBoxAnswerCorrect) "Correct" else "Incorrect"} (50%)\n")
        message.append("Total Score: $score%")

        AlertDialog.Builder(this)
            .setTitle("Quiz Results")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun calculateScore(isRadioAnswerCorrect: Boolean, isCheckBoxAnswerCorrect: Boolean): Int {
        var score = 0
        if (isRadioAnswerCorrect) score += 50
        if (isCheckBoxAnswerCorrect) score += 50
        return score
    }
}
