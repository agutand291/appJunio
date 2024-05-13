package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main : AppCompatActivity() {

    private lateinit var ratingButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        ratingButton = findViewById(R.id.ratingButton)
    }

    private fun initListeners() {
        ratingButton.setOnClickListener { goRating() }
    }

    private fun goRating() {
        val intent = Intent(this, Rating::class.java)
        startActivity(intent)
    }
}