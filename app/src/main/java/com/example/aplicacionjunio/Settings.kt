package com.example.aplicacionjunio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var auth: FirebaseAuth
        private lateinit var db: FirebaseFirestore
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            initComponents()
            initListeners()
            initFragments()
        }

        private fun initComponents() {
            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()
        }

        private fun initListeners() {

        }

        private fun initFragments() {
            val logoutButton: Preference? = findPreference("logout_button")
            // val darkModeSwitch: SwitchPreferenceCompat? = findPreference("darkmode_switch")
            val changeUserName: EditTextPreference? = findPreference("change_username")
            val changeEmail: EditTextPreference? = findPreference("change_email")
            // val changePassword: EditTextPreference? = findPreference("change_password")
            val rating: Preference? = findPreference("rating")

            /*
            darkModeSwitch?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            */
            logoutButton?.setOnPreferenceClickListener {
                logout()
                true
            }

            changeUserName?.setOnPreferenceChangeListener { _, newValue ->
                updateUsername(newValue as String)
                true
            }

            changeEmail?.setOnPreferenceChangeListener { _, newValue ->
                updateEmail(newValue as String)
                true
            }

            /*
            changePassword?.setOnPreferenceChangeListener { _, newValue ->
                updatePassword(newValue as String)
                true
            }
            */

            rating?.setOnPreferenceClickListener {
                goRating()
                true
            }

        }

        // Comprueba que no haya usuarios duplicados y luego actualiza el usuario
        private fun updateUsername(username: String) {
            val user = auth.currentUser

            db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        Toast.makeText(requireContext(), "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show()
                    } else {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(requireContext(), "Usuario actualizado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(requireContext(), "Error al actualizar el usuario", Toast.LENGTH_SHORT).show()
                                    Log.e("Actualizar usuario", "Erroer al actualizar el usuario", task.exception)
                                }
                            } ?: run {
                            Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error al verificar el nombre de usuario", Toast.LENGTH_SHORT).show()
                    Log.e("Comprobar usuario", "Error al comprobar usuario", e)
                }
        }


        // Comprueba que no haya correos electronicos duplicados y luego actualiza el del usuario
        private fun updateEmail(email: String) {
            val user = auth.currentUser

            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) Toast.makeText(requireContext(), "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show() else user?.updateEmail(email)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "Correo electrónico actualizado", Toast.LENGTH_SHORT).show()

                                val userDocRef = db.collection("users").document(user.uid)
                                userDocRef.update("email", email)
                                    .addOnSuccessListener {
                                        Log.d("Email actualizado", "Email actualizado")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Error al actualizar el correo", "Error al actualizar el correo", e)
                                    }
                            } else {
                                Toast.makeText(requireContext(), "Error al actualizar el correo electrónico", Toast.LENGTH_SHORT).show()
                                Log.e("Error al actualizar el correo", "Error al actualizar el correo", task.exception)
                            }
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error al verificar el correo electrónico", Toast.LENGTH_SHORT).show()
                    Log.e("Error comprobando el correo", "Error comprobando el correo", e)
                }
        }

        private fun logout() {
            FirebaseAuth.getInstance().signOut()

            Toast.makeText(context, "Desconectado", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, SignIn::class.java)
            startActivity(intent)
            activity?.finish()
        }

        private fun goRating() {
            startActivity(Intent(activity, Rating::class.java))
        }
    }
}
