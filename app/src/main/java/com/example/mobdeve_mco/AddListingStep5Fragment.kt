package com.example.mobdeve_mco

import android.content.Context
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
import com.example.mobdeve_mco.databinding.FragmentAddListingStep5Binding

class AddListingStep5Fragment : Fragment() {
    private var _binding: FragmentAddListingStep5Binding? = null
    private val binding: FragmentAddListingStep5Binding get() = _binding!!

    private lateinit var etTitle : EditText
    private lateinit var tvCharactersAvailable : TextView

    private val characterLimit = 50
    private lateinit var sharedPreferences: SharedPreferences


    private var title : String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep5Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        bindViews(view)
        loadTitleFromSharedPreferences()
        etTitle.setText(title)
        setupTextWatcher()
    }

    private fun bindViews(view: View){
        etTitle = view.findViewById(R.id.etTitle)
        tvCharactersAvailable = view.findViewById(R.id.tvCharactersAvailable)
    }

    private fun setupTextWatcher() {
        etTitle.addTextChangedListener(object : TextWatcher {
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

                saveTitleToSharedPreferences(s.toString())
                title = s.toString()
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

        if(title.isNotBlank()){
            btnNext.isEnabled = true
            btnNext.setTextColor(resources.getColor(android.R.color.white))
        } else {
            btnNext.isEnabled = false
            btnNext.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }

    private fun loadTitleFromSharedPreferences() {
        title = sharedPreferences.getString("title", "") ?: ""
    }

    private fun saveTitleToSharedPreferences(text: String) {
        val editor = sharedPreferences.edit()
        editor.putString("title", text)
        editor.apply()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}