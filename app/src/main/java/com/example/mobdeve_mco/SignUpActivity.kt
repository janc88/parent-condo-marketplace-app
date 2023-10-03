package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnLogin : Button
    private lateinit var ivClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnLogin = findViewById<Button>(R.id.btnLogin);
        btnLogin.paintFlags = btnLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG


        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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