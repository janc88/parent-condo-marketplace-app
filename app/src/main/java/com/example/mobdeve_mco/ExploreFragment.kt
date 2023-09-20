package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
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
    private lateinit var btnDLSU : ToggleButton
    private lateinit var btnADMU : ToggleButton
    private lateinit var btnUP : ToggleButton
    private lateinit var btnUST : ToggleButton

    private lateinit var imageList : ArrayList<Int>

    private var selectedUniversity: String? = null

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

        rvSearchResults= view.findViewById(R.id.rvSearchResults)
        svExplore = view.findViewById(R.id.svExplore)

        rvSearchResults.setHasFixedSize(true)
        rvSearchResults.layoutManager = LinearLayoutManager(this.activity)

        listings = ArrayList()

        imageList = ArrayList()

        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)

        listings.add(Listing(imageList, "1BR 25sqm fully furnished", 15000, "Green Residences", "DLSU"))
        listings.add(Listing(imageList, "3BR 45sqm top floor", 25000, "Taft Residences", "UP"))
        listings.add(Listing(imageList, "fully furnished", 10000, "Archers Place", "ADMU"))
        listings.add(Listing(imageList, "room for rent", 9000, "Green Residences", "DLSU"))
        listings.add(Listing(imageList, "big room near UST", 12000, "UST Residences", "UST"))

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
                searchList(newText)
                return true
            }
        })

        btnDLSU = view.findViewById(R.id.btnDLSU)
        btnADMU = view.findViewById(R.id.btnADMU)
        btnUST = view.findViewById(R.id.btnUST)
        btnUP = view.findViewById(R.id.btnUP)


        val toggleButtons = listOf(btnDLSU, btnADMU, btnUST, btnUP)

        for (button in toggleButtons) {
            button.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(compoundButton: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        for (otherButton in toggleButtons) {
                            if (otherButton != compoundButton) {
                                otherButton.isChecked = false
                            }
                        }
                        selectedUniversity = button.text.toString()
                        button.setTextColor(resources.getColor(R.color.red))
                        button.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
                        filterList(selectedUniversity)
                    } else {
                        selectedUniversity = null
                        button.setTextColor(resources.getColor(R.color.gray))
                        button.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal)
                        listingAdapter.setFilteredList(listings)
                    }
                }
            })
        }
    }

    private fun filterList(filter: String?) {
        if(filter != null){
            val filteredList = ArrayList<Listing>()
            for(i in listings){
                if(i.university.contains(filter)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this.activity, "No matches found", Toast.LENGTH_SHORT).show()
            }else{
                listingAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun searchList(searchQuery: String?) {
        if(searchQuery != null){
            val filteredList = ArrayList<Listing>()
            for(i in listings){
                if(i.title.lowercase(Locale.ROOT).contains(searchQuery)){
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