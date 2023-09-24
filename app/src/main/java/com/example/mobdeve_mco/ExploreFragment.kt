package com.example.mobdeve_mco

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentExploreBinding
import java.util.Locale


class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding: FragmentExploreBinding get() = _binding!!

    private lateinit var rvSearchResults: RecyclerView
//    private lateinit var listings : ArrayList<Listing>
    private lateinit var properties : ArrayList<Property>
//    private lateinit var listingAdapter: ListingAdapter
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var svExplore : SearchView
    private lateinit var btnDLSU : ToggleButton
    private lateinit var btnADMU : ToggleButton
    private lateinit var btnUP : ToggleButton
    private lateinit var btnUST : ToggleButton
    private lateinit var tvNoFound: TextView

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

//        listings = ArrayList()
        properties = ArrayList()

        imageList = ArrayList()

        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)

        properties.add(Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU"))
        properties.add(Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU"))
        properties.add(Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU"))
        properties.add(Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU"))

        propertyAdapter = PropertyAdapter(properties)
        rvSearchResults.adapter = propertyAdapter

        propertyAdapter.onItemClick = {
            val intent = Intent(this.activity, PropertyActivity::class.java)
            intent.putExtra("property", it)
            startActivity(intent)
        }


//        svExplore.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                searchAndFilterList(newText)
//                return true
//            }
//        })

        btnDLSU = view.findViewById(R.id.btnDLSU)
        btnADMU = view.findViewById(R.id.btnADMU)
        btnUST = view.findViewById(R.id.btnUST)
        btnUP = view.findViewById(R.id.btnUP)

        tvNoFound = view.findViewById(R.id.tvNoFound)


        val toggleButtons = listOf(btnDLSU, btnADMU, btnUST, btnUP)

//        for (button in toggleButtons) {
//            button.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
//                override fun onCheckedChanged(compoundButton: CompoundButton?, isChecked: Boolean) {
//                    if (isChecked) {
//                        for (otherButton in toggleButtons) {
//                            if (otherButton != compoundButton) {
//                                otherButton.isChecked = false
//                            }
//                        }
//                        selectedUniversity = button.text.toString()
//                        button.setTextColor(resources.getColor(R.color.red))
//                        button.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal_bold)
//                        searchAndFilterList(svExplore.query.toString())
//                    } else {
//                        selectedUniversity = null
//                        button.setTextColor(resources.getColor(R.color.gray))
//                        button.typeface = ResourcesCompat.getFont(requireContext(), R.font.cereal)
//                        searchAndFilterList(svExplore.query.toString())
//                    }
//                }
//            })
//        }
    }

//    private fun searchAndFilterList(query: String?) {
//        val filteredList = ArrayList<Listing>()
//
//        for (i in listings) {
//            val universityFilterPassed = selectedUniversity == null || i.university.contains(selectedUniversity!!)
//            val searchQueryPassed = query.isNullOrBlank() || i.title.lowercase(Locale.ROOT).contains(query)
//
//            if (universityFilterPassed && searchQueryPassed) {
//                filteredList.add(i)
//            }
//        }
//
//        if (filteredList.isEmpty()) {
//            showResults(false)
//        } else {
//            listingAdapter.setFilteredList(filteredList)
//            showResults(true)
//        }
//    }

    private fun showResults(show: Boolean){
        tvNoFound.isVisible = !show
        rvSearchResults.isVisible = show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}