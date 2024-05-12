package com.example.aplicacionjunio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {

    private lateinit var email: TextInputLayout
    private lateinit var emailText: TextInputEditText
    private lateinit var password: TextInputLayout
    private lateinit var passwordText: TextInputEditText
    private lateinit var sendButton: Button
    private lateinit var signUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        emailText = findViewById(R.id.emailText)
        passwordText = findViewById(R.id.passText)
        sendButton = findViewById(R.id.LoginButton)
        signUp = findViewById(R.id.signupText)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
    }

    private fun initListeners() {
        sendButton.setOnClickListener { check() }
        signUp.setOnClickListener { goSignup() }
    }

    private fun check() {

        var isEmpty = false

        if(emailText.text.isNullOrBlank()) {
            isEmpty = true
            email.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        }
        if(passwordText.text.isNullOrBlank()) {
            isEmpty = true
            password.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        }
        if(!isEmpty) {
            goMain()
        }
    }

    private fun goMain() {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    private fun goSignup() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}