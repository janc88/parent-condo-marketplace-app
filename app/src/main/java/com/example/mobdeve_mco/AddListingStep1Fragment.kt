package com.example.mobdeve_mco

import DummyData
import ListingViewModel
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentAddListingStep1Binding

class AddListingStep1Fragment : Fragment() {
    private var _binding: FragmentAddListingStep1Binding? = null
    private val binding: FragmentAddListingStep1Binding get() = _binding!!


    private lateinit var optionDLSU : LinearLayout
    private lateinit var optionADMU : LinearLayout
    private lateinit var optionUST : LinearLayout
    private lateinit var optionUP : LinearLayout

    private lateinit var btnDLSU : Button
    private lateinit var btnADMU : Button
    private lateinit var btnUST : Button
    private lateinit var btnUP : Button

    private lateinit var sharedPreferences: SharedPreferences



    private var selectedOption = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep1Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
        setListeners()
        updateButtons()
        setSharedPreferences()
    }

    private fun setSharedPreferences(){
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        selectedOption = sharedPreferences.getInt("university", -1)
        if (selectedOption != -1) {
            selectOption(selectedOption)
        }
    }

    private fun mapNumberToUniversity(number: Int): String {
        return when (number) {
            1 -> "DLSU"
            2 -> "ADMU"
            3 -> "UST"
            4 -> "UP"
            else -> ""
        }
    }

    private fun mapUniversityToNumber(university: String): Int {
        return when (university) {
            "DLSU" -> 1
            "ADMU" -> 2
            "UST" -> 3
            "UP" -> 4
            else -> -1
        }
    }


    // access back and next buttons of viewpager
    private fun updateButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        if(selectedOption != -1){
            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

    }


    private fun bindViews(view: View){
        optionDLSU = view.findViewById(R.id.optionDLSU)
        optionADMU = view.findViewById(R.id.optionADMU)
        optionUST = view.findViewById(R.id.optionUST)
        optionUP = view.findViewById(R.id.optionUP)

        btnDLSU = view.findViewById(R.id.btnDLSU)
        btnADMU = view.findViewById(R.id.btnADMU)
        btnUST = view.findViewById(R.id.btnUST)
        btnUP = view.findViewById(R.id.btnUP)
    }

    private fun setListeners(){
        btnDLSU.setOnClickListener{
            selectOption(1)
        }

        btnADMU.setOnClickListener{
            selectOption(2)
        }

        btnUST.setOnClickListener{
            selectOption(3)
        }

        btnUP.setOnClickListener{
            selectOption(4)
        }
    }

    private fun selectOption(optionNumber: Int) {
        clearSelection()

        selectedOption = optionNumber

        when (optionNumber) {
            1 -> optionDLSU.setBackgroundResource(R.drawable.round_10_card_selected)
            2 -> optionADMU.setBackgroundResource(R.drawable.round_10_card_selected)
            3 -> optionUST.setBackgroundResource(R.drawable.round_10_card_selected)
            4 -> optionUP.setBackgroundResource(R.drawable.round_10_card_selected)
        }

        sharedPreferences.edit().putInt("university", selectedOption).apply()
        updateButtons()
    }

    private fun clearSelection() {
        optionDLSU.setBackgroundResource(R.drawable.round_10_card)
        optionADMU.setBackgroundResource(R.drawable.round_10_card)
        optionUST.setBackgroundResource(R.drawable.round_10_card)
        optionUP.setBackgroundResource(R.drawable.round_10_card)

        selectedOption = -1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}