package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AddListingStepsActivity : AppCompatActivity() {

    private lateinit var btnGetStarted : Button
    private lateinit var btnBack : ImageButton

    private val firebaseHelper = FirebaseHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing_steps)

        btnGetStarted = findViewById(R.id.btnGetStarted)
        btnBack = findViewById(R.id.btnBack)

        btnGetStarted.setOnClickListener{
            if(firebaseHelper.isUserLoggedIn()){
                val intent = Intent(this, AddListingActivity::class.java)
                startActivity(intent)
            }else{
                val bottomSheetFragment = LoginBottomSheetFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }

        btnBack.setOnClickListener{
            onBackPressed()
        }
    }
}