package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var etFirstName : TextInputEditText
    private lateinit var etLastName : TextInputEditText
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var ivClose: ImageView

    private lateinit var auth: FirebaseAuth

    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.paintFlags = btnLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnSignUp = findViewById(R.id.btnSignUp);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            }else if(password.isEmpty()){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            } else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                            val user = auth.currentUser
                            userViewModel.updateUser(user)

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("userLoggedIn", true)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }


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