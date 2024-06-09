package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CharacterSheetDetail : AppCompatActivity() {

    private lateinit var charName: TextView
    private lateinit var charClass: TextView
    private lateinit var charRace: TextView
    private lateinit var charLevel: TextView
    private lateinit var strength: EditText
    private lateinit var dexterity: EditText
    private lateinit var constitution: EditText
    private lateinit var intelligence: EditText
    private lateinit var wisdom: EditText
    private lateinit var charisma: EditText
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charactersheet)

        character = intent?.getParcelableExtra("character") ?: return

        initComponents()
        setChar()
    }

    private fun initComponents() {
        charName = findViewById(R.id.charName)
        charClass = findViewById(R.id.charClass)
        charRace = findViewById(R.id.charRace)
        charLevel = findViewById(R.id.charLevel)
        strength = findViewById(R.id.charStrength)
        dexterity = findViewById(R.id.charDexterity)
        constitution = findViewById(R.id.charConstitution)
        intelligence = findViewById(R.id.charIntelligence)
        wisdom = findViewById(R.id.charWisdom)
        charisma = findViewById(R.id.charCharisma)
    }

    private fun setChar() {
        charName.text = character.name
        charClass.text = character.characterClass
        charRace.text = character.race
        charLevel.text = character.level.toString()
        strength.setText(character.strength.toString())
        dexterity.setText(character.dexterity.toString())
        constitution.setText(character.constitution.toString())
        intelligence.setText(character.intelligence.toString())
        wisdom.setText(character.wisdom.toString())
        charisma.setText(character.charisma.toString())
    }
}
