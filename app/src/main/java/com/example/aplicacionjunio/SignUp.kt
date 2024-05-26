package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var name: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var repeatPassword: TextInputEditText
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
        password = findViewById(R.id.signupPassword)
        repeatPassword = findViewById(R.id.signupPasswordRepeat)
        user = findViewById(R.id.signupUser)
        radioHombre = findViewById(R.id.radioHombre)
        radioMujer = findViewById(R.id.radioMujer)
        radioOtro = findViewById(R.id.radioOtro)
        sendButton = findViewById(R.id.button)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun initListeners() {
        sendButton.setOnClickListener { check() }
    }

    private fun check() {

        var isValid = true

        fun validateField(field: TextInputEditText): Boolean {
            return if (field.text.toString().isBlank()) {
                field.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                false
            } else {
                true
            }
        }

        isValid = validateField(name) && isValid
        isValid = validateField(surname) && isValid
        isValid = validateField(email) && isValid
        isValid = validateField(user) && isValid
        isValid = validateField(password) && isValid
        isValid = validateField(repeatPassword) && isValid

        if (!radioHombre.isChecked && !radioMujer.isChecked && !radioOtro.isChecked) {
            isValid = false
        }

        if (password.text.toString() != repeatPassword.text.toString()) {
            isValid = false
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }

        if (isValid) {
            firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveUserData(
                            name.text.toString(),
                            surname.text.toString(),
                            email.text.toString(),
                            user.text.toString(),
                            if(radioHombre.isChecked) "Hombre" else if (radioMujer.isChecked) "Mujer" else "Otro"
                        )
                        goLogin()
                    } else Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(name: String, surname: String, email: String, username: String, gender: String) {
        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "username" to username,
            "gender" to gender
        )

        db.collection("users")
            .document(firebaseAuth.currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Cuenta creada correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goLogin() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}
