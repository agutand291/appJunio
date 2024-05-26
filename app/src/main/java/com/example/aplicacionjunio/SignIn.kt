package com.example.aplicacionjunio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var sendButton: Button
    private lateinit var signUp: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        initComponents()
        initListeners()
        checkUser()
    }

    private fun initComponents() {
        emailInput = findViewById(R.id.emailText)
        passwordInput = findViewById(R.id.passText)
        sendButton = findViewById(R.id.LoginButton)
        progressBar = findViewById(R.id.progressBar)
        signUp = findViewById(R.id.signupText)
        emailLayout = findViewById(R.id.email)
        passwordLayout = findViewById(R.id.password)
    }

    private fun initListeners() {
        sendButton.setOnClickListener { check() }
        signUp.setOnClickListener { goSignup() }
    }

    private fun check() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isBlank() || password.isBlank()) {
            showErrorMessage("Introduzca contraseña y correo")
            return
        }

        progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.INVISIBLE
                if (task.isSuccessful) {
                    goMain()
                } else {
                    showErrorMessage("Autenticación fallida: ${task.exception?.message}")
                    animateErrorFields()
                }
            }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun animateErrorFields() {
        emailLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        passwordLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
    }

    private fun checkUser() {
        if (firebaseAuth.currentUser != null) {
            goMain()
        }
    }

    private fun goMain() {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }

    private fun goSignup() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}
