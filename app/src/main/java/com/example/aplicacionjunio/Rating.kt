package com.example.aplicacionjunio

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Rating : AppCompatActivity() {

    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.backButton)
    }

    private fun initListeners() {
        backButton.setOnClickListener { goBack() }
    }

    private fun goBack() {
        finish()
    }

}