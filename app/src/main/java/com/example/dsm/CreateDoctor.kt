package com.example.veterinary

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.veterinary.db.helpers.DatabaseHelper

class CreateDocCreateDoctortor : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_doctor)

        val nameEditText: EditText = findViewById(R.id.editTextDoctorName)
        val lastNameEditText: EditText = findViewById(R.id.editTextDoctorLastName)
        val emailEditText: EditText = findViewById(R.id.editTextDoctorEmail)
        val createButton: Button = findViewById(R.id.buttonCreateDoctor)

        createButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = generateRandomPassword(8)

            val dbHelper = DatabaseHelper(this)
            val isInserted = dbHelper.addUser(name, lastName, "doctor", email, password)

            if (isInserted) {
                Toast.makeText(this, "Doctor creado exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al crear doctor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateRandomPassword(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
