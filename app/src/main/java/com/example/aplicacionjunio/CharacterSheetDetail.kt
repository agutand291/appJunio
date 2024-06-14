package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CharacterSheetDetail : AppCompatActivity() {

    private lateinit var character: Character
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var charName: TextView
    private lateinit var charClass: TextView
    private lateinit var charRace: TextView
    private lateinit var charLevel: TextView
    private lateinit var charStrength: EditText
    private lateinit var charDexterity: EditText
    private lateinit var charConstitution: EditText
    private lateinit var charIntelligence: EditText
    private lateinit var charWisdom: EditText
    private lateinit var charCharisma: EditText
    private lateinit var objectRecyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charactersheet)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        character = intent?.getParcelableExtra("character") ?: return

        initComponents()
        setChar()
        setupRecyclerView()
        fetchItems()
    }

    private fun initComponents() {
        charName = findViewById(R.id.charName)
        charClass = findViewById(R.id.charClass)
        charRace = findViewById(R.id.charRace)
        charLevel = findViewById(R.id.charLevel)
        charStrength = findViewById(R.id.charStrength)
        charDexterity = findViewById(R.id.charDexterity)
        charConstitution = findViewById(R.id.charConstitution)
        charIntelligence = findViewById(R.id.charIntelligence)
        charWisdom = findViewById(R.id.charWisdom)
        charCharisma = findViewById(R.id.charCharisma)
        objectRecyclerView = findViewById(R.id.objects)
    }

    private fun setChar() {
        charName.text = character.name
        charClass.text = character.characterClass
        charRace.text = character.race
        charLevel.text = character.level.toString()

        charStrength.setText(character.strength.toString())
        charDexterity.setText(character.dexterity.toString())
        charConstitution.setText(character.constitution.toString())
        charIntelligence.setText(character.intelligence.toString())
        charWisdom.setText(character.wisdom.toString())
        charCharisma.setText(character.charisma.toString())
    }

    private fun setupRecyclerView() {
        objectRecyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(emptyList(), showCost = false, onItemClick = { item -> }, onItemLongClick = { item -> false })
        objectRecyclerView.adapter = itemAdapter
    }

    private fun fetchItems() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val characterId = character.id
            val objectRecyclerViewRef = db.collection("users").document(userId)
                .collection("characters").document(characterId)
                .collection("items")

            objectRecyclerViewRef.get()
                .addOnSuccessListener { querySnapshot ->
                    val objectList = mutableListOf<Item>()
                    for (document in querySnapshot.documents) {
                        val name = document.getString("name")
                        val value = document.getString("value")?.toInt() ?: 0
                        val item = Item(name, value)
                        objectList.add(item)
                    }
                    itemAdapter.updateData(objectList)
                }
                .addOnFailureListener { exception -> }
        }
    }
}
