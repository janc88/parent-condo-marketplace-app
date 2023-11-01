package com.example.mobdeve_mco

import DummyData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentAddListingStep2Binding

class AddListingStep2Fragment : Fragment() {
    private var _binding: FragmentAddListingStep2Binding? = null
    private val binding: FragmentAddListingStep2Binding get() = _binding!!

    private lateinit var rvProperties : RecyclerView
    private lateinit var property2Adapter: Property2Adapter
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedUniversity : String? = ""
    private var selectedItemPosition : Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep2Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onResume() {
        super.onResume()
        updateButtons()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        bindViews(view)
        init()
        getUniversity()
        retrieveSelectedItemPosition()
    }

    private fun updateButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        if(selectedItemPosition != -1){
            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }

    private fun retrieveSelectedItemPosition() {
        selectedItemPosition = sharedPreferences.getInt("propertyPosition", -1)
        if (selectedItemPosition != -1) {
            property2Adapter.setSelectedPosition(selectedItemPosition)
            property2Adapter.notifyItemChanged(selectedItemPosition)
        }
    }

    private fun bindViews(view: View){
        rvProperties = view.findViewById(R.id.rvProperties)
    }

    private fun getUniversity(){
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        selectedUniversity = mapNumberToUniversity(sharedPreferences.getInt("university", -1))
    }


    private fun init(){
        rvProperties.setHasFixedSize(true)
        rvProperties.layoutManager = LinearLayoutManager(this.context)

        // TODO: filter listings by the university the user selected in step 1
        property2Adapter = Property2Adapter(DummyData.propertyList) { selectedPosition ->
            selectedItemPosition = selectedPosition
            updateButtons()
        }
        rvProperties.adapter = property2Adapter

        property2Adapter.onItemClick = {

        }

        val verticalSpacingHeightInPixels = 55
        val itemDecoration = VerticalSpaceItemDecoration(verticalSpacingHeightInPixels)

        rvProperties.addItemDecoration(itemDecoration)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}