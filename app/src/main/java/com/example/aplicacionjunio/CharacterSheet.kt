package com.example.aplicacionjunio

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class CharacterSheet : AppCompatActivity(), ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var characterList = mutableListOf<Character>()
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.characterholder)

        initComponents()
        fetchCharacters()
    }

    private fun initComponents() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CharacterAdapter(characterList, this)
        recyclerView.adapter = adapter

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    private fun fetchCharacters() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val charactersRef = db.collection("users").document(userId).collection("characters")

            listenerRegistration = charactersRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Toast.makeText(this, "Error al cargar", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                characterList.clear()
                if (querySnapshot != null) for (document in querySnapshot.documents) try {
                    val name = document.getString("name") ?: ""
                    val race = document.getString("race") ?: ""
                    val characterClass = document.getString("class") ?: ""
                    val level = (document.getLong("level") ?: 0).toInt()
                    val strength = (document.getLong("strength") ?: 0).toInt()
                    val dexterity = (document.getLong("dexterity") ?: 0).toInt()
                    val intelligence = (document.getLong("intelligence") ?: 0).toInt()
                    val wisdom = (document.getLong("wisdom") ?: 0).toInt()
                    val constitution = (document.getLong("constitution") ?: 0).toInt()
                    val charisma = (document.getLong("charisma") ?: 0).toInt()

                    val character = Character(
                        name,
                        race,
                        characterClass,
                        level,
                        strength,
                        dexterity,
                        intelligence,
                        wisdom,
                        constitution,
                        charisma
                    )
                    characterList.add(character)
                } catch (e: Exception) {
                    Log.w(TAG, "Error al parsear", e)
                }
                adapter.notifyDataSetChanged()
            }
        } else Toast.makeText(this, "Usuario no conectado", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration?.remove()
    }

    override fun onItemClick(character: Character) {
        val intent = Intent(this, CharacterSheetDetail::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }

    override fun onEditClick(character: Character) {
        Toast.makeText(this, "${character.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(character: Character) {
        AlertDialog.Builder(this)
            .setTitle("Borrar Personaje")
            .setMessage("¿Estás seguro de que quieres borrar ${character.name}?")
            .setPositiveButton("Sí") { _, _ ->
                deleteCharacter(character)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteCharacter(character: Character) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val characterRef = db.collection("users").document(userId).collection("characters").document(character.name)

            Log.d(TAG, "Deleting character: ${character.name}")

            characterRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "${character.name} borrado", Toast.LENGTH_SHORT).show()
                    characterList.remove(character)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { _ ->
                    Toast.makeText(this, "Error al borrar el personaje", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuario no conectado", Toast.LENGTH_SHORT).show()
        }
    }
}
