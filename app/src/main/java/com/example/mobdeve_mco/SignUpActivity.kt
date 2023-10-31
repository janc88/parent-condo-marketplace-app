package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


class SignUpActivity : AppCompatActivity() {
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var etFirstName : TextInputEditText
    private lateinit var etLastName : TextInputEditText
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var etBio : EditText
    private lateinit var ivProfilePic : ImageView
    private lateinit var cvProfilePic : CardView
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
        etBio = findViewById(R.id.etBio);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        cvProfilePic = findViewById(R.id.cvProfilePic);

        cvProfilePic.setOnClickListener{

        }

        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val bio = etBio.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            userViewModel.updateUser(user)

                            val db = Firebase.firestore

                            val usersCollection = db.collection("users")

                            val newUser = User(
                                user!!.uid,
                                firstName,
                                lastName,
                                email,
                                Date(),
                                bio,
                                ""
                            )

                            usersCollection.document(user!!.uid)
                                .set(newUser)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("userLoggedIn", true)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to store user data in Firestore: $e", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
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