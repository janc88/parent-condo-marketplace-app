package com.example.mobdeve_mco

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentLoggedInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class LoggedInFragment : Fragment(){
    private var _binding: FragmentLoggedInBinding? = null
    private val binding: FragmentLoggedInBinding get() = _binding!!

    private lateinit var tvEmail : TextView
    private lateinit var tvFirstName : TextView
    private lateinit var tvFirstNameTop : TextView
    private lateinit var tvLastName : TextView
    private lateinit var tvContactNum : TextView
    private lateinit var tvBio : TextView
    private lateinit var ivPfp : ShapeableImageView
    private lateinit var btnLogOut : Button
    private lateinit var btnEdit : TextView

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

        tvEmail = view.findViewById(R.id.tvEmail)
        tvFirstNameTop = view.findViewById(R.id.tvFirstNameTop)
        tvFirstName = view.findViewById(R.id.tvFirstName)
        tvLastName = view.findViewById(R.id.tvLastName)
        tvContactNum = view.findViewById(R.id.tvContactNum)
        tvBio = view.findViewById(R.id.tvBio)
        ivPfp = view.findViewById(R.id.ivPfp)
        btnLogOut = view.findViewById(R.id.btnLogOut)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit.paintFlags = btnEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        loadData()

        btnLogOut.setOnClickListener{
            AlertDialog.Builder(requireContext())
                .setTitle("")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this.context, "Logged out successfully", Toast.LENGTH_SHORT).show()

                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_layout, LoggedOutFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                .show()
        }

        btnEdit.setOnClickListener{
            val bottomSheetFragment = EditProfileBottomSheetFragment()

            bottomSheetFragment.setOnProfileUpdatedListener(object : EditProfileBottomSheetFragment.OnProfileUpdatedListener {
                override fun onProfileUpdated(success: Boolean) {
                    if (success) {
                        Toast.makeText(requireActivity(), "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                       loadData()
                    } else {
                        Toast.makeText(requireActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)

        }
    }

    private fun loadData(){

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            val email = currentUser.email

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
                        val contactNum = userData!!.get("contactNum")
                        val bio = userData!!.get("bio").toString()
                        val pfp = userData!!.get("pfp").toString()
                        tvFirstName.text = firstName
                        tvFirstNameTop.text = firstName
                        tvLastName.text = lastName
                        if(contactNum != null){
                            tvContactNum.text = contactNum.toString()
                        } else {
                            tvContactNum.text = "Not available"
                        }

                        tvBio.text = bio
                        if(pfp != "") {
                            Picasso.get().load(pfp).into(ivPfp);
                        }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}