package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class LoginActivity : AppCompatActivity() {
    private lateinit var btnSignUp : Button
    private lateinit var ivClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        btnSignUp = findViewById<Button>(R.id.btnSignUp);
        btnSignUp.paintFlags = btnSignUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        ivClose = findViewById(R.id.ivClose)
        ivClose.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("accountFragment", "LoggedOutFragment")
            startActivity(intent)
        }
    }


}