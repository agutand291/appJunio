package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main : AppCompatActivity() {

    private lateinit var ratingButton: FloatingActionButton
    private lateinit var settingsButton: FloatingActionButton
    private lateinit var charSheetButton: ImageButton
    private lateinit var addItem: ImageButton
    private lateinit var changeSheet: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        ratingButton = findViewById(R.id.ratingButton)
        settingsButton = findViewById(R.id.settings)
        charSheetButton = findViewById(R.id.charactersheets)
        addItem = findViewById(R.id.addItem)
        changeSheet = findViewById(R.id.changeSheets)
    }

    private fun initListeners() {
        ratingButton.setOnClickListener { goRating() }
        charSheetButton.setOnClickListener { goCharacterSheet() }
        settingsButton.setOnClickListener { goSettings() }
        addItem.setOnClickListener { goNewCharacter() }
    }
    private fun goCharacterSheet() {
        startActivity(Intent(this, CharacterSheet::class.java))
    }
    private fun goNewCharacter() {
        startActivity(Intent(this, CreateCharacter::class.java))
    }
    private fun goRating() {
        startActivity(Intent(this, Rating::class.java))
    }
    private fun goSettings() {
        startActivity(Intent(this, Settings::class.java))
    }
}