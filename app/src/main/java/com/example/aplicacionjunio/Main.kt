package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main : AppCompatActivity() {

    private lateinit var settingsButton: FloatingActionButton
    private lateinit var charSheetButton: ImageButton
    private lateinit var addItem: ImageButton
    private lateinit var session: ImageButton
    private lateinit var guideButton: Button
    private lateinit var itemButton: Button
    private lateinit var equipButton: Button
    private lateinit var classButton: Button
    private lateinit var raceButton: Button
    private lateinit var magicButton: Button
    private lateinit var abilityButton: Button
    private lateinit var monsterButton: Button

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
        itemButton = findViewById(R.id.itemButton)
        equipButton = findViewById(R.id.equipButton)
        classButton = findViewById(R.id.classButton)
        raceButton = findViewById(R.id.raceButton)
        magicButton = findViewById(R.id.spellsButton)
        monsterButton = findViewById(R.id.monstersButton)
    }

    private fun initListeners() {
        charSheetButton.setOnClickListener { goCharacterSheet() }
        settingsButton.setOnClickListener { goSettings() }
        addItem.setOnClickListener { goNewCharacter() }
        session.setOnClickListener { goSession() }
        guideButton.setOnClickListener { goManual() }
        itemButton.setOnClickListener { goItems() }
        equipButton.setOnClickListener { soon() }
        classButton.setOnClickListener { soon() }
        raceButton.setOnClickListener { soon() }
        magicButton.setOnClickListener { soon() }
        monsterButton.setOnClickListener { soon() }
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

    private fun goItems() {
        startActivity(Intent(this, ItemView::class.java))
    }

    private fun soon() {
        Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
    }
}