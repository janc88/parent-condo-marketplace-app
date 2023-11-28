package com.example.mobdeve_mco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentMyListingsBinding


class MyListingsFragment : Fragment() {
    private var _binding: FragmentMyListingsBinding? = null
    private val binding: FragmentMyListingsBinding get() = _binding!!

    private val firebaseHelper = FirebaseHelper.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(firebaseHelper.isUserLoggedIn()){
            replaceFragment(MyListingsLoggedIn())
        }else{
            replaceFragment(MyListingsLoggedOut())
        }

    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}