package com.example.mobdeve_mco


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentMyListingsLoggedOutBinding



class MyListingsLoggedOut : Fragment() {
    private var _binding: FragmentMyListingsLoggedOutBinding? = null
    private val binding: FragmentMyListingsLoggedOutBinding get() = _binding!!

    private lateinit var btnLogIn : Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListingsLoggedOutBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogIn = view.findViewById(R.id.btnLogIn)

        btnLogIn.setOnClickListener {
            val bottomSheetFragment = LoginBottomSheetFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}