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

class LoggedInFragment : Fragment() {
    private var _binding: FragmentLoggedInBinding? = null
    private val binding: FragmentLoggedInBinding get() = _binding!!

    private lateinit var tvId : TextView
    private lateinit var tvEmail : TextView
    private lateinit var tvName : TextView
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
        tvName = view.findViewById(R.id.tvName)
        btnLogOut = view.findViewById(R.id.btnLogOut)


        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            val email = currentUser.email
            val displayName = currentUser.displayName

            tvId.text = uid
            tvEmail.text = email
            tvName.text = displayName


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