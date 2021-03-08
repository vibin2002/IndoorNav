package com.example.android.bleservertty.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.bleservertty.R
import com.example.android.bleservertty.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var dept: String
    private lateinit var year: String
    private lateinit var semester: String
    private lateinit var designation: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppdefaultTheme)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.signupGotologinpage.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.suspinnerdept.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                dept = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                semester = parent.getItemAtPosition(0).toString()
            }
        }
        binding.suyearspinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                year = parent.getItemAtPosition(0).toString()
            }
        }
        binding.susemspinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                semester = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                semester = parent.getItemAtPosition(0).toString()
            }
        }

        binding.regBtn.setOnClickListener {
            username = binding.signupUsername.editText?.text.toString()
            password = binding.signupPassword.editText?.text.toString()
            email = binding.signupEmail.editText?.text.toString()

            if (username.length <= 4 || email.length <= 4 || password.length <= 4) {
                Toast.makeText(this, "Fill all fields properly", Toast.LENGTH_SHORT).show()
            }
            val data = getDataForFireStore(designation)
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            if (designation == "Faculty") {
                firestore.collection("Faculty").document(username).set(data)
            } else if (designation == "Student") {
                firestore.collection("Student").document(username).set(data)
            }
        }

    }

    private fun getDataForFireStore(designation: String): Map<String, String> {
        return if (designation == "Faculty") {
            mapOf(
                "Email" to email,
                "Department" to dept,
            )
        } else {
            mapOf(
                "Email" to email,
                "Department" to dept,
                "Year" to year,
                "Semester" to semester
            )
        }
    }

    fun choiceforUser(view: View?) {
        val selectedId: Int = binding.radiogrpDesignation.getCheckedRadioButtonId()
        var radioButton: RadioButton = findViewById<View>(selectedId) as RadioButton
        if (selectedId == -1) {
            Toast.makeText(this@SignupActivity, "Nothing selected", Toast.LENGTH_SHORT).show()
        } else {
            designation = radioButton.getText().toString()
        }
    }
}