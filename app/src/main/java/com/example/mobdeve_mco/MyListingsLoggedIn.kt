package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mc.GridSpacingItemDecoration
import com.example.mobdeve_mco.databinding.FragmentMyListingsLoggedInBinding


class MyListingsLoggedIn : Fragment() {
    private var _binding: FragmentMyListingsLoggedInBinding? = null
    private val binding: FragmentMyListingsLoggedInBinding get() = _binding!!

    private lateinit var rvMyListings: RecyclerView
    private val firebaseHelper = FirebaseHelper.getInstance()
    private lateinit var tvNoFound : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListingsLoggedInBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    private fun showResults(show: Boolean){
        tvNoFound.isVisible = !show
        rvMyListings.isVisible = show
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMyListings = view.findViewById(R.id.rvMyListings)
        tvNoFound = view.findViewById(R.id.tvNoFound)

        firebaseHelper.getCurrentUserListings { listings ->
            if(listings.isNotEmpty()){
                val spacingInPixels = 50
                val layoutManager = GridLayoutManager(context, 2)
                rvMyListings.layoutManager = layoutManager
                rvMyListings.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels))

                val myListingsAdapter = MyListingAdapter(listings)
                rvMyListings.adapter = myListingsAdapter

                myListingsAdapter.onItemClick = {
                    val intent = Intent(this.activity, ListingActivity::class.java)
                    intent.putExtra("listing", it)
                    startActivity(intent)
                }
                showResults(true)
            }else{
                showResults(false)
            }

        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}