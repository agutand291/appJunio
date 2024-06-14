package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ItemView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var characters: List<Character> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itemview)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemAdapter = ItemAdapter(emptyList(), characters,
            onItemClick = ::showChooseCharacterDialog,
            onItemLongClick = { item -> false })
        recyclerView.adapter = itemAdapter

        loadItemsFromFirestore()
        loadCharactersFromFirestore()
    }

    private fun loadItemsFromFirestore() {
        db.collection("items")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val itemList = mutableListOf<Item>()
                for (document in querySnapshot.documents) {
                    val name = document.getString("name") ?: ""
                    val cost = document.getLong("cost")?.toInt() ?: 0
                    val item = Item(name, cost)
                    itemList.add(item)
                }
                itemAdapter.updateData(itemList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error en los documentos: $exception",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun loadCharactersFromFirestore() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users")
                .document(userId)
                .collection("characters")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    characters = querySnapshot.documents.mapNotNull { document ->
                        val characterId = document.id
                        val name = document.getString("name") ?: ""
                        val race = document.getString("race") ?: ""
                        val characterClass = document.getString("characterClass") ?: ""
                        val level = document.getLong("level")?.toInt() ?: 0
                        val strength = document.getLong("strength")?.toInt() ?: 0
                        val dexterity = document.getLong("dexterity")?.toInt() ?: 0
                        val intelligence = document.getLong("intelligence")?.toInt() ?: 0
                        val wisdom = document.getLong("wisdom")?.toInt() ?: 0
                        val constitution = document.getLong("constitution")?.toInt() ?: 0
                        val charisma = document.getLong("charisma")?.toInt() ?: 0

                        Character(
                            characterId,
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
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Error al cargar los personajes: $exception",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else Toast.makeText(this, "Usuario no logeado", Toast.LENGTH_SHORT).show()
    }


    private fun showChooseCharacterDialog(item: Item) {
        val characterNames = characters.map { it.name }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Selecciona el personaje")
            .setItems(characterNames) { dialog, which ->
                val character = characters[which]
                addItemToCharacter(item, character)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun addItemToCharacter(item: Item, character: Character) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users")
                .document(userId)
                .collection("characters")
                .document(character.id)
                .collection("items")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(
                        this,
                        "Item ${item.name} added to ${character.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Error al añadir item al personaje: $exception",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else Toast.makeText(this, "User no logeado", Toast.LENGTH_SHORT).show()
    }
}
