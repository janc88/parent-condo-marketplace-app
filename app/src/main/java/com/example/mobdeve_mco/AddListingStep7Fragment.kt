package com.example.mobdeve_mco

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mobdeve_mco.databinding.FragmentAddListingStep7Binding

class AddListingStep7Fragment : Fragment() {
    private var _binding: FragmentAddListingStep7Binding? = null
    private val binding: FragmentAddListingStep7Binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var etPrice : EditText
    private var price : String = "8637"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep7Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        bindViews(view)
        loadPriceFromSharedPreferences()
        etPrice.setText(price)
        savePriceToSharedPreferences(price)
        setupTextWatcher()
    }

    private fun bindViews(view: View){
        etPrice = view.findViewById(R.id.etPrice)
    }

    private fun setupTextWatcher(){
        etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed when text changes.
            }

            override fun afterTextChanged(s: Editable?) {
                savePriceToSharedPreferences(s.toString())
                price = s.toString()
            }
        })
    }

    private fun loadPriceFromSharedPreferences(){
        price = sharedPreferences.getString("price", "8637") ?: ""
        Log.d("price", price)
    }

    private fun savePriceToSharedPreferences(price: String) {
        val editor = sharedPreferences.edit()
        editor.putString("price", price)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}