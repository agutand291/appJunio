package com.example.aplicacionjunio

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DateSelect : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var saveButton: Button
    private var selectedDate: Long = 0

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.session)

        initComponents()
        initListeners()
        fetchDate()
    }

    private fun initComponents() {
        calendarView = findViewById(R.id.calendarView)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun initListeners() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        saveButton.setOnClickListener {
            if (selectedDate != 0L) {
                saveDate(selectedDate)
            } else Toast.makeText(this, "Selecciona una fecha", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDate() {
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            firestore.collection("users").document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("nextSessionDate")) {
                        val date = document.getLong("nextSessionDate")
                        date?.let {
                            calendarView.date = it
                            selectedDate = it
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        }
                    } else {
                        val currentDate = Calendar.getInstance().timeInMillis
                        calendarView.date = currentDate
                        selectedDate = currentDate
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al hacer fetch: ${e.message}", Toast.LENGTH_SHORT).show()
                    val currentDate = Calendar.getInstance().timeInMillis
                    calendarView.date = currentDate
                    selectedDate = currentDate
                }
        }
    }

    private fun saveDate(date: Long) {
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            val dateMap = hashMapOf("nextSessionDate" to date)
            firestore.collection("users").document(it)
                .set(dateMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Fecha guardada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al guardar la fecha: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
