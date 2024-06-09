package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateCharacter : AppCompatActivity() {

    private lateinit var characterName: TextInputEditText
    private lateinit var characterClass: AppCompatAutoCompleteTextView
    private lateinit var characterRaceSpinner: Spinner
    private lateinit var strength: NumberPicker
    private lateinit var dexterity: NumberPicker
    private lateinit var constitution: NumberPicker
    private lateinit var intelligence: NumberPicker
    private lateinit var wisdom: NumberPicker
    private lateinit var charisma: NumberPicker
    private lateinit var level: NumberPicker
    private lateinit var saveButton: Button

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sheetcreator)

        initComponents()
        fetchRaces()
        fetchClasses()
        initListeners()
    }

    private fun initComponents() {
        characterName = findViewById(R.id.character_name)
        characterClass = findViewById(R.id.character_class)
        characterRaceSpinner = findViewById(R.id.character_race)
        strength = findViewById(R.id.strength)
        dexterity = findViewById(R.id.dexterity)
        constitution = findViewById(R.id.constitution)
        intelligence = findViewById(R.id.intelligence)
        wisdom = findViewById(R.id.wisdom)
        charisma = findViewById(R.id.charisma)
        level = findViewById(R.id.level)
        saveButton = findViewById(R.id.save_button)

        listOf(strength, dexterity, constitution, intelligence, wisdom, charisma, level).forEach { configureNumberPicker(it) }

    }

    private fun configureNumberPicker(numberPicker: NumberPicker) {
        numberPicker.apply {
            minValue = 0
            maxValue = 20
            wrapSelectorWheel = false
        }
    }

    private fun fetchRaces() {
        val racesRef = firestore.collection("game").document("races")

        racesRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val races = document.data?.values?.map { it.toString() } ?: emptyList()
                setupSpinner(races)
            } else Toast.makeText(this, "No existe el documento", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            Toast.makeText(
                this,
                "Error al hacer fetch: $exception",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fetchClasses() {
        val classesRef = firestore.collection("game").document("classes")

        classesRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val classes = document.data?.values?.map { it.toString() } ?: emptyList()
                setupAutocomplete(classes)
            } else Toast.makeText(this, "No existe el documento", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            Toast.makeText(
                this,
                "Error al hacer fetch: $exception",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupAutocomplete(classes: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, classes)
        characterClass.setAdapter(adapter)
    }

    private fun setupSpinner(races: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, races)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        characterRaceSpinner.adapter = adapter
    }

    private fun initListeners() {
        saveButton.setOnClickListener { saveCharacter() }
    }

    private fun saveCharacter() {
        val name = characterName.text.toString().trim()
        val charClass = characterClass.text.toString().trim()
        val race = characterRaceSpinner.selectedItem.toString()
        val level = level.value
        val str = strength.value
        val dex = dexterity.value
        val con = constitution.value
        val intel = intelligence.value
        val wis = wisdom.value
        val cha = charisma.value

        if (name.isEmpty() || charClass.isEmpty() || race.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val character = mapOf(
            "name" to name,
            "class" to charClass,
            "race" to race,
            "level" to level,
            "strength" to str,
            "dexterity" to dex,
            "constitution" to con,
            "intelligence" to intel,
            "wisdom" to wis,
            "charisma" to cha
        )

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userDocRef = firestore.collection("users").document(currentUser.uid)
            userDocRef.collection("characters").add(character)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Personaje Guardado",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al guardar el personaje: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
    }
}