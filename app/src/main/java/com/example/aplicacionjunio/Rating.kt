package com.example.aplicacionjunio

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Rating : AppCompatActivity() {

    private lateinit var backButton: FloatingActionButton
    private lateinit var ratingButton: Button
    private lateinit var feedback: AppCompatEditText
    private lateinit var ratingCheck: CheckBox
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.backButton)
        ratingButton = findViewById(R.id.ratingButton)
        ratingBar = findViewById(R.id.ratingBar)
        feedback = findViewById(R.id.ratingText)
        ratingCheck = findViewById(R.id.ratingCheck)
    }

    private fun initListeners() {
        backButton.setOnClickListener { goBack() }
        ratingButton.setOnClickListener { sendReview() }
    }

    private fun goBack() {
        finish()
    }

    private fun sendReview() {
        val rating = ratingBar.rating.toInt()
        val feedback = feedback.text.toString()
        val addEmail = ratingCheck.isChecked

        animateRatingBar(rating)
    }

    private fun animateRatingBar(newRating: Int) {
        // Calculate the increment for animation duration
        val increment = 100L

        ratingBar.rating = 0f

        for (i in 1..newRating) {
            val delay = i * increment
            ratingBar.postDelayed({
                ratingBar.rating = i.toFloat()
            }, delay)
        }
    }

}
