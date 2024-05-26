package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Settings : AppCompatActivity() {

    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        initComponents()
        iniListeners()
    }

    private fun initComponents() {
        logout = findViewById(R.id.logOutButton)
    }

    private fun iniListeners() {
        logout.setOnClickListener { logout() }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, SignIn::class.java))
        finish()
    }

}