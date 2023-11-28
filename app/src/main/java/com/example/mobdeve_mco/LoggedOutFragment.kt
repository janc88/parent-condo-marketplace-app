package com.example.mobdeve_mco

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentLoggedOutBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore
import java.util.Date


class LoggedOutFragment : Fragment() {
    private var _binding: FragmentLoggedOutBinding? = null
    private val binding: FragmentLoggedOutBinding get() = _binding!!

    private lateinit var btnSignUp : Button
    private lateinit var btnLogIn : Button
    private lateinit var btnGoogle : Button

    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var auth : FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedOutBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp = view.findViewById<Button>(R.id.btnSignUp);
        btnSignUp.paintFlags = btnSignUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnGoogle = view.findViewById<Button>(R.id.btnGoogle);
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("844272747520-rp6ntj34ct1oc08krldderghhg8dk8at.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnGoogle.setOnClickListener {
            signInWithGoogle()
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogIn = view.findViewById<Button>(R.id.btnLogIn)

        btnLogIn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user!!.uid

                        val db = com.google.firebase.ktx.Firebase.firestore
                        val usersCollection = db.collection("users")

                        usersCollection.document(userId).get().addOnCompleteListener { snapshotTask ->
                            if (snapshotTask.isSuccessful) {
                                val document = snapshotTask.result
                                if (document.exists()) {
                                    val intent = Intent(requireContext(), MainActivity::class.java)
                                    intent.putExtra("userLoggedIn", true)
                                    startActivity(intent)
                                    requireActivity().finish()
                                } else {
                                    val firstName = user.displayName?.split(" ")?.getOrNull(0) ?: ""
                                    val lastName = user.displayName?.split(" ")?.getOrNull(1) ?: ""
                                    val email = user.email
                                    val photoUrl = user.photoUrl.toString()

                                    val newUser = User(
                                        userId,
                                        firstName,
                                        lastName,
                                        email!!,
                                        "",
                                        Date(),
                                        "",
                                        photoUrl
                                    )

                                    usersCollection.document(userId)
                                        .set(newUser)
                                        .addOnSuccessListener {
                                            Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()

                                            val intent = Intent(requireContext(), MainActivity::class.java)
                                            intent.putExtra("userLoggedIn", true)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(requireContext(), "Failed to store user data in Firestore: $e", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to check user data in Firestore: ${snapshotTask.exception}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Sign out failed", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}