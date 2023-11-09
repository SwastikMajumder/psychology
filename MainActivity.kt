package com.dm.tutorialemptyactivity422

import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.widget.Button
import android.widget.TextView
import android.content.Context
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var waitTextView: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private lateinit var startButton: Button
    private lateinit var shareButton: Button

    private var sequence: List<Int> = emptyList()
    private var currentIndex = 0
    private var globalString: String = ""

    private var startTimeMillis: Long = 0
    private var elapsedTimeMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waitTextView = findViewById(R.id.waitTextView)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        startButton = findViewById(R.id.startButton)
	shareButton = findViewById<Button>(R.id.shareButton)

        // Disable the "Yes" and "No" buttons initially
        yesButton.isEnabled = false
        noButton.isEnabled = false
	shareButton.isEnabled = false
        // Set a click listener for the "Start" button
        startButton.setOnClickListener {
            sequence = generateRandomSequence(1, 6, 0, 9)
            currentIndex = 0
	    startButton.isEnabled = false
            displaySequenceDigit()
        }

        yesButton.setOnClickListener {
            stopAndCalculateTime("yes")
        }

        noButton.setOnClickListener {
            stopAndCalculateTime("no")
        }
	shareButton.setOnClickListener {
    		val sharingIntent = Intent(Intent.ACTION_SEND)
  		sharingIntent.type = "text/plain"
    		sharingIntent.putExtra(Intent.EXTRA_TEXT, globalString)
    		startActivity(Intent.createChooser(sharingIntent, "Share via"))
	}

    }

    private fun generateRandomSequence(minLength: Int, maxLength: Int, minValue: Int, maxValue: Int): List<Int> {
        if (minLength > maxLength || minValue > maxValue) {
            // Handle invalid input
            return emptyList()
        }

        val sequenceLength = (minLength..maxLength).random()
        val availableNumbers = (minValue..maxValue).toMutableList()
        val sequence = mutableListOf<Int>()

        while (sequence.size < sequenceLength && availableNumbers.isNotEmpty()) {
            val randomIndex = (0 until availableNumbers.size).random()
            val selectedNumber = availableNumbers[randomIndex]
            sequence.add(selectedNumber)
            availableNumbers.removeAt(randomIndex)
        }

        return sequence
    }

    private fun stopAndCalculateTime(displayText: String) {
        val currentTimeMillis = System.currentTimeMillis()
        elapsedTimeMillis = currentTimeMillis - startTimeMillis
        val mergedString = "${waitTextView.text}, Elapsed Time: $elapsedTimeMillis ms, Sequence: ${sequence.joinToString(", ")}, Input: $displayText"
	globalString += mergedString + "\n"
	yesButton.isEnabled = false
        noButton.isEnabled = false
	shareButton.isEnabled = true
	startButton.isEnabled = true
	waitTextView.text = "press start"
    }

    private fun generateNewSequence(excludedNumbers: List<Int>): List<Int> {
        val availableNumbers = (0..9).toMutableList()
        availableNumbers.removeAll(excludedNumbers)
        val sequenceLength = (1..6).random()
        val sequence = mutableListOf<Int>()

        while (sequence.size < sequenceLength && availableNumbers.isNotEmpty()) {
            val randomIndex = (0 until availableNumbers.size).random()
            val selectedNumber = availableNumbers[randomIndex]
            sequence.add(selectedNumber)
            availableNumbers.removeAt(randomIndex)
        }

        return sequence
    }

    private fun chooseNumberFromSequences(oldSequence: List<Int>, newSequence: List<Int>): Int {
        val choice = (0..1).random()
        val selectedNumber: Int
        if (choice == 0) {
            if (oldSequence.isNotEmpty()) {
                selectedNumber = oldSequence.random()
            } else {
                selectedNumber = newSequence.random()
            }
        } else {
            selectedNumber = newSequence.random()
        }
        return selectedNumber
    }

    private fun displaySequenceDigit() {
        if (currentIndex < sequence.size) {
            waitTextView.text = sequence[currentIndex].toString()
            currentIndex++

            // Delay for 1.2 seconds before displaying the next digit
            Handler().postDelayed({ displaySequenceDigit() }, 1200)
        } else {
            waitTextView.text = "wait"
            Handler().postDelayed({
                yesButton.isEnabled = true
                noButton.isEnabled = true
                val newSequence = generateNewSequence(sequence)
                val selectedNumber = chooseNumberFromSequences(sequence, newSequence)
                waitTextView.text = selectedNumber.toString()
                startTimeMillis = System.currentTimeMillis()
            }, 2000)
        }
    }
}
