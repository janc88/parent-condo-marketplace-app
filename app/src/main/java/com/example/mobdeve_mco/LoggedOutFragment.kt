package com.example.mobdeve_mco

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentLoggedOutBinding



class LoggedOutFragment : Fragment() {
    private var _binding: FragmentLoggedOutBinding? = null
    private val binding: FragmentLoggedOutBinding get() = _binding!!

    private lateinit var btnSignUp : Button
    private lateinit var btnLogIn : Button


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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}