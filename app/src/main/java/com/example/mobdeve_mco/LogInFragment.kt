package com.example.mobdeve_mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobdeve_mco.databinding.FragmentLogInBinding


class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}