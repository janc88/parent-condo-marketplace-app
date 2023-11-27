package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date


class SignUpActivity : AppCompatActivity() {
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var etFirstName : TextInputEditText
    private lateinit var etLastName : TextInputEditText
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var etContactNum : TextInputEditText
    private lateinit var etBio : EditText
    private lateinit var ivProfilePic : ImageView
    private lateinit var cvProfilePic : CardView
    private lateinit var ivClose: ImageView

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val userViewModel by viewModels<UserViewModel>()

    private val GALLERY_REQUEST_CODE = 1001
    private var imageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        bindViews()
        setListeners()

    }

    private fun bindViews(){
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.paintFlags = btnLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnSignUp = findViewById(R.id.btnSignUp);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etContactNum = findViewById(R.id.etContactNum);
        etBio = findViewById(R.id.etBio);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        cvProfilePic = findViewById(R.id.cvProfilePic);
        ivClose = findViewById(R.id.ivClose)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data
            ivProfilePic.setImageURI(imageUri)
        }
    }


    private fun setListeners(){

        cvProfilePic.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }


        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val contactNum = etContactNum.text.toString()
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

                            val storageReference = FirebaseStorage.getInstance().reference
                            val profilePictureRef: StorageReference =
                                storageReference.child("profile_pictures/${user!!.uid}.jpg")

                            if(imageUri == null){
                                val db = Firebase.firestore
                                val usersCollection = db.collection("users")

                                val newUser = User(
                                    user.uid,
                                    firstName,
                                    lastName,
                                    email,
                                    contactNum,
                                    Date(),
                                    bio,
                                    ""
                                )

                                usersCollection.document(user.uid)
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
                            }else{
                                profilePictureRef.putFile(imageUri!!)
                                    .addOnSuccessListener {
                                        profilePictureRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                            val downloadUrlString = downloadUrl.toString()

                                            val db = Firebase.firestore
                                            val usersCollection = db.collection("users")

                                            val newUser = User(
                                                user.uid,
                                                firstName,
                                                lastName,
                                                email,
                                                contactNum,
                                                Date(),
                                                bio,
                                                downloadUrlString
                                            )

                                            usersCollection.document(user.uid)
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
                                        }
                                    }
                            }


                        } else {
                            val exception = task.exception
                            if (exception != null) {
                                Log.e("Authentication", "Authentication failed: ${exception.message}")
                                Toast.makeText(this, "Authentication failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.e("Authentication", "Authentication failed.")
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }



        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        ivClose.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("accountFragment", "LoggedOutFragment")
            startActivity(intent)
        }
    }
}