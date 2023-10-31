package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentLoggedInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoggedInFragment : Fragment() {
    private var _binding: FragmentLoggedInBinding? = null
    private val binding: FragmentLoggedInBinding get() = _binding!!

    private lateinit var tvId : TextView
    private lateinit var tvEmail : TextView
    private lateinit var tvFirstName : TextView
    private lateinit var tvLastName : TextView
    private lateinit var tvBio : TextView

    private lateinit var btnLogOut : Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedInBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvId = view.findViewById(R.id.tvId)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvFirstName = view.findViewById(R.id.tvFirstName)
        tvLastName = view.findViewById(R.id.tvLastName)
        tvBio = view.findViewById(R.id.tvBio)
        btnLogOut = view.findViewById(R.id.btnLogOut)


        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            val email = currentUser.email

            tvId.text = uid
            tvEmail.text = email

            val db = Firebase.firestore

            val usersCollection = db.collection("users")
            val userDocument = usersCollection.document(uid)

            userDocument.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userData = document.data
                        val firstName = userData!!.get("firstname").toString()
                        val lastName = userData!!.get("lastname").toString()
                        val bio = userData!!.get("bio").toString()
                        tvFirstName.text = firstName
                        tvLastName.text = lastName
                        tvBio.text = bio

                    } else {
                        // Handle the case where the user document doesn't exist
                    }
                }
                .addOnFailureListener { e ->
                    // Handle any errors that occurred during the read operation
                    // e.g., network errors, permission issues, etc.
                }


        } else {
            // User is not signed in
            // Handle the case where the user is not logged in
        }

        btnLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this.context, "Logged out successfully", Toast.LENGTH_SHORT).show()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, LoggedOutFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}