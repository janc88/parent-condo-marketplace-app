package com.example.mobdeve_mco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class AddListingStepsActivity : AppCompatActivity() {

    private lateinit var btnGetStarted : Button
    private lateinit var btnBack : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing_steps)

        btnGetStarted = findViewById(R.id.btnGetStarted)
        btnBack = findViewById(R.id.btnBack)

        btnGetStarted.setOnClickListener{
            val intent = Intent(this, AddListingActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener{
            onBackPressed()
        }
    }
}