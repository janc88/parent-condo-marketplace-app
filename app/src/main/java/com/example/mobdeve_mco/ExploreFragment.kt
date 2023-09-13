package com.example.mobdeve_mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding: FragmentExploreBinding get() = _binding!!
    private lateinit var rvSearchResults: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDLSU.setOnClickListener {
            Toast.makeText(requireContext(), "DLSU", Toast.LENGTH_SHORT).show()
        }

        rvSearchResults= view.findViewById(R.id.rvSearchResults)

        rvSearchResults.adapter
        rvSearchResults.setHasFixedSize(true)
        rvSearchResults.layoutManager = GridLayoutManager(this.activity, 2)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}