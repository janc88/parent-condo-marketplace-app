package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentExploreBinding
import java.util.Locale


class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding: FragmentExploreBinding get() = _binding!!

    private lateinit var rvSearchResults: RecyclerView
    private lateinit var listings : ArrayList<Listing>
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var svExplore : SearchView

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
        svExplore = view.findViewById(R.id.svExplore)

        rvSearchResults.setHasFixedSize(true)
        rvSearchResults.layoutManager = LinearLayoutManager(this.activity)

        listings = ArrayList()

        listings.add(Listing(R.drawable.bed, "Green Residence"))
        listings.add(Listing(R.drawable.bed, "Green Residence2"))
        listings.add(Listing(R.drawable.bed, "Green Residence3"))
        listings.add(Listing(R.drawable.bed, "Green Residence4"))
        listings.add(Listing(R.drawable.bed, "Green Residence5"))

        listingAdapter = ListingAdapter(listings)
        rvSearchResults.adapter = listingAdapter

        listingAdapter.onItemClick = {
            val intent = Intent(this.activity, ListingActivity::class.java)
            intent.putExtra("listing", it)
            startActivity(intent)
        }

        svExplore.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


    }

    private fun filterList(newText: String?) {
        if(newText != null){
            val filteredList = ArrayList<Listing>()
            for(i in listings){
                if(i.title.lowercase(Locale.ROOT).contains(newText)){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(this.activity, "No data found", Toast.LENGTH_SHORT).show()
            }else{
                listingAdapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}