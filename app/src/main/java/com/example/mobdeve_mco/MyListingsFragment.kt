package com.example.mobdeve_mco

import DummyData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentMyListingsBinding


class MyListingsFragment : Fragment() {
    private var _binding: FragmentMyListingsBinding? = null
    private val binding: FragmentMyListingsBinding get() = _binding!!

    private lateinit var rvMyListings: RecyclerView

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

        rvMyListings = view.findViewById(R.id.rvMyListings)


        val layoutManager = GridLayoutManager(context, 2)

        rvMyListings.layoutManager = layoutManager

        val myListingsAdapter = MyListingAdapter(DummyData.listingList)
        rvMyListings.adapter = myListingsAdapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}