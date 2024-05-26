package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateCharacter : AppCompatActivity() {

    private lateinit var characterName: TextInputEditText
    private lateinit var characterClass: TextInputEditText
    private lateinit var characterRaceSpinner: Spinner
    private lateinit var strength: TextInputEditText
    private lateinit var dexterity: TextInputEditText
    private lateinit var constitution: TextInputEditText
    private lateinit var intelligence: TextInputEditText
    private lateinit var wisdom: TextInputEditText
    private lateinit var charisma: TextInputEditText
    private lateinit var saveButton: Button

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sheetcreator)

        initComponents()
        fetchRaces()
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
        saveButton = findViewById(R.id.save_button)
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
        val str = strength.text.toString().trim()
        val dex = dexterity.text.toString().trim()
        val con = constitution.text.toString().trim()
        val intel = intelligence.text.toString().trim()
        val wis = wisdom.text.toString().trim()
        val cha = charisma.text.toString().trim()

        if (name.isEmpty() || charClass.isEmpty() || race.isEmpty() || str.isEmpty() || dex.isEmpty() ||
            con.isEmpty() || intel.isEmpty() || wis.isEmpty() || cha.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val character = mapOf(
            "name" to name,
            "class" to charClass,
            "race" to race,
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