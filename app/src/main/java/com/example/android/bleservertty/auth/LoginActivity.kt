package com.example.android.bleservertty.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.bleservertty.R
import com.example.android.bleservertty.ble.DisplayDataActivity
import com.example.android.bleservertty.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, DisplayDataActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppdefaultTheme)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginNewuserbtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginLoginbtn.setOnClickListener {
            signInUser(
                binding.loginemailidTIL.editText?.text.toString(),
                binding.loginPasswordET.text.toString()
            )
        }
    }

    private fun signInUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this, "Auth Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DisplayDataActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
        }
    }
}