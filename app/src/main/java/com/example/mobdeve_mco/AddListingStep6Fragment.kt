package com.example.mobdeve_mco

import DummyData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentAddListingStep6Binding

class AddListingStep6Fragment : Fragment() {
    private var _binding: FragmentAddListingStep6Binding? = null
    private val binding: FragmentAddListingStep6Binding get() = _binding!!

    private lateinit var etDescription : EditText
    private lateinit var tvCharactersAvailable : TextView

    private val characterLimit = 430
    private lateinit var sharedPreferences: SharedPreferences

    private var description : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep6Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        bindViews(view)
        loadDescriptionFromSharedPreferences()
        etDescription.setText(description)
        setupTextWatcher()
    }

    private fun setupTextWatcher() {
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed when text changes.
            }

            override fun afterTextChanged(s: Editable?) {
                val currentLength = s?.length ?: 0
                val charactersRemaining = characterLimit - currentLength
                tvCharactersAvailable.text = charactersRemaining.toString()

                saveDescriptionToSharedPreferences(s.toString())
                description = s.toString()
                if (currentLength >= characterLimit) {
                    s?.delete(characterLimit, currentLength)
                }
                updateButtons()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateButtons()
    }

    private fun updateButtons() {
        val activity = requireActivity() as AppCompatActivity
        val btnNext = activity.findViewById<Button>(R.id.btnNext)

        if(description.isNotBlank()){
            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }

    private fun loadDescriptionFromSharedPreferences() {
        description = sharedPreferences.getString("description", "") ?: ""
    }

    private fun saveDescriptionToSharedPreferences(text: String) {
        val editor = sharedPreferences.edit()
        editor.putString("description", text)
        editor.apply()
    }

    private fun bindViews(view: View){
        etDescription = view.findViewById(R.id.etDescription)
        tvCharactersAvailable = view.findViewById(R.id.tvCharactersAvailable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}