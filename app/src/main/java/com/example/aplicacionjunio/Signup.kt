package com.example.aplicacionjunio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Signup : AppCompatActivity() {

    private lateinit var signupName: TextInputLayout
    private lateinit var signupSurname: TextInputLayout
    private lateinit var signupPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        initComponents()
        initListeners()
    }

    private fun initComponents() {

    }

    private fun initListeners() {

    }
}