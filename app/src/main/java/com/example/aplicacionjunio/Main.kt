package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main : AppCompatActivity() {

    private lateinit var settingsButton: FloatingActionButton
    private lateinit var charSheetButton: ImageButton
    private lateinit var addItem: ImageButton
    private lateinit var session: ImageButton
    private lateinit var guideButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        settingsButton = findViewById(R.id.settings)
        charSheetButton = findViewById(R.id.charactersheets)
        addItem = findViewById(R.id.addItem)
        session = findViewById(R.id.session)
        guideButton = findViewById(R.id.guideButtons)
    }

    private fun initListeners() {
        charSheetButton.setOnClickListener { goCharacterSheet() }
        settingsButton.setOnClickListener { goSettings() }
        addItem.setOnClickListener { goNewCharacter() }
        session.setOnClickListener { goSession() }
        guideButton.setOnClickListener { goManual() }
    }
    private fun goCharacterSheet() {
        startActivity(Intent(this, CharacterSheet::class.java))
    }
    private fun goNewCharacter() {
        startActivity(Intent(this, CreateCharacter::class.java))
    }

    private fun goSession() {
        startActivity(Intent(this, DateSelect::class.java))
    }

    private fun goSettings() {
        startActivity(Intent(this, Settings::class.java))
    }

    private fun goManual() {
        startActivity(Intent(this, Manuales::class.java))
    }
}