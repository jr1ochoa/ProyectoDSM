package com.example.veterinary

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createDoctorTextView: TextView = findViewById(R.id.createDoctorTextView)
        createDoctorTextView.setOnClickListener {
            val intent = Intent(this, CreateDoctor::class.java)
            startActivity(intent)
        }
    }
}
