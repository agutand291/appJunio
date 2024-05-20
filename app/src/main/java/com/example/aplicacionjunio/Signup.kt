package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Signup : AppCompatActivity() {

    private lateinit var name: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var user: TextInputEditText
    private lateinit var radioHombre: RadioButton
    private lateinit var radioMujer: RadioButton
    private lateinit var radioOtro: RadioButton
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        name = findViewById(R.id.signupName)
        surname = findViewById(R.id.signupSurname)
        email = findViewById(R.id.signupEmail)
        password = findViewById(R.id.password)
        user = findViewById(R.id.signupUser)
        radioHombre = findViewById(R.id.radioHombre)
        radioMujer = findViewById(R.id.radioMujer)
        radioOtro = findViewById(R.id.radioOtro)
        sendButton = findViewById(R.id.button)
    }

    private fun initListeners() {
        sendButton.setOnClickListener { check() }
    }

    private fun check() {

        var isEmpty = false

        if(name.text.isNullOrBlank())
            isEmpty = true
            email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

        if(email.text.isNullOrBlank())
            isEmpty = true
            email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

        if(user.text.isNullOrBlank())
            isEmpty = true
            email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

        if(!radioHombre.isChecked || !radioMujer.isChecked || !radioOtro.isChecked)
            isEmpty = true

        if(!isEmpty) {
            goMain()
        }
    }

    private fun goMain() {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }
}